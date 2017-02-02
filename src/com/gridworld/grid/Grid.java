/*
 * Authors: Joseph Kotzker and Esther Shimanovich

Initialized Array of grid_squares that includes cost dictionaries
 *
 */
package com.gridworld.grid;

import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import com.gridworld.*;

public class Grid {

	GridSquare[][] GridSquares;
	HashMap<String, Double> whiteCosts = new HashMap<String, Double>();
	HashMap<String, Double> lightgrayCosts = new HashMap<String, Double>();
	int oneHundredCells = 0;
	Coordinates sStart = new Coordinates(-1, -1);
	Coordinates sGoal = new Coordinates(-1, -1);

	public Grid() {

		whiteCosts.put("horiz", 0.5);
		whiteCosts.put("vert", 0.5);
		whiteCosts.put("diagonal", Math.pow(2, 0.5) / 2);

		lightgrayCosts.put("horiz", 1.0);
		lightgrayCosts.put("vert", 1.0);
		lightgrayCosts.put("diagonal", Math.pow(2, 0.5));

		this.GridSquares = new GridSquare[160][120];
		for (int i = 0; i < 160; i++) {
			for (int j = 0; j < 120; j++) {
				GridSquares[i][j] = new GridSquare(i, j, Color.WHITE);
			}
		}

		// Step 2: Placement of Hard Cells
		// Choose 8 random coordinates
		for (int n = 0; n < 8; n++) {
			int x = randomNumberGenerator(0, 119);
			int y = randomNumberGenerator(0, 159);
			// For each coordinate, set 31x31 blocks 50% chance light gray
			for (int i = inRange(x - 15, 119); i < inRange(x + 15, 119); i++) {
				for (int j = inRange(y - 15, 159); j < inRange(y + 15, 159); j++) {
					if (percentChance(50)) {
						this.GridSquares[i][j].setColor(Color.LIGHT_GRAY);
					}
				}
			}

		}

		// Step 3: Set up Highway
		Stack<Coordinates> highwayStack = new Stack<Coordinates>();
		Coordinates highwayStart = new Coordinates(-1, -1);
		int Counter = 0;
		while (highwayStack.size() < 5) {
			Counter++;
			if (Counter > 25) {
				clearHighways();
				highwayStack.clear();
				Counter = 0;
			}
			int x = randomNumberGenerator(0, 119);
			int y = randomNumberGenerator(0, 159);
			if (percentChance(50)) {
				y = randomNumberGenerator(0, 1) * 159;
				highwayStart = new Coordinates(x, y);
				y = markHoriz(x, y, 20, true, 0);
			} else {
				x = randomNumberGenerator(0, 1) * 119;
				highwayStart = new Coordinates(x, y);
				x = markVert(x, y, 20, true, 0);
			}
			highwayStack.push(highwayStart);
			if (x == -1 || y == -1) {
				highwayStack.pop();
				x = -2;
			}
			while (x != -2 || y != -2) {
				if (percentChance(60)) {
					if (highwayStack.peek().XVal == x) {
						y = markHoriz(x, y, 20, true, 0);
					} else {
						x = markVert(x, y, 20, true, 0);
					}
				} else {
					if (percentChance(50)) {
						if (highwayStack.peek().XVal == x) {
							y = markVert(x, y, 20, true, 0);
						} else {
							x = markHoriz(x, y, 20, true, 0);
						}
					} else {
						if (highwayStack.peek().XVal == x) {
							y = markVert(x, y, -20, true, 0);
						} else {
							x = markHoriz(x, y, -20, true, 0);
						}
					}
				}
				if (x == -1 || y == -1) {
					highwayStack.pop();
				}
			}
		}

		// Step 4: Select 20% cells to be blocked
		int i = 0;
		while (i < 0.20 * 3850) {
			int x = randomNumberGenerator(0, 119);
			int y = randomNumberGenerator(0, 159);
			if (!this.GridSquares[x][y].memberOfHorizontalHighway
					&& !this.GridSquares[x][y].memberOfHorizontalHighway) {
				this.GridSquares[x][y].setColor(Color.DARK_GRAY);
				i++;
			}
		}
		
		double distance = 0;
		// Step 5: Select sStart and sGoal
		do{
		this.sStart = generateStartOrGoal();
		this.sGoal = generateStartOrGoal();
		distance = Math.pow((this.sStart.XVal-this.sGoal.XVal),2)+ Math.pow((this.sStart.YVal-this.sGoal.YVal),2);
		} while(distance>100);

	}



	private int randomNumberGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max) + min;
	}

	private int inRange(int val, int max) {
		if (val < 0) {
			return 0;
		}
		if (val > max) {
			return max;
		}
		return val;
	}

	private boolean percentChance(int percent) {
		if (randomNumberGenerator(0, 100) < percent) {
			return true;
		}
		return false;
	}

	/*
	 * Creates horizontal highways. author: Esther Shimanovich
	 */
	private int markHoriz(int row, int col, int blocksToTraverse, boolean markHighway, int direction) {
		// Direction -1 means LEFT, +1 means RIGHT, 0 means BORDER
		// If we are on highway already, and we wanted to build, then exit
		if ((this.GridSquares[row][col].memberOfHorizontalHighway || this.GridSquares[row][col].memberOfVerticalHighway)
				&& markHighway == true) {
			return -1;
		}
		// If on border, set direction correctly
		if (col == 0 || col == 159) {
			direction = 2 * col / 159 - 1;
		}
		// Now we traverse
		int n = 0;
		while (n < blocksToTraverse) {
			this.GridSquares[row][col + n * direction].memberOfHorizontalHighway = markHighway;
			oneHundredCounter(markHighway);
			// Restart path if Border or Intersection on next block
			int nextCol = inRange(col + (n + 1) * direction, 159);
			boolean hitIntersection = (this.GridSquares[row][nextCol].memberOfHorizontalHighway == true
					|| this.GridSquares[row][nextCol].memberOfVerticalHighway == true) && markHighway == true;
			boolean hitBorder = nextCol != col + (n + 1) * direction;
			if ((hitBorder && this.oneHundredCells < 100) || hitIntersection) {
				markHoriz(row, col + n * direction, n, false, -1 * direction);
				return -1;
			}
			if (hitBorder && this.oneHundredCells >= 100) {
				return -2;
			}
			// Otherwise, continue traversing
			n++;
		}
		return col + blocksToTraverse * direction;
	}

	/*
	 * Creates vertical highways. author: Esther Shimanovich
	 */
	private int markVert(int row, int col, int blocksToTraverse, boolean markHighway, int direction) {
		// Direction -1 means DOWN, +1 means UP, 0 means BORDER
		// If we are on highway already, and we wanted to build, then exit
		if ((this.GridSquares[row][col].memberOfHorizontalHighway || this.GridSquares[row][col].memberOfVerticalHighway)
				&& markHighway == true) {
			return -1;
		}
		// If on border, set direction correctly
		if (row == 0 || row == 119) {
			direction = 2 * col / 119 - 1;
		}
		// Now we traverse
		int n = 0;
		while (n < blocksToTraverse) {
			this.GridSquares[row + n * direction][col].memberOfVerticalHighway = markHighway;
			oneHundredCounter(markHighway);
			// Restart path if Border or Intersection on next block
			int nextRow = inRange(row + (n + 1) * direction, 119);
			boolean hitIntersection = (this.GridSquares[nextRow][col].memberOfHorizontalHighway == true
					|| this.GridSquares[nextRow][col].memberOfVerticalHighway == true) && markHighway == true;
			boolean hitBorder = nextRow != row + (n + 1) * direction;
			if ((hitBorder && this.oneHundredCells < 100) || hitIntersection) {
				markVert(row + n * direction, col, n, false, -1 * direction);
				return -1;
			}
			if (hitBorder && this.oneHundredCells >= 100) {
				return -2;
			}
			// Otherwise, continue traversing
			n++;
		}
		return row + blocksToTraverse * direction;
	}

	private void oneHundredCounter(boolean changeCounter) {
		if (changeCounter == true) {
			this.oneHundredCells++;
			return;
		}
		this.oneHundredCells--;
		return;
	}

	private void clearHighways() {
		for (int i = 0; i < 119; i++) {
			for (int j = 0; j < 119; j++) {
				this.GridSquares[i][j].memberOfHorizontalHighway = false;
				this.GridSquares[i][j].memberOfVerticalHighway = false;
			}
		}
	}

	private Coordinates generateStartOrGoal() {
		int x = -1;
		int y = -1;
		do {
			y = randomNumberGenerator(0, 159);
			if (percentChance(50)) {
				x = randomNumberGenerator(0, 19);
			} else {
				x = randomNumberGenerator(99, 119);
			}
		} while (this.GridSquares[x][y].color.equals(Color.DARK_GRAY));
		return new Coordinates(x, y);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.GridSquares.toString();
	}

}
