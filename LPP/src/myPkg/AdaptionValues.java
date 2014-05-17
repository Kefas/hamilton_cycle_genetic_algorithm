package myPkg;

public class AdaptionValues {
	long best;
	long average;
	long worst;
	public long getBest() {
		return best;
	}
	public void setBest(long best) {
		this.best = best;
	}
	public long getAverage() {
		return average;
	}
	public void setAverage(long average) {
		this.average = average;
	}
	public long getWorst() {
		return worst;
	}
	public void setWorst(long worst) {
		this.worst = worst;
	}	
	@Override	
	public String toString(){
		return "Best: "+best+"\nAverage: "+average+"\nWorst: "+worst;
	}
}
