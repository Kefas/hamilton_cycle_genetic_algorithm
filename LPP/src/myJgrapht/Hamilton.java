package myJgrapht;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.ext.MatrixExporter;
import org.jgrapht.ext.VisioExporter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;


public class Hamilton {
	 private static SimpleWeightedGraph<Integer, DefaultWeightedEdge> g = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(
			   DefaultWeightedEdge.class);
		
			 public void addVertex(Integer vertices) {
			  g.addVertex(vertices);
			 }
			 
			 public void addVertex(List<Integer> ids) {
			  for (int id : ids) {
			   g.addVertex(id);
			  }
			 }
			 
			 public void addEdge(int source, int destination, int weight) {
			  DefaultWeightedEdge edge = g.addEdge(source, destination);
			  g.setEdgeWeight(edge, weight);
			 }
			 
			 public List<Integer> execute() {
				 Set<Integer> vertices = g.vertexSet();
//				  addVertex(0);
				  System.out.println(vertices);
//				  for (int v : vertices) {
//				   if (v == 0)
//				    continue;
//				   DefaultWeightedEdge edge = g.addEdge(0, v);
//				   g.setEdgeWeight(edge, 0);
//				  }
				  List<Integer> output = HamiltonianCycle.getApproximateOptimalForCompleteGraph(g);
//				  output.remove(Long.valueOf(0l));
				  return output;
			 }
			 public void export(SimpleWeightedGraph<Integer, DefaultWeightedEdge> g, String path){
				 Writer writer = null;
//				 MatrixExporter<int, DefaultWeightedEdge> exporter = new MatrixExporter<>();
				 try {
					writer = new FileWriter(path);
					for(int x : g.vertexSet()){
						for(int y : g.vertexSet()){
							if(x == y)
								writer.append("0");
							else
								writer.append(Integer.toString((int)(g.getEdgeWeight(g.getEdge(x, y)))));
							writer.append(" ");
						}
						
						writer.append("\n");
					}
					
					
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				 
			 }
			 public static void importFromFile(String path){
				 Scanner reader = null;
				 int cos;
				 int x=0, y=0;
				 List< List<Integer>> matrix = new ArrayList<>();
				 List<Integer> vertices = new ArrayList<Integer>();
				 try {
					reader = new Scanner(new File(path));
					List<Integer> row = new ArrayList<Integer>();
					
					String line=null;
					Scanner scanner = null;
					while(reader.hasNextLine()){
						line = reader.nextLine();
						scanner = new Scanner(line);
						while(scanner.hasNextInt()){
							cos = scanner.nextInt();
							row.add(cos);
							System.out.print(cos + " ");
						}
						System.out.println();
						matrix.add(row);
						row = new ArrayList<>();
					}
					
					for(int i=0;i<matrix.size();i++)
						vertices.add(i);					
					Hamilton h = new Hamilton();
					h.addVertex(vertices);
	
					for(x=0;x<matrix.size();x++){
						for(y=x+1;y<matrix.get(x).size();y++){
							h.addEdge(x, y, matrix.get(x).get(y));
							
						}
					}

					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 
			 }
	public static void main(String[] args) {
//		 	List<Integer> vertices = new ArrayList<Integer>();
//		  vertices.add(0);
//		  vertices.add(1);
//		  vertices.add(2);
//		  Hamilton h = new Hamilton();
//		  
//		  h.addVertex(vertices);	  
//		  h.addEdge(0, 1, 1);
//		  h.addEdge(0, 2, 5);
//		  h.export(g);
//		  h.addEdge(1, 2, 3);
//		  List<Integer> output = h.execute();
//		  
//		  System.out.println("wut" + output);
//		  System.out.println(g);


		 Hamilton h = new Hamilton();
		 importFromFile("output.txt");
		 List<Integer> output = h.execute();
		  System.out.println(g);
		 System.out.println("Cykl hamiltona:" + output);
	}
}	
