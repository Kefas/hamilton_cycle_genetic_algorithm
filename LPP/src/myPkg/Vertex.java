package myPkg;

import java.util.ArrayList;
import java.util.Random;

public class Vertex {
	ArrayList<Pair<Integer, Integer>> list;
	
	public Vertex(){
		list = new ArrayList<>();
		Random random = new Random();
		int rand = random.nextInt(Main.GRAPH_SIZE-1)+1;
		//int randVertex;
		//int randValue;
		
		for(int i=0;i<rand;i++){
			//jeżeli graf ma być bez pętli
			
			/*randVertex = random.nextInt(Main.GRAPH_SIZE);
			randValue = random.nextInt(Main.MAX_VALUE);
			for(int j=0;j<i;j++)
				if(list.get(j).getL() == randVertex){
					randVertex = random.nextInt(Main.GRAPH_SIZE);
					j=0;
				}
					
					
			list.add(new Pair(randVertex, randValue));*/
			
			//graf z pętlami i powtórzonymi krawędziami
			list.add(new Pair<Integer, Integer>(random.nextInt(Main.GRAPH_SIZE), random.nextInt(Main.GRAPH_SIZE)));
			
		}
	}
	
	public String toString(){
		String result = "";
		for(int i=0; i<list.size();i++){
			result += Integer.toString(list.get(i).getL()) + " - " + Integer.toString(list.get(i).getR());
			result += "\n";
		}
			
		return result;
		
	}

}
