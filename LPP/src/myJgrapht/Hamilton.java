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

import myPkg.ExportImport;

import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.ext.MatrixExporter;
import org.jgrapht.ext.VisioExporter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

public class Hamilton {
	private static SimpleWeightedGraph<Integer, DefaultWeightedEdge> g = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(
			DefaultWeightedEdge.class);
	private static double[][] tab;

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
		// addVertex(0);
		// System.out.println(vertices);
		// for (int v : vertices) {
		// if (v == 0)
		// continue;
		// DefaultWeightedEdge edge = g.addEdge(0, v);
		// g.setEdgeWeight(edge, 0);
		// }
		List<Integer> output = HamiltonianCycle
				.getApproximateOptimalForCompleteGraph(g);
		// output.remove(Long.valueOf(0l));

		// write output to file
		Writer writer = null;
		try {
			writer = new FileWriter("output.txt");
			for (int i = 0; i < output.size(); i++) {
				writer.append(output.get(i).toString());
				writer.append(" ");
			}
			writer.append("\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return output;
	}

	public static void importFromFile(String path) {

		int x = 0, y = 0;

		List<Integer> vertices = new ArrayList<Integer>();
		ExportImport ex = new ExportImport();
		tab = ex.importFromFile(path);

		for (int i = 0; i < tab.length; i++)
			vertices.add(i);
		Hamilton h = new Hamilton();
		h.addVertex(vertices);

		for (x = 0; x < tab.length; x++) {
			for (y = x + 1; y < tab.length; y++)
				h.addEdge(x, y,  tab[x][y]);
		}

	}

	public static void main(String[] args) {
		// List<Integer> vertices = new ArrayList<Integer>();
		// vertices.add(0);
		// vertices.add(1);
		// vertices.add(2);
		// Hamilton h = new Hamilton();
		//
		// h.addVertex(vertices);
		// h.addEdge(0, 1, 1);
		// h.addEdge(0, 2, 5);
		// h.export(g);
		// h.addEdge(1, 2, 3);
		// List<Integer> output = h.execute();
		//
		// System.out.println("wut" + output);
		// System.out.println(g);

		Hamilton h = new Hamilton();
		importFromFile("input.txt");
		// new ExportImport().exportToFile(tab, "test2.txt");
		List<Integer> output = h.execute();
		System.out.println("Cykl hamiltona:" + output);
	}
}
