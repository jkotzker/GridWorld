package com.gridworld.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import com.gridworld.algorithm.Search;
import com.gridworld.exceptions.CoordinateException;
import com.gridworld.exceptions.TraversalException;

public class Grid {

	public String name = "";
	public GridSquare[][] GridSquares = new GridSquare[120][160];
	public ArrayList<CoordinatePair> pathPoints = new ArrayList<CoordinatePair>();
	private Stack<Coordinates> highwayBlocks;

	public Grid() throws CoordinateException {
		// System.out.println("Making grid");
		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
				GridSquares[i][j] = new GridSquare(i, j, SquareColor.WHITE);
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
						this.GridSquares[i][j].setColor(SquareColor.LIGHT_GRAY);
					}
				}
			}
		}
		// Step 3: Set up Highway
		Stack<Coordinates> highwayStack = new Stack<Coordinates>();
		Coordinates highwayStart = new Coordinates(0, 0);
		int Counter = 0;
		while (highwayStack.size() < 4) {
			this.highwayBlocks = new Stack<Coordinates>();
			Counter++;
			if (Counter > 25) {
				Counter = clearHighways();
				highwayStack.clear();
			}
			int x = randomNumberGenerator(0, 120);
			int y = randomNumberGenerator(0, 160);
			if (percentChance(50)) {
				y = randomNumberGenerator(0, 2) * 159;
				highwayStart = new Coordinates(x, y);
				y = markHighway(x, y, (-2 * (y / 159)) + 1, "horiz");
			} else {
				x = randomNumberGenerator(0, 2) * 119;
				highwayStart = new Coordinates(x, y);
				x = markHighway(x, y, (-2 * (x / 159)) + 1, "vert");
			}
			highwayStack.push(highwayStart);
			int xInit = highwayStack.peek().XVal;
			int yInit = highwayStack.peek().YVal;
			if (x == -1 || y == -1) {
				highwayStack.pop();
				x = -2;
			} else {
				highwayStack = HighwayTraveler(xInit, x, yInit, y, highwayStack, 0);
			}
		}

		// Step 4: Select 20% cells to be blocked
		int i = 0;
		while (i < 0.20 * 160 * 120) {
			int x = randomNumberGenerator(0, 119);
			int y = randomNumberGenerator(0, 159);
			if (!this.GridSquares[x][y].memberOfHorizontalHighway
					&& !this.GridSquares[x][y].memberOfHorizontalHighway) {
				this.GridSquares[x][y].setColor(SquareColor.DARK_GRAY);
				i++;
			}
		}
	}

	public void newStartGoal() throws CoordinateException {

		int i = 0;
		while (i < 10) {
			double distance = 0;
			Coordinates sStart = generateStartOrGoal();
			Coordinates sGoal = generateStartOrGoal();
			CoordinatePair newPair = new CoordinatePair(sStart, sGoal);
			distance = Math.pow((sStart.XVal - sGoal.XVal), 2) + Math.pow((sStart.YVal - sGoal.YVal), 2);
			if (distance > 100) {
				newPair.parent = this;
				this.pathPoints.add(newPair);
				i++;
			} else {
				continue;
			}

		}

	}

	public void performAllSearches() {

		for (int i = 0; i < pathPoints.size(); i++) {
			Search search = new Search();
			ArrayList<GridSquare> results = search.performSearch(this, i);
			if (results != null)
				pathPoints.get(i).path.addAll(results);
		}

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

	private Stack<Coordinates> HighwayTraveler(int xInit, int x, int yInit, int y, Stack<Coordinates> highwayStack,
			int iter) throws CoordinateException {
		// Save inputValues
		iter++;
		while (x != -2 && y != -2) {
			int xInitS = xInit;
			int xS = x;
			int yInitS = yInit;
			int yS = y;
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
			if (iter == 30) {
				clearThisHighway();
				highwayStack.pop();
				this.highwayBlocks.clear();
				return highwayStack;
			}
			if (x == -1 || y == -1) {
				HighwayTraveler(xInitS, xS, yInitS, yS, highwayStack, iter);
				return highwayStack;
			}

		}
		this.highwayBlocks.clear();
		return highwayStack;
	}

	/*
	 * Creates horizontal highways. author: Esther Shimanovich
	 */
	private int markHighway(int row, int col, int direction, String horizOrVert) throws CoordinateException {
		// Direction -1 means LEFT/DOWN, +1 means RIGHT/UP, 0 means BORDER
		// If we are on highway already, and we wanted to build, then exit
		Stack<Coordinates> H = new Stack<Coordinates>();
		if (this.GridSquares[row][col].memberOfHorizontalHighway
				|| this.GridSquares[row][col].memberOfVerticalHighway) {
			return -1;
		}

		int c = 0;
		int r = 0;
		while (c < 20 && r < 20) {
			int nextCol = inRange((col + ((c + 1) * direction)), 159);
			int nextRow = inRange((row + ((r + 1) * direction)), 119);
			boolean hitIntersection = (this.GridSquares[row][nextCol].memberOfHorizontalHighway == true
					|| this.GridSquares[row][nextCol].memberOfVerticalHighway == true);
			boolean hitBorder = false;
			if (horizOrVert == "horiz") {
				this.GridSquares[row][col + c * direction].memberOfHorizontalHighway = true;
				Coordinates newC = new Coordinates(row, col + c * direction);
				this.highwayBlocks.push(newC);
				H.push(newC);
				hitBorder = (nextCol != col + (c + 1) * direction);
			} else {
				this.GridSquares[row + r * direction][col].memberOfVerticalHighway = true;
				Coordinates newC = new Coordinates(row + r * direction, col);
				this.highwayBlocks.push(newC);
				H.push(newC);
				hitBorder = (nextRow != (row + (r + 1) * direction));
				hitIntersection = (this.GridSquares[nextRow][col].memberOfHorizontalHighway == true
						|| this.GridSquares[nextRow][col].memberOfVerticalHighway == true);
			}
			// Restart path if Border or Intersection on next block
			if (hitBorder && this.highwayBlocks.size() >= 100) {
				return -2;
			}
			if ((hitBorder && this.highwayBlocks.size() < 100) || hitIntersection) {
				// System.out.println("I think I hit the border");
				clearThisHighway(H);
				return -1;
			}
			// Otherwise, continue traversing
			c++;
			r++;
		}

		if (horizOrVert == "horiz") {
			return col + 20 * direction;

		} else {
			return row + 20 * direction;
		}

	}

	private void clearThisHighway() {
		Coordinates C = null;
		// System.out.println("Got to clear this Highways");
		while (!this.highwayBlocks.empty()) {
			C = this.highwayBlocks.pop();
			this.GridSquares[C.XVal][C.YVal].memberOfHorizontalHighway = false;
			this.GridSquares[C.XVal][C.YVal].memberOfVerticalHighway = false;

		}
		return;
	}

	private void clearThisHighway(Stack<Coordinates> H) {
		Coordinates C = null;
		while (!H.empty()) {

			C = H.pop();
			this.GridSquares[C.XVal][C.YVal].memberOfHorizontalHighway = false;
			this.GridSquares[C.XVal][C.YVal].memberOfVerticalHighway = false;
			if (C == this.highwayBlocks.peek()) {
				this.highwayBlocks.pop();
			}
		}
		return;
	}

	private int clearHighways() {
		for (int i = 0; i < 119; i++) {
			for (int j = 0; j < 119; j++) {
				this.GridSquares[i][j].memberOfHorizontalHighway = false;
				this.GridSquares[i][j].memberOfVerticalHighway = false;
			}
		}
		return 0;
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
		} while (this.GridSquares[x][y].color.equals(SquareColor.DARK_GRAY));
		return new Coordinates(x, y);
	}

	private int getDirection(int xInit, int x) {
		if (xInit < x) {
			return 1;
		}
		return -1;
	}

	public void SaveVertices() {
		for (int row = 0; row < 120; row++) {
			for (int col = 0; col < 160; col++) {
				this.GridSquares[row][col].SavedVertices.push(this.GridSquares[row][col].SearchVertex);

			}
		}
	}


	@Override
	public String toString() {
		return this.name;
	}

}
