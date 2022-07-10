package models;

public class MyProcess {

	private String name;
	private int size;
	private double time;
	private boolean locked;

	public MyProcess(String name, double time,int size, boolean locked ) {
		super();
		this.name = name;
		this.time = time;
		this.size = size;
		this.locked = locked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = (this.time-time);
	}

	public void updateTime(int time){
		this.time = time;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public boolean isLocked() {
		return locked;
	}
		
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
}
