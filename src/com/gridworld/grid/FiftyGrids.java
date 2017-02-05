package com.gridworld.grid;

import java.util.ArrayList;

import com.gridworld.exceptions.CoordinateException;

public class FiftyGrids {
	public ArrayList<Grid> gridsList = new ArrayList<Grid>();

	public FiftyGrids() throws CoordinateException {

		for (int i = 0; i < 5; i++) {
			Grid A = new Grid();
			A.newStartGoal();
			A.name = Integer.toString(i);
		}
	}
}
