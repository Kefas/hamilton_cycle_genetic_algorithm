package myPkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import cern.jet.random.Gamma;

public class MainLoop{
	Random random = new Random();
	int sizeOfPopulation;
	int iterationCounter = 0;
	int iterationsLimit = 100;
	MyGraph graph;
	ArrayList<Individual> generation = new ArrayList<>();
	
	public MainLoop(MyGraph graph) {
		this.graph = graph;
		sizeOfPopulation = graph.getSize()/2;
		for(int i=0; i<sizeOfPopulation; i++){
			generation.add(new Individual(graph));
		}
	}

	void start(){
			while( continueOrNot() ){
				reproduction();
				performCrossing();
				performMutation(1);
				System.out.println(assessPopulation()+"\n#################################");
				iterationCounter++;			
			}
			
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
			newGen.add(generation.get(k));
		}
		generation = newGen;
	}
	void performCrossing(){
		Individual ind1, ind2;
		ArrayList<Individual> newGeneration = new ArrayList<Individual>();
		int tmp;
		for(int i=0; i<sizeOfPopulation/2; i++){
			ind1 = generation.get(0);
			generation.remove(0);
			ind2 = generation.get( tmp = random.nextInt(generation.size()) );
			generation.remove(tmp);
			newGeneration.add(ind1);
			newGeneration.add(ind2);
		}
		generation = newGeneration;
	}
	void performMutation(int type){
		switch(type){
		case 1:
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
	
	public static void main(String [] args){
		MainLoop m = new MainLoop(new MyGraph(20));
		/*m.graph.wypisz();
		System.out.println("Stara generacja: "+Arrays.toString(m.generation.toArray()));
		m.reproduction();
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55\n"+
		"Nowa generacja: "+Arrays.toString(m.generation.toArray()));
		*/
		m.start();
	}
	
}
