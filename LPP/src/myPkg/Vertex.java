package myPkg;

public class Vertex {
	int numberOfAllVertexes;
	int [] tabOfNeigh;
	int id;
	public Vertex(int id, int noOfAllVertexes, int [] values){
		numberOfAllVertexes = noOfAllVertexes;
		this.id = id;		
		tabOfNeigh = values.clone();		
	}
	
	public Vertex(int id, int noOfAllVertexes){
		numberOfAllVertexes = noOfAllVertexes;
		this.id = id;
		tabOfNeigh = new int[numberOfAllVertexes];		
	}
	/* index of 'tab' indicates the id of vertex, value at this index is the distance to this vertex
	 * at 'id' position in tab - 0 is placed
	 */
	
	public void setDistanceToNeighbour(int idOfNeighbour, int distance){
		tabOfNeigh[idOfNeighbour] = distance;
	}
	public int getDistanceToNeigbour(int idOfNeighbour){
		return tabOfNeigh[idOfNeighbour];
	}
	public int[] getTabOfNeigh(){
		return tabOfNeigh;
	}
	public void setTabOfNeigh(int[] tabOfNeigh) {
		this.tabOfNeigh = tabOfNeigh;
	}
}
