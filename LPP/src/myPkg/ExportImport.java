package myPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ExportImport {

	public static int newExportToFile( int vertexCords[][], String path){
		Writer writer = null;
		try {
			writer = new FileWriter(new File(path));
			for(int i=0;i<vertexCords.length;i++){
				for(int j=0; j<vertexCords[i].length;j++)
					writer.append(Integer.toString(vertexCords[i][j]) + " ");
				writer.append("\n");
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}			
		return 1;				
	}
	
	public static int[][] newImportFromFile(String path){
		int vertexCords[][];
		Scanner scanner = null;
		Scanner liner = null;
		try {
			scanner = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		while(scanner.hasNextLine()){
			i++;
			scanner.nextLine();
		}
		scanner.close();
		
		vertexCords = new int[i][];
		for(int j=0;j<i;j++)
			vertexCords[j] = new int[2];
		
		int x=0,y=0;
		try {
			scanner = new Scanner(new File(path));
			while(scanner.hasNextLine()){
				liner = new Scanner(scanner.nextLine());
				vertexCords[x][y++] = liner.nextInt();
				vertexCords[x++][y--] = liner.nextInt();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(int w=0;w<vertexCords.length;w++){
//			for(int z=0;z<vertexCords[w].length;z++)
//				System.out.print(vertexCords[w][z] + " ");
//			System.out.println();
//		}
		return vertexCords;
	
	}
	
	
//	wtf? why double ?
	public static double exportToFile(double [][] matrix, String path){
		Writer writer = null;
		try {
			writer = new FileWriter(new File(path));
			for(int i=0;i<matrix.length;i++){
				for(int j=0;j<matrix.length;j++){
					writer.append(Double.toString(matrix[i][j]));
					writer.append(" ");
				}
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
			
		return 1;
	}
	
	public static double[][] importFromFile(String path){
		Scanner scanner;
		Scanner liner;
		List<List<Integer> > list = new ArrayList<>();
		List<Integer> lineList;
		double [][] matrix = null;
		try {
			scanner = new Scanner(new File(path));
			while(scanner.hasNextLine()){
				liner = new Scanner(scanner.nextLine());
				lineList = new ArrayList<>();
				while(liner.hasNextInt())
					lineList.add(liner.nextInt());
				list.add(lineList);			
				liner.close();
			}			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		matrix = new double[list.size()][];
		for(int i=0;i<list.size();i++){
			matrix[i] = new double[list.size()];
			for(int j=0;j<list.size();j++)
				matrix[i][j] = list.get(i).get(j);
		}
		
	
		return matrix;
	}
	
	public static void main(String [] args){
		ExportImport ex = new ExportImport();
//		double [][] tab = ex.importFromFile("input.txt");
//		ex.exportToFile(tab, "test.txt");
		int tab[][];
		tab = new int[5][];
		for(int i=0;i<5;i++)
			tab[i] = new int[2];
		for(int i=0;i<5;i++)
			for(int j=0;j<2;j++)
				tab[i][j] = i+j;
		ex.newExportToFile(tab, "out.txt");
		ex.newImportFromFile("out.txt");
	}
}
