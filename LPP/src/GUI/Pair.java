package GUI;

public class Pair<T1, T2> {
	private T1 x;
	private T2 y;
	public Pair(T1 x2, T2 y2){
		x = x2;
		y = y2;
	}
	public void setX(T1 x) {
		this.x = x;
	}
	public void setY(T2 y) {
		this.y = y;
	}
	
	public T1 getX(){
		return x;
	}
	public T2 getY(){
		return y;
	}
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
		
	}
	
	
	
	
}
