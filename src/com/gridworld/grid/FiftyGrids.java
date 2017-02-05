package com.gridworld.grid;

import com.gridworld.exceptions.CoordinateException;

public class FiftyGrids {
	Grid[] FiftyGrids = new Grid[50];

	public void FiftyGrids() throws CoordinateException {

		for (int i = 0; i < 5; i++) {
			Grid A = new Grid();
			for (int j = 0; i < 10; j++) {
				A.newStartGoal();
				this.FiftyGrids[(i * 10) + j] = A;
			}
		}
	}
}
