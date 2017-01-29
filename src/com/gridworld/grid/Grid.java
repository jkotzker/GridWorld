/*
 * Authors: Joseph Kotzker and Esther Shimanovich

Initialized Array of grid_squares that includes cost dictionaries
 *
 */
package com.gridworld.grid;

import java.util.HashMap;

import com.gridworld.*;

public class Grid {
	
	GridSquare[][] GridSquares;
	HashMap<String,Double> whiteCosts = new HashMap<String,Double>();
	HashMap<String,Double> lightgrayCosts = new HashMap<String,Double>();
	
	public Grid(){
		
		whiteCosts.put("horiz", 0.5);
		whiteCosts.put("vert", 0.5);
		whiteCosts.put("diagonal", Math.pow(2, 0.5)/2);
		
		lightgrayCosts.put("horiz", 1.0);
		lightgrayCosts.put("very", 1.0);
		lightgrayCosts.put("diagonal", Math.pow(2, 0.5));
		
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
