package myPkg;

import java.util.ArrayList;

public class Main {
	public static final int GRAPH_SIZE = 10;

	public static final int MAX_VALUE = 1000;
	
	private ArrayList<Vertex> graph;

	public void printGraph(ArrayList<Vertex> graph) {
		for(int i=0; i<graph.size(); i++){
			System.out.println(i + ".");
			System.out.println(graph.get(i).toString());
		}
	}

	public void makeGraph() {
		graph = new ArrayList<>();
		for(int i=0;i<GRAPH_SIZE;i++)
			graph.add(new Vertex());
	}

	public ArrayList<Vertex> getGraph() {
		return graph;
	}

	public void setGraph(ArrayList<Vertex> graph) {
		this.graph = graph;
	}
	
	

}
