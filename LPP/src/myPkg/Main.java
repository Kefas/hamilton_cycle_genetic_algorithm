package myPkg;

import java.util.ArrayList;

public class Main {
	public static final int GRAPH_SIZE = 10;

	public static final int MAX_VALUE = 1000;
	
	private static ArrayList<Vertex> graph;

	public static void main(String[] args) {
		makeGraph();
		printGraph(graph);
		
	}

	private static void printGraph(ArrayList<Vertex> graph) {
		for(int i=0; i<graph.size(); i++){
			System.out.println(i + ".");
			System.out.println(graph.get(i).toString());
		}
	}

	private static void makeGraph() {
		graph = new ArrayList<>();
		for(int i=0;i<GRAPH_SIZE;i++)
			graph.add(new Vertex());
	}

}
