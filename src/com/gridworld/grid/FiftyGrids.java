package com.gridworld.grid;

import java.util.ArrayList;

import com.gridworld.exceptions.CoordinateException;

public class FiftyGrids {
	public ArrayList<Grid> gridsList = new ArrayList<Grid>();

	public FiftyGrids() throws CoordinateException {

		for (int i = 0; i < 5; i++) {
			Grid A = new Grid();
			for (int j = 0; j < 10; j++) {
				A.newStartGoal();
				this.gridsList.add(A);
			}
		}
	}
}
