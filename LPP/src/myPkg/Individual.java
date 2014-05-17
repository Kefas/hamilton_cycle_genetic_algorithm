package myPkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.media.j3d.Leaf;

import sun.security.util.Length;

public class Individual {
	int codedRoute[];
	int nodesAmount;
	double mutProbabMod; //mutation probability modifier
	MyGraph graph;
	Random random = new Random();
	
	public Individual(MyGraph graph){
		this(graph, graph.getNodesAmount(),1);
	}
	Individual(MyGraph graph, int size, int mutProbabMod){
		this.graph = graph;
		nodesAmount = size;
		this.mutProbabMod = mutProbabMod;
		codedRoute = new int[size];
		for(int i=0; i<size; i++){
			codedRoute[i] = random.nextInt(size - i );
		}	
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
	long getRouteLength(){
		int lengthSubSum = 0;
		int routeTab[] = getRoute();
		int previous = routeTab[routeTab.length-1];
		int next = routeTab[0];
		lengthSubSum += graph.getDistance(previous, next);

		for(int i=1; i<nodesAmount; i++){
			previous = next;
			next = routeTab[i];
			lengthSubSum += graph.getDistance(previous, next);
		}
		
		return lengthSubSum;
	}
	
	void crossing(Individual other){
		int firstCut = random.nextInt(this.nodesAmount);
		//firstCut specifies position BEFORE which there'll be cut
		//secondCut specifies position AFTER which there'll be cut
		int secondCut = random.nextInt(this.nodesAmount - firstCut) + firstCut;
		Individual newIndividual1 = new Individual(graph,nodesAmount,1);
		Individual newIndividual2 = new Individual(graph,nodesAmount,1);
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
		System.out.println("FirstCut: "+firstCut+" Second Cut: "+secondCut+"\n");
		
		this.codedRoute = newCodedRoute1;
		other.codedRoute = newCodedRoute2;
	}
	
	
	/**
	 * Pair mutation
	 * @return
	 */
	void mutation1(){
		double basicProbability = 1./nodesAmount;
		double tmpToTest;
		for(int i=0; i<nodesAmount; i++){
			if((tmpToTest = random.nextDouble()) <= basicProbability){
				for(int j=0; j<nodesAmount; j++)
					if((tmpToTest = random.nextDouble()) <= basicProbability){
						this.swapElements(i, j);
						//System.out.println("Swap: '"+i+"' and '"+j+"'");
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
		this.codedRoute = newCodedRoute;
	}
	
	void mutation2(){
		double basicProbability = 1./nodesAmount;
		for(int i=0; i<nodesAmount-1; i++){
			if(random.nextDouble()<= basicProbability){
				System.out.println("Mutation at "+i+" th position");
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
	
	void mutation3(){
		double basicProbability = 1./nodesAmount;
		for(int i=0; i<nodesAmount-1; i++){
			if(random.nextDouble()<= basicProbability){
				System.out.println("Mutation at "+i+" th position");
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
		Individual nowy = new Individual(graph,5,1);
		Individual nowy2 = new Individual(graph,5,1);
		int tab[]=nowy.getRoute();
		for (int i : tab) {
			System.out.print(i+" ");
		}
		System.out.println("\n"+nowy);
		System.out.println(nowy2);
		nowy.crossing(nowy2);
		System.out.println("\n"+nowy);
		System.out.println(nowy2);
		
		//nowy.mutation3();
		//nowy.mutation3();
		//System.out.println(nowy);
		/*System.out.println(nowy2);
		System.out.println(nowy.crossing(nowy2));*/
	}
	
	
}
