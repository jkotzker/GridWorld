package com.gridworld.grid;

import java.util.ArrayList;

import com.gridworld.exceptions.CoordinateException;

public class FiftyGrids {
	public ArrayList<Grid> gridsList = new ArrayList<Grid>();

	public FiftyGrids(int heuristicVal, int searchVal) throws CoordinateException {

		for (int i = 0; i < 5; i++) {// i should be <5
			Grid A = new Grid();
			//System.out.println("Generated map number " + Integer.toString(i));
			A.newStartGoal();
			//System.out.println("Generated starts and goals for map number " + Integer.toString(i));
			A.performAllSearches(heuristicVal, searchVal);
			//System.out.println("Generated paths for map number " + Integer.toString(i));
			A.name = Integer.toString(i);
			gridsList.add(A);
		}
	}
}
