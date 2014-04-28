package myPkg;

public class Vertex2 {
	int numberOfAllVertexes;
	int [] tab;
	int id;
	public Vertex2(int id, int noOfAllVertexes, int [] values){
		numberOfAllVertexes = noOfAllVertexes;
		this.id = id;
		tab = new int[numberOfAllVertexes];
		tab = values.clone();		
	}
	/* index of 'tab' indicates the id of vertex, value at this index is the distance to this vertex
	 * at 'id' position in tab - 0 is placed
	 */
	
	public void setDistanceToNeighbour(int idOfNeighbour, int distance){
		tab[idOfNeighbour] = distance;
	}
	public int getDistanceToNeigbour(int idOfNeighbour){
		return tab[idOfNeighbour];
	}
}
