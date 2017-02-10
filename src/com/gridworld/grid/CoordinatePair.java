package com.gridworld.grid;

import java.util.ArrayList;

public class CoordinatePair {
	
	public Grid parent;

	public Coordinates sStart;
	public Coordinates sGoal;
	
	public ArrayList<GridSquare> path;
	
	public CoordinatePair(Coordinates start, Coordinates goal) {
		
		this.sStart = start;
		this.sGoal = goal;
		this.path = new ArrayList<GridSquare>();
	}
	
	public String toString() {
		return this.parent.name + " ( " + sStart.XVal + ", " + sStart.YVal + " ) ," + " ( " + sGoal.XVal + ", " + sGoal.YVal + " ) ";
	}

}
