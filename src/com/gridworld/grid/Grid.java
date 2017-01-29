/*
 * Authors: Joseph Kotzker and Esther Shimanovich

Initialized Array of grid_squares that includes cost dictionaries
 *
 */
package com.gridworld.grid;

import java.util.*;

import com.gridworld.grid.GridSquare;

public class Grid {
	GridSquare[][] GridSquares = new GridSquare[160][120];
	Object[][] white  = {{"horiz","vert","diagonal"},{0.5, 0.5, Math.pow(2, 0.5)/2}};
	Object[][] lightGray  = {{"horiz","vert","diagonal"},{1, 1, Math.pow(2, 0.5)}};
	
	Grid(){
		this.GridSquares = new GridSquare[160][120];
		for(int i = 0; i<160; i++){
			for(int j = 0; j<120; j++){
				this.GridSquares[i][j]= GridSquare(i,j,color.white);
			}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.GridSquares.toString();
	}
		
	}
	
}
