package myPkg;

import java.util.ArrayList;
import java.util.Random;

public class MyGraph {
	private final int maxDistance = 1000;
	Vertex tableOfVertexes[];
	private Random random = new Random();
	private ArrayList<VertexJUNG> graph;
	
	int tmpDistance = 0, tmpMinSumDistance = Integer.MAX_VALUE;
	
	public MyGraph(int size){
		tableOfVertexes = new Vertex[size];
		/*
		 * currVer -> current Vertex
		 * currNeigh -> current Neighbor (for current Vertex)
		 */
		for(int currVer=0; currVer < size; currVer++){
			tableOfVertexes[currVer] = new Vertex(currVer, size);
			for(int currNeigh=0; currNeigh < size; currNeigh++){
				if(currNeigh < currVer){
					// these distances has been already generated
					tmpDistance = tableOfVertexes[currNeigh].getDistanceToNeigbour(currVer);
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, tmpDistance);
				}else if(currNeigh==currVer){
					// distance to itself is 0
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, 0);
				}else{
					//we need to retain 'triangle inequality'
					tmpMinSumDistance = currVer==0? maxDistance : Integer.MAX_VALUE;
					for(int x=0; x < currVer; x++){
						if( tmpMinSumDistance > 
									tableOfVertexes[x].getDistanceToNeigbour(currNeigh) 
									+ tableOfVertexes[x].getDistanceToNeigbour(currVer) 
								)
							//tmpMinSumDistance = tableOfVertexes[x].getDistanceToNeigbour(currNeigh);
							tmpMinSumDistance = tableOfVertexes[x].getDistanceToNeigbour(currNeigh)+ tableOfVertexes[x].getDistanceToNeigbour(currVer);
					}
					tmpMinSumDistance = tmpMinSumDistance <=1000? tmpMinSumDistance : 1000;
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, random.nextInt(tmpMinSumDistance-1) + 1 );
				}
			}
		}			
	}
	
	

	public void wypisz() {
		
		for(int i=0; i<tableOfVertexes.length; i++){
			for(int j=0; j<tableOfVertexes.length; j++)
				System.out.print( tableOfVertexes[i].getDistanceToNeigbour(j) + "\t");
			System.out.println("");
		}
		
	}
	int getNodesAmount(){
		return tableOfVertexes.length;
	}
	int getDistance(int vertex1, int vertex2){
		return tableOfVertexes[vertex1].getDistanceToNeigbour(vertex2);
	}
	public static void main(String [] args){
		MyGraph nowy = new MyGraph(5);
		nowy.wypisz();
		Individual osobnik = new Individual(nowy);
		System.out.println(osobnik + "\n" + osobnik.getRouteLength());
		
	}
	int getSize(){
		return tableOfVertexes.length;
	}
	public ArrayList<VertexJUNG> getGraph(){
		graph = new ArrayList<>();
		for(int i=0; i<tableOfVertexes.length; i++){
			graph.add(new VertexJUNG(tableOfVertexes[i].getTabOfNeigh()));
		}
		return graph;
	}	
	
}
