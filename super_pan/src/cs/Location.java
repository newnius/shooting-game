package cs;


public class Location {
	
	private int i;
	private int j;
	private int g;                                          //gֵ
	private int h;                                          //hֵ
	private int f;                                           //fֵ
	private Location previous;
	
	public Location(int i,int j){
		this.i = i;
		this.j = j;
	}
	
	public boolean equals(Object o){
		if(o instanceof Location){
			Location l = (Location)o;
			if(l.i == this.i && l.j == this.j){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String desc = "["+i+","+j+"]";
		return desc;
	}
	
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g=g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setTotalEvalSteps(int f) {
		this.f = f;
	}

	public Location getPrevious() {
		return previous;
	}

	public void setPrevious(Location previous) {
		this.previous = previous;
	}
	
}
