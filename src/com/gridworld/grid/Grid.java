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
import com.gridworld.exceptions.CoordinateException;

public class Grid {

	public GridSquare[][] GridSquares = new GridSquare[120][160];
	public GridSquare sStart = null;
	public GridSquare sGoal = null;
	private HashMap<String, Double> whiteCosts = new HashMap<String, Double>();
	private HashMap<String, Double> lightgrayCosts = new HashMap<String, Double>();
	private Stack<Coordinates> highwayBlocks;

	public Grid() throws CoordinateException {

		whiteCosts.put("horiz", 0.5);
		whiteCosts.put("vert", 0.5);
		whiteCosts.put("diagonal", Math.pow(2, 0.5) / 2);

		lightgrayCosts.put("horiz", 1.0);
		lightgrayCosts.put("vert", 1.0);
		lightgrayCosts.put("diagonal", Math.pow(2, 0.5));

		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
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
		Coordinates highwayStart = new Coordinates(0, 0);
		int Counter = 0;
		while (highwayStack.size() < 5) {
			this.highwayBlocks = new Stack<Coordinates>();
			this.oneHundredCells = 0;
			Counter++;
			if (Counter > 25) {
				clearHighways();
				highwayStack.clear();
				Counter = 0;
			}
			int x = randomNumberGenerator(0, 120);
			int y = randomNumberGenerator(0, 160);
			if (percentChance(50)) {
				y = randomNumberGenerator(0, 2) * 159;
				highwayStart = new Coordinates(x, y);
				y = markHighway(x, y, 0, "horiz");
			} else {
				x = randomNumberGenerator(0, 2) * 119;
				highwayStart = new Coordinates(x, y);
				x = markHighway(x, y, 0, "vert");
			}
			highwayStack.push(highwayStart);
			int xInit = highwayStack.peek().XVal;
			int yInit = highwayStack.peek().YVal;
			if (x == -1 || y == -1) {
				highwayStack.pop();
				x = -2;
			}
			while (x != -2 && y != -2) {
				if (percentChance(60)) {
					if (xInit == x) {
						int direction = getDirection(yInit, y);
						yInit = y;
						y = markHighway(x, y, direction, "horiz");
					} else {
						int direction = getDirection(xInit, x);
						xInit = x;
						x = markHighway(x, y, direction, "vert");
					}
				} else {
					if (percentChance(50)) {
						if (xInit == x) {
							yInit = y;
							x = markHighway(x, y, 1, "vert");
						} else {
							xInit = x;
							y = markHighway(x, y, 1, "horiz");
						}
					} else {
						if (xInit == x) {
							yInit = y;
							x = markHighway(x, y, -1, "vert");
						} else {
							xInit = x;
							y = markHighway(x, y, -1, "horiz");
						}
					}
				}
				if (x == -1 || y == -1) {
					highwayStack.pop();
					x = -2;
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
	}

	public void newStartGoal() throws CoordinateException {
		double distance = 0;
		// Step 5: Select sStart and sGoal
		do {
			Coordinates sStart = generateStartOrGoal();
			Coordinates sGoal = generateStartOrGoal();
			this.sStart = this.GridSquares[sStart.XVal][sStart.YVal];
			this.sGoal = this.GridSquares[sGoal.XVal][sGoal.YVal];
			distance = Math.pow((sStart.XVal - sGoal.XVal), 2) + Math.pow((sStart.YVal - sGoal.YVal), 2);
		} while (distance > 100);

	}

	private int randomNumberGenerator(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min) + min;
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
		if (randomNumberGenerator(0, 101) < percent) {
			return true;
		}
		return false;
	}

	/*
	 * Creates horizontal highways. author: Esther Shimanovich
	 */
	private int markHighway(int row, int col, int direction, String horizOrVert) throws CoordinateException {
		// Direction -1 means LEFT/DOWN, +1 means RIGHT/UP, 0 means BORDER
		// If we are on highway already, and we wanted to build, then exit

		if (this.GridSquares[row][col].memberOfHorizontalHighway
				|| this.GridSquares[row][col].memberOfVerticalHighway) {
			return -1;
		}

		// If on border, set direction correctly
		if (col == 0 || col == 159 && horizOrVert == "horiz") {
			direction = -2 * col / 159 + 1;
		}
		if (row == 0 || row == 119 && horizOrVert == "vert") {
			direction = -2 * row / 159 + 1;
		}
		// Now we traverse
		int c = 0;
		int r = 0;
		while (c < 20 && r < 20) {
			int nextCol = inRange(col + (c + 1) * direction, 159);
			int nextRow = inRange((row + (r + 1) * direction), 119);
			boolean hitIntersection = (this.GridSquares[row][nextCol].memberOfHorizontalHighway == true
					&& this.GridSquares[row][nextCol].memberOfVerticalHighway == true);
			if (horizOrVert == "horiz") {
				this.GridSquares[row][col + c * direction].memberOfHorizontalHighway = true;
				this.highwayBlocks.push(new Coordinates(row, col + c * direction));
			} else {
				this.GridSquares[row + r * direction][col].memberOfVerticalHighway = true;
				this.highwayBlocks.push(new Coordinates(row + r * direction, col));
				hitIntersection = (this.GridSquares[nextRow][col].memberOfHorizontalHighway == true
						&& this.GridSquares[nextRow][col].memberOfVerticalHighway == true);
			}
			// Restart path if Border or Intersection on next block

			boolean hitBorder = (nextCol != col + (c + 1) * direction) || (nextRow != (row + (r + 1) * direction));
			if ((hitBorder && this.highwayBlocks.size() < 100) || hitIntersection) {

				clearThisHighway();
				return -1;
			}
			if (hitBorder && this.highwayBlocks.size() >= 100) {
				return -2;
			}
			// Otherwise, continue traversing
			if (horizOrVert == "horiz") {
				c++;
			} else {
				r++;
			}
		}

		if (horizOrVert == "horiz") {
			return col + 20 * direction;

		} else {
			return row + 20 * direction;
		}

	}

	private void clearThisHighway() {
		while (!this.highwayBlocks.empty()) {
			Coordinates C = this.highwayBlocks.pop();
			this.GridSquares[C.XVal][C.YVal].memberOfHorizontalHighway = false;
			this.GridSquares[C.XVal][C.YVal].memberOfHorizontalHighway = false;
		}
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

	private Coordinates generateStartOrGoal() throws CoordinateException {
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

	private int getDirection(int xInit, int x) {
		if (xInit < x) {
			return 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.GridSquares.toString();
	}

}
