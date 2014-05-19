package myPkg;

import java.awt.BorderLayout;
import java.awt.Color;
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

import edu.uci.ics.jung.algorithms.generators.EvolvingGraphGenerator;

public class MainLoop{
	Random random = new Random();
	int sizeOfPopulation;
	int iterationCounter = 0;
	int iterationsLimit = 1000;
	ParametersOfEvolution evolParams;
	MyGraph graph;
	ArrayList<Individual> generation = new ArrayList<>();
	 private static XYPlot plot;
	 private static ChartPanel chartPanel;
	 private static int datasetIndex = 0;
	 private static List<XYSeriesCollection> seriesArrayList = new ArrayList<XYSeriesCollection>();
	 
	public MainLoop(MyGraph graph, ParametersOfEvolution params) {
		this.graph = graph;
		this.evolParams = params;
		sizeOfPopulation = (int) ( graph.getSize()* params.getSizeOfPopulation());
		iterationsLimit = evolParams.numberOfIterations;
		
		for(int i=0; i<sizeOfPopulation; i++){
			generation.add(new Individual(graph, params.getIndividualParams()));
		}
	}
	

	void start(){
		AdaptionValues appraisal = null;
			while( continueOrNot() ){
				
				reproduction();
				performCrossing(evolParams.getMethodOfCrossing());
				
				performMutation(evolParams.getMethodOfMutation());
				appraisal = assessPopulation();
				updateChart(appraisal);
				
				iterationCounter++;			
			}	
			
	}
	private void updateChart(AdaptionValues generation2) {
		 getXYSeries(0).getSeries(0).add(iterationCounter, generation2.getBest());
		 getXYSeries(1).getSeries(0).add(iterationCounter, generation2.getAverage());
		 getXYSeries(2).getSeries(0).add(iterationCounter, generation2.getWorst());
//		 try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	/**
	 * It appraise newGeneration - how it is adapted to environment
	 * the lower value returned - the better adaption
	 * 
	 * @return length of path
	 */
	AdaptionValues assessPopulation(){
		AdaptionValues toReturn = new AdaptionValues();
		ArrayList<Long> distances = new ArrayList<Long>();
		for(Individual x : generation){
			distances.add(x.getRouteLength());
		}
		
		toReturn.setWorst(Collections.max(distances));
		toReturn.setBest(Collections.min(distances));
		long average = 0;
		for(Long x : distances){
			average+= x;
		}
		average /= distances.size();
		toReturn.setAverage(average);
		return toReturn;
	}
	
	boolean continueOrNot(){
		boolean condition = true;
		
		if(iterationCounter > iterationsLimit)
			condition = false;
		
		return condition;
	}
	
	void reproduction(){
		ArrayList<Individual> newGen = new ArrayList<Individual>();
		ArrayList<Long> distances = new ArrayList<Long>();
		for(Individual x : generation){
			distances.add(x.getRouteLength());
		}
		long max = Collections.max(distances);
		max++;
		long totalSum = 0;
		ArrayList<Long> appraisals = new ArrayList<Long>();
		for(Long x : distances){
			appraisals.add(max - x);		  //the shortest routes will have the highest value of assessment
			totalSum+= max - x;
		}
		double tabOfProbablity[] = new double[distances.size()];
		int i =0;
		for(Long x: appraisals){
			if(i==0)
				tabOfProbablity[i] = ((double)x)/totalSum;
			else
				tabOfProbablity[i] = ((double)x)/totalSum + tabOfProbablity[i-1];
			
			i++;
		}//last value in tabOfProbability should be 1, but it may not be because of numerical errors
		tabOfProbablity[tabOfProbablity.length-1] = 1;
	
		for(int j=0; j<generation.size(); j++){
			double genVal = random.nextDouble();
			int k = 0;
			while(genVal>tabOfProbablity[k]){
				k++;
			}
			try {
				newGen.add( (Individual) generation.get(k).clone() );
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		generation = newGen;
	}
	void performCrossing(int typeOfCrossing){
		Individual ind1, ind2;
		ArrayList<Individual> newGeneration = new ArrayList<Individual>();
		int tmp;
		for(int i=0; i<sizeOfPopulation/2; i++){
			ind1 = generation.get(0);
			generation.remove(0);
			ind2 = generation.get( tmp = random.nextInt(generation.size()) );
			generation.remove(tmp);
			switch(typeOfCrossing){
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
	void performMutation(int type){
		switch(type){
		case 1:
			/*for(int i=0; i<generation.size(); i++){
				generation.get(i).mutation1();
			}
			break;*/
			for(Individual x: generation){
				x.mutation1();
			}
			break;
		case 2:
			for(Individual x: generation){
				x.mutation2();
			}
			break;
		case 3:
			for(Individual x: generation){
				x.mutation3();
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
	
	public static void main(String [] args){
		XYSeriesCollection dataset = createDataset("Populacje");
		 JFreeChart chart = ChartFactory.createXYLineChart("", "Numer Populacji", "Wartoœæ",dataset, PlotOrientation.VERTICAL, false, false, false);
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
	        
	        JFrame frame = new JFrame();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        JPanel panel = new JPanel();
	        panel.add(getChart());
	        frame.add(panel);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        
	        createAdditionalDataset();
	        createAdditionalDataset();
	        createAdditionalDataset();
	        
	        
		 MainLoop m = new MainLoop(new MyGraph(100), new ParametersOfEvolution());
		 
		/*m.graph.wypisz();
		System.out.println("Stara generacja: "+Arrays.toString(m.generation.toArray()));
		m.reproduction();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55\n"+
		"Nowa generacja: "+Arrays.toString(m.generation.toArray()));
		*/
		m.start();
	}
	
}
