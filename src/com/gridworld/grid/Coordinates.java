package com.gridworld.grid;

public class Coordinates {
	
	public int XVal;
	public int YVal;
	
	public Coordinates(int x, int y) {
		this.XVal = x;
		this.YVal = y;
	}
	
	public void setX(int newx){
		this.XVal = newx;
	}
	
	public void setY(int newy){
		this.YVal = newy;
	}

}
