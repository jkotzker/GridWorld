package com.gridworld.algorithm;

import com.gridworld.grid.GridSquare;

public class Heuristics {
	// Weight 1
	private static double weight = 1;

	// Heuristics are stored here
	public static double H(Vertex V, int i) {
		/*
		 * I (Joey) am operating under the assumption that the integer value i
		 * selects which heuristic algorithm to use. The value returned will be
		 * the h value.
		 *
		 * I modified the method signature so that I could get the goal also.
		 */

		int currX = V.block.coordinates.XVal;
		int currY = V.block.coordinates.YVal;

		int currSearch = V.block.currentGrid.searchIterator;
		int goalX = V.block.currentGrid.pathPoints.get(currSearch).sGoal.XVal;
		int goalY = V.block.currentGrid.pathPoints.get(currSearch).sGoal.YVal;

		if (i == 5) {
			return 0;
		}
		if (i == 0) {

			/*
			 * This heuristic is the one we wrote as admissible and consistent
			 * in our report. It assumes straight-line distance and all movement
			 * along a highway.
			 */

			int xdif = goalX - currX;
			int ydif = goalY - currY;

			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = 0.25 * dist;
			return H;
		}
		if (i == 2) {

			/*
			 * This heuristic is the one we wrote as inadmissible; it assumes
			 * all diagonal movement.
			 */

			int xdif = goalX - currX;
			int ydif = goalY - currY;

			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = Math.sqrt(2) * dist;
			return H;
		}
		if (i == 3) {

			/*
			 * This heuristic, rather than determining straight-line distance
			 * via the distance formula, attempts to estimate the number of
			 * blocks, and then uses the highway movement value to multiply.
			 */

			int xdif = goalX - currX;
			int ydif = goalY - currY;

			double H = 0.25 * xdif + 0.25 * ydif;

			return H;
		}
		if (i == 4) {

			/*
			 * This heuristic, rather than determining straight-line distance
			 * via the distance formula, attempts to estimate the number of
			 * blocks, and then uses an unrealistically low value to multiply.
			 * This heuristic will be admissible because of its gross
			 * underestimation.
			 */

			int xdif = goalX - currX;
			int ydif = goalY - currY;

			double H = 0.01 * xdif + 0.01 * ydif;

			return H;
		}
		if (i == 4) {

			/*
			 * This heuristic takes a very roughly computed "average cost" of
			 * traversal between any two given cells in the grid, and multiples
			 * that by the straight-line distance.
			 */

			// Roughly computed average cost of traversal for vertices in any
			// given grid; if we stored the exact counts of each type of cell,
			// could be more exact
			double avgCost = 1.5931;

			// Use it with the difference formula

			int xdif = goalX - currX;
			int ydif = goalY - currY;

			double dist = Math.sqrt((xdif * xdif) + (ydif * ydif));
			double H = avgCost * dist;
			return H;

		}

		return 1;
	}

	// Get key value

	public static void storeNewKey(Vertex V, int i) {
		V.setH(i, weight * H(V, i));
	}

	// used only for search 3
	public static double Key(Vertex V, int i) {
		return V.getG(i) + weight * H(V, i);
	}
}
