package myPkg;

import java.util.ArrayList;
import java.util.Random;

public class Vertex {
	ArrayList<Pair<Integer, Integer>> list;
	
	//Co tutaj się dzieje? Co oznacza zmienna rows?
	public Vertex(int [][] tab, int rows){
		list = new ArrayList<>();
		Random random = new Random();
		for(int j=rows; j<tab.length;j++)
				if(tab[rows][j] != 0)
					list.add(new Pair<Integer, Integer>(j, random.nextInt(MyGraph.MAX_VALUE)));
	}
	
	public String toString(){
		String result = "";
		for(int i=0; i<list.size();i++){
			result += Integer.toString(list.get(i).getL()) + " - " + Integer.toString(list.get(i).getR());
			result += "\n";
		}		
		return result;	
	}
	
	public ArrayList<Pair<Integer, Integer>> getList(){
		return list;
	}

}
