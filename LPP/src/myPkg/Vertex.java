package myPkg;

public class Vertex {
	int numberOfAllVertexes;
	double [] tabOfDistToNeigh;
	int id;
	public Vertex(int id, int noOfAllVertexes, double [] values){
		numberOfAllVertexes = noOfAllVertexes;
		this.id = id;		
		tabOfDistToNeigh = values.clone();	
	}
	
	public Vertex(int id, int noOfAllVertexes){
		numberOfAllVertexes = noOfAllVertexes;
		this.id = id;
		tabOfDistToNeigh = new double[numberOfAllVertexes];		
	}
	/* index of 'tab' indicates the id of vertex, value at this index is the distance to this vertex
	 * at 'id' position in tab - 0 is placed
	 */
	
	public void setDistanceToNeighbour(int idOfNeighbour, double distance){
		tabOfDistToNeigh[idOfNeighbour] = distance;
	}
	public double getDistanceToNeigbour(int idOfNeighbour){
		return tabOfDistToNeigh[idOfNeighbour];
	}
	public double[] getTabOfNeigh(){
		return tabOfDistToNeigh;
	}
	public void setTabOfNeigh(double[] tabOfNeigh) {
		this.tabOfDistToNeigh = tabOfNeigh;
	}
}
