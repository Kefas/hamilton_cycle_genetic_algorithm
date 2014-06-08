package myPkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import sun.security.krb5.internal.APRep;
import edu.uci.ics.jung.algorithms.generators.EvolvingGraphGenerator;

public class MainLoop extends Thread{
	Random random = new Random();
	int sizeOfPopulation;
	int iterationCounter = 0;
	int iterationsLimit = 1000;
	ArrayList<Double> extremeAncestors = new ArrayList<Double>();
	ParametersOfEvolution evolParams;
	AdaptationValues appraisal = null;
	
	MyGraph graph;
	ArrayList<Individual> generation = new ArrayList<>();
	private static XYPlot plot;
	private static ChartPanel chartPanel;
	private static int datasetIndex = 0;
	private static List<XYSeriesCollection> seriesArrayList = new ArrayList<XYSeriesCollection>();
	private boolean exitPressed = false;
	

	public boolean isExitPressed() {
		return exitPressed;
	}

	public void setExitPressed(boolean exitPressed) {
		this.exitPressed = exitPressed;
	}

	public MainLoop(MyGraph graph, ParametersOfEvolution params, AdaptationValues appraisal) {
		this.graph = graph;
		this.evolParams = params;
		this.appraisal = appraisal;
		sizeOfPopulation = params.getSizeOfPopulation();
		iterationsLimit = evolParams.numberOfIterations;

		for (int i = 0; i < sizeOfPopulation; i++) {
			generation.add(new Individual(graph, params.getIndividualParams()));
		}
	}
	
	static public void clearStatic(){
		plot = null;
		chartPanel = null;
		datasetIndex = 0;
		seriesArrayList = new ArrayList<XYSeriesCollection>();
	}

	public void run() {
		Thread.currentThread().setPriority(MAX_PRIORITY);
		while ( continueOrNot(evolParams.methodToFinish) && !exitPressed) {
			
			reproduction();
			performCrossing(evolParams.getMethodOfBreeding(), evolParams.getMethodOfCrossing());

			performMutation(evolParams.getMethodOfMutation());
	
			/*appraisal =*/ assessPopulation(); //this function modifies 'appraisal'
			if(!exitPressed)
				updateChart(appraisal);
			
			iterationCounter++;
		}
		

	}

	private void updateChart(AdaptationValues generation2) {
		getXYSeries(0).getSeries(0)
				.add(iterationCounter, generation2.getBest());
		getXYSeries(1).getSeries(0).add(iterationCounter,
				generation2.getAverage());
		getXYSeries(2).getSeries(0).add(iterationCounter,
				generation2.getWorst());
	}

	/**
	 * It appraise newGeneration - how it is adapted to environment the lower
	 * value returned - the better adaption
	 * 
	 * @return length of path
	 */
	//AdaptationValues assessPopulation() {
	void assessPopulation() {
				//AdaptationValues toReturn = new AdaptationValues();
		AdaptationValues toReturn = appraisal; // pokrecony sposob, ale to przez to, ze zmienilismy koncepcje w trakcie
		ArrayList<Double> distances = new ArrayList<Double>();
		for (Individual x : generation) {
			distances.add(x.getRouteLength());
		}

		toReturn.setWorst(Collections.max(distances));
		toReturn.setBest(Collections.min(distances));
		int idOfBestInd = 0;
		for(Individual x: generation){
			if(x.getRouteLength()==toReturn.getBest())
				break;
			idOfBestInd++;
		}
		if(idOfBestInd>generation.size()-1)
			System.out.println("Error in assess population");
		
		toReturn.setTheBestCycle(generation.get(idOfBestInd).getRoute());
		long average = 0;
		for (Double x : distances) {
			average += x;
		}
		average /= distances.size();
		toReturn.setAverage(average);
		//return toReturn;
	}

	boolean continueOrNot(int methodOfFinishing) {
		boolean condition = true;
		
		switch(methodOfFinishing){
		case 1:
			//finishes when iterations limit was exceeded
			if (iterationCounter > iterationsLimit)
				condition = false;
			return condition;
		case 2:	
			//searches the 'evolParams.getMaxAmountOfAncestors()' amount of ancestors, choses the best and the worst one 
			// and checks the similarity to them - if is higher then 'evolParams.getSimilarityToAncestors()' the evolution (by default 98%)
			// is finished
			if(extremeAncestors.size() < evolParams.getMaxAmountOfAncestors()){
				extremeAncestors.add(appraisal.getBest());
				return condition;
			}			
			double bestAncestor = Collections.min(extremeAncestors);
			double worstFromBestAncestor = Collections.max(extremeAncestors);
			if(  	appraisal.getBest() / bestAncestor > evolParams.getSimilarityToAncestors()
				&&  appraisal.getBest() / bestAncestor <= 1
				&& 	appraisal.getBest() / worstFromBestAncestor > evolParams.getSimilarityToAncestors()
				&&  appraisal.getBest() / worstFromBestAncestor <= 1 )
			{
				condition = false;
				return condition;
			} 			
			extremeAncestors.remove(0);		
			extremeAncestors.add(appraisal.getBest());
			return condition;
		case 3:
			//this is finish test when we have somehow the correct solution
			// evolution ends when the similarity to exact solution is highet then 'evolParams.getSimiliarityInTest()' (by default 98%)
			if( evolParams.getCorrectResult() / ((double)appraisal.best) < evolParams.getSimiliarityInTest())
				condition = false;
			return condition;
		default: 
			return condition;
		}
		
	}

	void reproduction() {
		ArrayList<Individual> newGen = new ArrayList<Individual>();
		ArrayList<Double> distances = new ArrayList<Double>();
		for (Individual x : generation) {
			distances.add(x.getRouteLength());
		}
		double max = Collections.max(distances);
		max++;
		long totalSum = 0;
		ArrayList<Double> appraisals = new ArrayList<Double>();
		for (Double x : distances) {
			appraisals.add(max - x); // the shortest routes will have the
										// highest value of assessment
			totalSum += max - x;
		}
		double tabOfProbablity[] = new double[distances.size()];
		int i = 0;
		for (Double x : appraisals) {
			if (i == 0)
				tabOfProbablity[i] = ((double) x) / totalSum;
			else
				tabOfProbablity[i] = ((double) x) / totalSum
						+ tabOfProbablity[i - 1];
			i++;
		}// last value in tabOfProbability should be 1, but it may not be
			// because of numerical errors
		tabOfProbablity[tabOfProbablity.length - 1] = 1;

		for (int j = 0; j < generation.size(); j++) {
			double genVal = random.nextDouble();
			int k = 0;
			while (genVal > tabOfProbablity[k]) {
				k++;
			}
			try {
				newGen.add((Individual) generation.get(k).clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		generation = newGen;
	}

	void performCrossing(int typeOfBreeding, int typeOfCrossing) {
		switch(typeOfBreeding){
		case 1:
			selectiveBreedingCrossing(typeOfCrossing);
			break;
		case 2:
			wholePopulationBreedingCrossing(typeOfCrossing);
			break;
		default:
				System.out.println("Wrong arg of preformCrossing");
		}
	}
	void wholePopulationBreedingCrossing(int typeOfCrossing){
		Individual ind1, ind2;
		ArrayList<Individual> newGeneration = new ArrayList<Individual>();
		int tmp;
		for (int i = 0; i < sizeOfPopulation / 2; i++) {
			ind1 = generation.get(0);
			generation.remove(0);
			ind2 = generation.get(tmp = random.nextInt(generation.size()));
			generation.remove(tmp);
			switch (typeOfCrossing) {
			case 1:
				ind1.crossing1(ind2);
				break;
			case 2:
				ind1.crossing2(ind2);
				break;
			}
			newGeneration.add(ind1);
			newGeneration.add(ind2);
		}
		generation = newGeneration;
	}
	
	void selectiveBreedingCrossing(int typeOfCrossing){
		Individual ind1, ind2;
		ArrayList<Individual> newGeneration = new ArrayList<Individual>();
		
		//before reproduction we reduce the population removing the worse half of it
		Collections.sort(generation);
		for(int i=generation.size()/2; i>0; i--){
				newGeneration.add(generation.get(0));
				generation.remove(0);
		}
		//and now we need to cross those individuals from that better half of population 
		// both - parents and their children go into new Generation
		generation = (ArrayList<Individual>) newGeneration.clone();
		int tmp;
		for (int i = generation.size() / 2; i > 0 ; i--) {
			ind1 = generation.get(0);
			generation.remove(0);
			ind2 = generation.get(tmp = random.nextInt(generation.size()));
			generation.remove(tmp);
			//tu po wybraniu pary - robimy krzy�owanie i te par� tak czy siak wklepujemy do nowej populacji
			switch (typeOfCrossing) {
			case 1:
				ind1.crossing1(ind2);
				break;
			case 2:
				ind1.crossing2(ind2);
				break;
			}
			newGeneration.add(ind1);
			newGeneration.add(ind2);
		}
		if( generation.size() > 0)
			newGeneration.add(generation.get(0));
		if(generation.size() > 1)
			System.out.println("Error in performing crossing in interation: "+ iterationCounter+" size: "+generation.size());
		
		generation = newGeneration;
	}

	void performMutation(int type) {
		switch (type) {
		case 1:
			/*
			 * for(int i=0; i<generation.size(); i++){
			 * generation.get(i).mutation1(); } break;
			 */
			for (Individual x : generation) {
				x.mutation1();
			}
			break;
		case 2:
			for (Individual x : generation) {
				x.mutation3();
			}
			break;
		case 3:
			for (Individual x : generation) {
				x.mutation2();
			}
			break;
		default:
			System.out.println("Wrong type of mutation!!!\n");
		}
	}

	private static XYSeriesCollection createDataset(String name) {
		XYSeries series = new XYSeries(name);
		return new XYSeriesCollection(series);
	}

	public static ChartPanel getChart() {
		return chartPanel;
	}

	public static void createAdditionalDataset() {
		seriesArrayList.add(createDataset("S" + datasetIndex));
		plot.setDataset(datasetIndex, seriesArrayList.get(datasetIndex));
		plot.setRenderer(datasetIndex, new StandardXYItemRenderer());
		datasetIndex++;
	}

	public XYSeriesCollection getXYSeries(int datasetIndex) {
		return seriesArrayList.get(datasetIndex);
	}

	public int getDatasetCount() {
		return this.plot.getDatasetCount();
	}
	public void mainFunction(){
		XYSeriesCollection dataset = createDataset("Populacje");
		JFreeChart chart = ChartFactory.createXYLineChart("",
				"Numer Populacji", "Warto��", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		chart.setBackgroundPaint(Color.white);
		plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		NumberAxis rangeAxis2 = new NumberAxis("Range Axis 2");
		rangeAxis2.setAutoRangeIncludesZero(false);
		
		
		JPanel content = new JPanel(new BorderLayout());
		chartPanel = new ChartPanel(chart);
		content.add(chartPanel);
		class ChartFrame extends JFrame{
			
			
			public void windowClosing(final WindowEvent evt){
				if(evt.getWindow() == this){
				dispose();
				

				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void dispose() {
				super.dispose();
				System.out.println("Hello");
				exitPressed = true;
				MainLoop.clearStatic();
				
			}
			
		}

		ChartFrame frame = new ChartFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.add(getChart());
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		

		createAdditionalDataset();
		createAdditionalDataset();
		createAdditionalDataset();
	
		//MainLoop m = new MainLoop(new MyGraph(100), new ParametersOfEvolution(), new AdaptationValues());

		/*
		 * m.graph.wypisz();
		 * System.out.println("Stara generacja: "+Arrays.toString
		 * (m.generation.toArray())); m.reproduction(); System.out.println(
		 * "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55\n"+
		 * "Nowa generacja: "+Arrays.toString(m.generation.toArray()));
		 */
		//m.run();
	}
	

	public static void main(String[] args) {
		MainLoop m = new MainLoop(new MyGraph(100), new ParametersOfEvolution(), new AdaptationValues());
		m.mainFunction();
		m.run();
		
	}

}
