package com.gridworld.grid;

import java.util.ArrayList;

public class CoordinatePair {
	
	public Grid parent;
	
	public Coordinates sStart = null;
	public Coordinates sGoal = null;
	
	public ArrayList<GridSquare> path;
	
	public CoordinatePair(Coordinates start, Coordinates goal) {
		
		this.sStart = start;
		this.sGoal = goal;
		
	}
	
	public String toString() {
		return this.parent.name + " ( " + sStart.XVal + ", " + sStart.YVal + " ) ," + " ( " + sGoal.XVal + ", " + sGoal.YVal + " ) ";
	}

}
