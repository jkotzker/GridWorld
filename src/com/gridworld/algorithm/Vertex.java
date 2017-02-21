
package com.gridworld.algorithm;

import com.gridworld.grid.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Vertex {
	// The following is used only in the first Search
	private Double[] h = new Double[10];
	public boolean inFringe = false;

	public Double[] g = new Double[10];
	public Double[] key = new Double[10];
	private GridSquare[] BP = new GridSquare[10];
	public boolean closed = false;
	public boolean[] inClosed = new boolean[10];
	public boolean[] inOpen = new boolean[10];
	public double v;

	public Vertex Parent = null;
	public GridSquare block = null;
	public Grid currentGrid = null;
	public LinkedList<Vertex> succ = new LinkedList<Vertex>();
	private String searchType = null;
	// || this.gridSquare.color == SquareColor.DARK_GRAY

	public LinkedList<Vertex> GetSucc() {
		succ = new LinkedList<Vertex>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				int row = this.block.coordinates.XVal + x;
				int col = this.block.coordinates.YVal + y;

				if (row < 120 && row >= 0 && col < 160 && col >= 0 && !(x == 0 && y == 0)) {
					Vertex gridVert = currentGrid.GridSquares[row][col].SearchVertex;
					if (gridVert == null) {
						succ.add(new Vertex(currentGrid.GridSquares[row][col], currentGrid, this.searchType));
					}
					if (gridVert != null && gridVert != this.Parent) {
						succ.add(gridVert);
					}
				}
			}
		}
		return succ;

	}

	public Vertex(GridSquare currentBlock, Grid currentGrid, String searchType) {
		this.block = currentBlock;
		if (currentBlock != null) {
			currentBlock.SearchVertex = this;
		}
		this.currentGrid = currentGrid;
		this.searchType = searchType;
<<<<<<< HEAD
		/*
		if (searchType == "A*") {
			this.h = 1;
		} else if (searchType == "wA*") {
			this.h = 2;
		}
		*/
		// JOEY, HERE IS WHERE YOU ADD THE HEURISTIC
		//Heuristics calculateH = new Heuristics();
		this.h = Heuristics.H(this, 1);
		this.key.add(Heuristics.Key(this, 1));
		//this.key = new LinkedList<Double>();
		//this.key.add(0.0);
||||||| merged common ancestors
		if (searchType == "A*") {
			this.h = 1;
		} else if (searchType == "wA*") {
			this.h = 2;
		}
		// JOEY, HERE IS WHERE YOU ADD THE HEURISTIC
		Heuristics calculateH = new Heuristics();
		this.h = calculateH.Key(this, 1);
		//this.key = new LinkedList<Double>();
		//this.key.add(0.0);
=======
	
		Arrays.fill(this.g, -1.0);
		Arrays.fill(this.key, null);
		Arrays.fill(this.BP, null);
		Arrays.fill(this.h, 0.0);
		Arrays.fill(this.inClosed, false);
		Arrays.fill(this.inOpen, false);

		this.setG(0, Double.POSITIVE_INFINITY);

		this.setH(0, Heuristics.Key(this, currentGrid.currentHeuristic));
		// this.key = new LinkedList<Double>();
		// this.key.add(0.0);
>>>>>>> origin/esther
	}

	public int compareTo(Vertex other, int i) {
		// if (this.key == Double.NaN) {
		/*
		 * if (this.h + this.g < other.h + other.g) {// TODO include this.g
		 * return -1; } return 0; /*}
		 */
		if (this.getKey(0) < other.getKey(0)) {
			return -1;
		}
		return 0;
	}

	public Double getKey(int i) {
		if (this.key[0] == null) {
			this.key[0] = 0.0;
		}
		/*
		 * while (this.key.size() < i + 1) { this.key.add(0.0); }
		 */
		return key[i];
	}

	public void setKey(int i, double key) {
		/*
		 * while (this.key.size() < i + 1) { this.key.add(null); }
		 */
		this.key[i] = key;
	}

	public double getG(int i) {
		/*
		 * while (this.g.size() < i + 1) { this.g.add(-1.0); }
		 */
		return this.g[i];
	}

	public void setG(int i, double g) {
		/*
		 * while (this.g.size() < i + 1) { this.g.add(-1.0); }
		 */
		this.g[i] = g;
		this.setKey(i, this.getH(i) + this.getG(i));
	}

<<<<<<< HEAD
	public double getH() {
		return this.h;
||||||| merged common ancestors
	public double getH() {
		return h;
=======
	public double getH(int i) {
		/*
		 * while (this.h.size() < i + 1) { this.h.add(0.0); }
		 */
		return this.h[i];
>>>>>>> origin/esther
	}

	public void setH(int i, double h) {
		/*
		 * while (this.h.size() < i + 1) { this.h.add(0.0); }
		 */
		this.h[i] = h;
		this.key[i] = this.getG(i) + this.getH(i);
	}

	public GridSquare getBP(int i) {
		return this.BP[i];
	}

	public void setBP(int i, GridSquare bP) {
		/*
		 * while (this.BP.size() < i + 1) { this.BP.add(null); }
		 */
		BP[i] = bP;
	}

}
