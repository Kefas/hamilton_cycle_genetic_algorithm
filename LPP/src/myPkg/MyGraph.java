package myPkg;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.SysexMessage;

public class MyGraph {
	private final int maxDistance = 1000;
	Vertex tableOfVertexes[];
	int tableOfCoordinates[][];
	private Random random = new Random();
	private ArrayList<VertexJUNG> graph;
	
	double tmpDistance = 0, tmpMinSumDistance = Integer.MAX_VALUE;
	
	/**
	 * Generates indirected graph of specified size
	 * @param size
	 */
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
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, (double) random.nextInt((int)tmpMinSumDistance-1) + 1 );
				}
			}
		}			
	}
	
	public MyGraph(String path){
		double [][] matrix = ExportImport.importFromFile(path);
		int size = matrix.length;
		tableOfVertexes = new Vertex[size];
		for(int i=0; i<size; i++){
			tableOfVertexes[i] = new Vertex(i, size);
			tableOfVertexes[i].setTabOfNeigh(matrix[i]);
		}		
	}
	public MyGraph(String path, int _){
		tableOfCoordinates = null;		
		// to zmieniamy ^^ tu ma byæ wczytywanie
		double distanceTmp = 0;
		for(int currVer=0; currVer<tableOfCoordinates.length; currVer++){
			tableOfVertexes[currVer] = new Vertex(currVer, tableOfCoordinates.length);
			for(int currNeigh=0; currNeigh<tableOfCoordinates.length; currNeigh++){
				if(currVer==currNeigh){
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, 0);
				}
				else{
					distanceTmp = Math.pow(tableOfCoordinates[currVer][0] - tableOfCoordinates[currNeigh][0],2) +
							Math.pow(tableOfCoordinates[currVer][1] - tableOfCoordinates[currNeigh][1], 2);
					distanceTmp = Math.sqrt(distanceTmp);							
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, distanceTmp);
				}
			}
		}
	}
	public void exportToFile(String path){
		double [][]matrix = new double[tableOfVertexes.length][tableOfVertexes.length];
		for(int i=0; i<tableOfVertexes.length; i++)
			for(int j=0; j<tableOfVertexes.length; j++)
				matrix[i][j] = tableOfVertexes[i].getDistanceToNeigbour(j);
		ExportImport.exportToFile(matrix, path);
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
	double getDistance(int vertex1, int vertex2){
		return tableOfVertexes[vertex1].getDistanceToNeigbour(vertex2);
	}
	public static void main(String [] args){
		MyGraph nowy = new MyGraph(5);
		nowy.wypisz();
		nowy.exportToFile("nowyGraf5.csv");
		MyGraph nowy2 = new MyGraph("nowyGraf5.csv");
		System.out.println("");
		nowy2.wypisz();
		//Individual osobnik = new Individual(nowy, new ParamsOfIndividual());
		//System.out.println(osobnik + "\n" + osobnik.getRouteLength());
		
	}
	int getSize(){
		return tableOfVertexes.length;
	}
/*	public ArrayList<VertexJUNG> getGraph(){
		graph = new ArrayList<>();
		for(int i=0; i<tableOfVertexes.length; i++){
			graph.add(new VertexJUNG(tableOfVertexes[i].getTabOfNeigh()));
		}
		return graph;
	}	
	*/
}
