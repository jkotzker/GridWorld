package com.gridworld.grid;

import java.util.ArrayList;

public class CoordinatePair {
	
	public Coordinates sStart = null;
	public Coordinates sGoal = null;
	
	public ArrayList<GridSquare> path;
	
	public CoordinatePair(Coordinates start, Coordinates goal) {
		
		this.sStart = start;
		this.sGoal = goal;
		
	}

}
