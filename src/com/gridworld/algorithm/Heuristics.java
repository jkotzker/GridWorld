package com.gridworld.algorithm;

import com.gridworld.grid.GridSquare;

public class Heuristics {
	// Weight
	private double weight;

	// Heuristics are stored here
	private double H(Vertex V, GridSquare goal, int i) {
		/*
		 * I (Joey) am operating under the assumption that the integer value i selects which heuristic algorithm to use.
		 * The value returned will be the h value.
		 * 
		 * I modified the method signature so that I could get the goal also. 
		 */
		if (i == 0) {
			return 0;
		}
		if (i == 1) {
			
			/*
			 * This heuristic is the one we wrote as admissible and consistent in our report.
			 * It assumes straight-line distance and all movement along a highway.
			 */
			
			int currX = V.block.coordinates.XVal;
			int currY = V.block.coordinates.YVal;
			
			int goalX = goal.coordinates.XVal;
			int goalY = goal.coordinates.YVal;
			
			int xdif = goalX - currX;
			int ydif = goalY - currY;
			
			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = 0.25 * dist;
			return H;
		}
		if (i == 2) {
			
			/*
			 * This heuristic is the one we wrote as inadmissible; it assumes all diagonal movement.
			 */
			
			int currX = V.block.coordinates.XVal;
			int currY = V.block.coordinates.YVal;
			
			int goalX = goal.coordinates.XVal;
			int goalY = goal.coordinates.YVal;
			
			int xdif = goalX - currX;
			int ydif = goalY - currY;
			
			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = Math.sqrt(2) * dist;
			return H;
		}
		if (i == 3) {
			
			/*
			 * This heuristic, rather than determining straight-line distance via the distance formula,
			 * attempts to estimate the number of blocks, and then uses the highway movement value to multiply. 
			 */
			
			int currX = V.block.coordinates.XVal;
			int currY = V.block.coordinates.YVal;
			
			int goalX = goal.coordinates.XVal;
			int goalY = goal.coordinates.YVal;
			
			int xdif = goalX - currX;
			int ydif = goalY - currY;
			
			double H = 0.25*xdif + 0.25*ydif;

			return H;
		}
		if (i == 4) {
			
			/*
			 * This heuristic, rather than determining straight-line distance via the distance formula,
			 * attempts to estimate the number of blocks, and then uses an unrealistically low value to multiply.
			 * This heuristic will be admissible because of its gross underestimation.  
			 */
			
			int currX = V.block.coordinates.XVal;
			int currY = V.block.coordinates.YVal;
			
			int goalX = goal.coordinates.XVal;
			int goalY = goal.coordinates.YVal;
			
			int xdif = goalX - currX;
			int ydif = goalY - currY;
			
			double H = 0.01*xdif + 0.01*ydif;

			return H;
		}
		if (i == 5) {
			
			/*
			 * This heuristic takes a very roughly computed "average cost" of traversal between any two given cells in the grid, and multiples that by the straight-line distance. 
			 */
			
			// Roughly computed average cost of traversal for vertices in any given grid; if we stored the exact counts of each type of cell, could be more exact
			double avgCost = 1.5931;
			
			// Use it with the difference formula
			int currX = V.block.coordinates.XVal;
			int currY = V.block.coordinates.YVal;
			
			int goalX = goal.coordinates.XVal;
			int goalY = goal.coordinates.YVal;
			
			int xdif = goalX - currX;
			int ydif = goalY - currY;
			
			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = avgCost * dist;
			return H;
			
		}
		
		
		
		return 1;
	}

	// Get key value
	public void storeNewKey(Vertex V, GridSquare sq, int i, double w) {
		this.weight = w;
		V.key = V.g + weight * H(V, sq, i);
	}
}
