
package com.gridworld.algorithm;

import com.gridworld.grid.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Vertex {
	public boolean inFringe = false;
	public LinkedList<Double> G = new LinkedList<Double>();
	// This h is only used in the first Search
	private double h = 0;
	private LinkedList<Double> key = new LinkedList<Double>();
	public LinkedList<ArrayList<GridSquare>> BP = new LinkedList<ArrayList<GridSquare>>();
	public boolean closed = false;

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
				}
			}
		}
		return succ;

	}

	public Vertex(GridSquare currentBlock, Grid currentGrid, String searchType) {
		this.setG(0, Double.POSITIVE_INFINITY);
		this.block = currentBlock;
		if (currentBlock != null) {
			currentBlock.SearchVertex = this;
		}
		this.currentGrid = currentGrid;
		this.searchType = searchType;
		if (searchType == "A*") {
			this.h = 1;
		} else if (searchType == "wA*") {
			this.h = 2;
		}
		this.h = 0;
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
		return key.get(i);
	}

	public void setKey(int i, double key) {
		while (this.key.size() < i + 1) {
			this.key.add(null);
		}
		this.key.set(i, key);
	}

	public double getG(int i) {
		return this.G.get(i);
	}

	public void setG(int i, double g) {
		while (this.G.size() < i + 1) {
			this.G.add(null);
		}
		this.G.set(i, g);
		this.setKey(i, g + this.h);
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
		this.key.set(0, this.getG(0) + this.h);
	}

}
