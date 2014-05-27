package myPkg;

public class AdaptationValues {
	double best;
	double average;
	double worst;
	int [] theBestCycle;
	
	public int[] getTheBestCycle() {
		return theBestCycle;
	}
	public void setTheBestCycle(int[] theBestCycle) {
		this.theBestCycle = theBestCycle;
	}
	public double getBest() {
		return best;
	}
	public void setBest(double best) {
		this.best = best;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getWorst() {
		return worst;
	}
	public void setWorst(double worst) {
		this.worst = worst;
	}	
	@Override	
	public String toString(){
		return "Best: "+best+"\nAverage: "+average+"\nWorst: "+worst;
	}
}
