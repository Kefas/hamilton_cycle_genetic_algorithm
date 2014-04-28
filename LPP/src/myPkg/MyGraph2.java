package myPkg;

import java.util.Random;

public class MyGraph2 {
	private final int maxDistance = 1000;
	Vertex2 tableOfVertexes[];
	private Random random = new Random();
	
	int tmpDistance = 0, tmpMinSumDistance = Integer.MAX_VALUE;
	
	MyGraph2(int size){
		tableOfVertexes = new Vertex2[size];
		/*
		 * currVer -> current Vertex
		 * currNeigh -> current Neighbour (for current Vertex)
		 */
		for(int currVer=0; currVer < size; currVer++){
			for(int currNeigh=0; currNeigh < size; currNeigh++){
				if(currNeigh < currVer){
					// these distances has been already generated
					tmpDistance = tableOfVertexes[currNeigh].getDistanceToNeigbour(currVer);
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, tmpDistance);
				}
				if(currNeigh==currVer){
					// distance to itself is 0
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, 0);
				}else{
					//we need to retain 'triangle inequality'
					tmpMinSumDistance = Integer.MAX_VALUE;
					for(int x=0; x < currVer; currVer++){
						if( tmpMinSumDistance > 
									tableOfVertexes[x].getDistanceToNeigbour(currNeigh) 
									+ tableOfVertexes[x].getDistanceToNeigbour(currVer) 
								)
							tmpMinSumDistance = tableOfVertexes[x].getDistanceToNeigbour(currNeigh);
					}
					tableOfVertexes[currVer].setDistanceToNeighbour(currNeigh, random.nextInt(tmpMinSumDistance-1) + 1 );
				}
			}
		}			
	}
	
	
	
	
	
	
}
