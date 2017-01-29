/*
 * Authors: Joseph Kotzker and Esther Shimanovich

Initialized Array of grid_squares that includes cost dictionaries
 *
 */
package com.gridworld.grid;

import com.gridworld.*;

public class Grid {
	
	GridSquare[][] GridSquares;
	Object[][] white  = {{"horiz","vert","diagonal"},{0.5, 0.5, Math.pow(2, 0.5)/2}};
	Object[][] lightGray  = {{"horiz","vert","diagonal"},{1, 1, Math.pow(2, 0.5)}};
	
	public Grid(){
		this.GridSquares = new GridSquare[160][120];
		for(int i = 0; i<160; i++){
			for(int j = 0; j<120; j++){
				GridSquares[i][j]= new GridSquare(i,j,Color.WHITE);
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.GridSquares.toString();
	}
	
}
