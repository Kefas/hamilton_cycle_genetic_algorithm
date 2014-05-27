package myPkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.media.j3d.Leaf;

import sun.security.util.Length;

public class Individual implements Comparable<Individual> {
	int codedRoute[];
	int nodesAmount;
	static int numberOfMutations = 0;
	ParamsOfIndividual indParams;
	//double mutProbabMod = 1;//mutation probability modifier from [0.5; 1.5]
	MyGraph graph;
	static Random random = new Random();
	
	public Individual(MyGraph graph, ParamsOfIndividual params){
		this(graph, graph.getNodesAmount(),params);
	}
	Individual(MyGraph graph, int size, ParamsOfIndividual params){
		this.graph = graph;
		nodesAmount = size;
		this.indParams = params;
		codedRoute = new int[size];
		for(int i=0; i<size; i++){
			codedRoute[i] = random.nextInt(size - i );
		}	
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Individual newOne = new Individual(graph, indParams);
		newOne.codedRoute = this.codedRoute.clone();
		return newOne;
	}
	
	int [] getRoute(){
		ArrayList<Integer> auxiliaryList = new ArrayList<Integer>();
		for(int i=0; i<nodesAmount; i++){
			auxiliaryList.add(i);
		}
		int routeTab[] = new int[nodesAmount];
		for(int i=0; i<nodesAmount; i++){
			routeTab[i] = auxiliaryList.get( codedRoute[i] );
			auxiliaryList.remove(codedRoute[i]);
		}
		return routeTab;
	}
	double getRouteLength(){
		double lengthSubSum = 0;
		int routeTab[] = getRoute(); //uncoded route
		int previous = routeTab[routeTab.length-1]; //previous node 
		int next = routeTab[0];						//next node
		lengthSubSum += graph.getDistance(previous, next);

		for(int i=1; i<nodesAmount; i++){
			previous = next;
			next = routeTab[i];
			lengthSubSum += graph.getDistance(previous, next);
		}
		
		return lengthSubSum;
	}
	
	void crossing1(Individual other){
		int cut = random.nextInt(this.nodesAmount);
		//cut specifies position AFTER which there'll be cut
		int [] newCodedRoute1 = new int[nodesAmount];
		int [] newCodedRoute2 = new int[nodesAmount];
		for(int i=0; i<nodesAmount; i++){
			if(i<=cut){
				newCodedRoute1[i] = this.codedRoute[i];
				newCodedRoute2[i] = other.codedRoute[i];
			}else{
				newCodedRoute1[i] = other.codedRoute[i];
				newCodedRoute2[i] = this.codedRoute[i];
			}
		}	
		this.codedRoute = newCodedRoute1;
		other.codedRoute = newCodedRoute2;
	}
	void crossing2(Individual other){
		int firstCut = random.nextInt(this.nodesAmount);
		//firstCut specifies position BEFORE which there'll be cut
		//secondCut specifies position AFTER which there'll be cut
		int secondCut = random.nextInt(this.nodesAmount - firstCut) + firstCut;
		int [] newCodedRoute1 = new int[nodesAmount];
		int [] newCodedRoute2 = new int[nodesAmount];
		for(int i=0; i<nodesAmount; i++){
			if(i<firstCut || i > secondCut){
				newCodedRoute1[i] = this.codedRoute[i];
				newCodedRoute2[i] = other.codedRoute[i];
			}else{
				newCodedRoute1[i] = other.codedRoute[i];
				newCodedRoute2[i] = this.codedRoute[i];
			}
		}
	//	System.out.println("FirstCut: "+firstCut+" Second Cut: "+secondCut+"\n");
		
		this.codedRoute = newCodedRoute1;
		other.codedRoute = newCodedRoute2;
	}
	
	
	/**
	 * Pair mutation
	 * @return
	 */
	void mutation1(){
		//double basicProbability = 1./nodesAmount;
		double probabilityOfMutForInd = 0.01* indParams.getMutProbabMod(); //modifier from [0.5; 1.5], one percent of pupulation mutates on average
		double probabilityOfMutForGene =  1./nodesAmount; // if individual mutates - an average there is only one mutation
		
		//whether this individual mutates or not:
		if( random.nextDouble() > probabilityOfMutForInd)
			return;
		numberOfMutations++;
		/*if(numberOfMutations > 1000)
			System.out.println("Hej");*/
		double tmpToTest;
		for(int i=0; i<nodesAmount; i++){
			if((tmpToTest = random.nextDouble()) <= probabilityOfMutForGene){
				for(int j=0; j<nodesAmount; j++)
					if((tmpToTest = random.nextDouble()) <= probabilityOfMutForGene){
						this.swapElements(i, j);
					//	System.out.println("Swap: '"+i+"' and '"+j+"'");
					}
			}	
		}
	}
	
	void swapElements(int index1, int index2){
		if(index1 == index2)
			return;
		int decodedRoute[] = this.getRoute();
		int tmp = decodedRoute[index1];
		decodedRoute[index1]=decodedRoute[index2];
		decodedRoute[index2] = tmp;
		
		//transforming decoded route to coded one
		ArrayList<Integer> auxiliaryList = new ArrayList<Integer>();
		for(int i=0; i<nodesAmount; i++){
			auxiliaryList.add(i);
		}
		int newCodedRoute[] = new int[nodesAmount];
		for(int i=0; i<nodesAmount; i++){
			newCodedRoute[i] = auxiliaryList.indexOf(decodedRoute[i]);
			auxiliaryList.remove(newCodedRoute[i]);
		}
		Individual nowy = new Individual(graph, indParams);
		this.codedRoute = newCodedRoute;
	}
	
	/**
	 * this mutation increments or decrements value of random element(s) in codedRoute
	 */
	void mutation2(){
		//double basicProbability = 1./nodesAmount;
		//double probabilityOfMut = basicProbability * indParams.getMutProbabMod();
		
		double probabilityOfMutForInd = 0.01* indParams.getMutProbabMod(); //modifier from [0.5; 1.5], one percent of pupulation mutates on average
		double probabilityOfMutForGene =  1./nodesAmount; // if individual mutates - an average there is only one mutation
		
		//whether this individual mutates or not:
		if( random.nextDouble() > probabilityOfMutForInd)
			return;		
		for(int i=0; i<nodesAmount-1; i++){
			if(random.nextDouble()<= probabilityOfMutForGene){
				//System.out.println("Mutation at "+i+" th position");
				if(this.codedRoute[i] < nodesAmount-1 -i){
					if(this.codedRoute[i] > 0){
						codedRoute[i] += (random.nextBoolean())? -1 : 1;
					}else
						codedRoute[i]++;
				}else
					codedRoute[i]--;
			}
		}
		
	}
	
	/**
	 * this mutation changes randomly value of random element(s) in codedRoute
	 */
	void mutation3(){
		//double basicProbability = 1./nodesAmount;
		//double probabilityOfMutForGene = basicProbability * indParams.getMutProbabMod();
		
		double probabilityOfMutForInd = 0.01* indParams.getMutProbabMod(); //modifier from [0.5; 1.5], one percent of pupulation mutates on average
		double probabilityOfMutForGene =  1./nodesAmount; // if individual mutates - an average there is only one mutation
		
		//whether this individual mutates or not:
		if( random.nextDouble() > probabilityOfMutForInd)
			return;		
		for(int i=0; i<nodesAmount-1; i++){
			if(random.nextDouble()<= probabilityOfMutForGene){
				//System.out.println("Mutation at "+i+" th position");
				codedRoute[i] = random.nextInt(nodesAmount-i);
			}
		}
		
	}
	
	@Override
	public String toString() {
		return "Individual \n[codedRoute=" + Arrays.toString(codedRoute)
				+ "]\n [route=" + Arrays.toString(this.getRoute()) +" ]\n"+
				droga()+"\n";
	}
	public String droga(){
		return "### Total distance: "+getRouteLength()+" ###\n";
	}
	
	public static void main(String [] args){
		MyGraph graph = new MyGraph(5);
		Individual nowy = new Individual(graph,new ParamsOfIndividual());
		Individual nowy2 = new Individual(graph,new ParamsOfIndividual());
		int tab[]=nowy.getRoute();
		for (int i : tab) {
			System.out.print(i+" ");
		}
		System.out.println("\n"+nowy);
		System.out.println(nowy2);
		nowy.crossing2(nowy2);
		System.out.println("\n"+nowy);
		System.out.println(nowy2);
		
		//nowy.mutation3();
		//nowy.mutation3();
		//System.out.println(nowy);
		/*System.out.println(nowy2);
		System.out.println(nowy.crossing(nowy2));*/
	}
	@Override
	public int compareTo(Individual o) {
		return (new Double( this.getRouteLength() ) ).compareTo(new Double(o.getRouteLength()));
	}
	
	
}
