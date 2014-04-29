package myPkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Individual {
	int codedRoute[];
	int length;
	Random random = new Random();
	
	Individual(int size){
		length = size;
		codedRoute = new int[size];
		for(int i=0; i<size; i++){
			codedRoute[i] = random.nextInt(size - i );
		}	
	}
	int [] getRoute(){
		ArrayList<Integer> auxiliaryList = new ArrayList<Integer>();
		for(int i=0; i<length; i++){
			auxiliaryList.add(i);
		}
		int routeTab[] = new int[length];
		for(int i=0; i<length; i++){
			routeTab[i] = auxiliaryList.get( codedRoute[i] );
			auxiliaryList.remove(codedRoute[i]);
		}
		return routeTab;
	}
	@Override
	public String toString() {
		return "Individual [codedRoute=" + Arrays.toString(codedRoute)
				+ "]";
	}
	
	public static void main(String [] args){
		Individual nowy = new Individual(5);
		System.out.println(nowy);
		int tab[]=nowy.getRoute();
		for (int i : tab) {
			System.out.print(i+" ");
		}
	}
	
	
}
