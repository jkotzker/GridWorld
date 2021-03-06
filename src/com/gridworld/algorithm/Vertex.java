package com.gridworld.algorithm;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.grid.*;
import java.util.LinkedList;
import com.gridworld.grid.Coordinates;

public class Vertex {
	public boolean inFringe = false;
	public double g = Double.POSITIVE_INFINITY;
	public double h = 0;
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
					Vertex gridVert = currentGrid.GridSquares[this.block.coordinates.XVal
							+ x][this.block.coordinates.YVal + y].SearchVertex;
					if (gridVert == null) {
						currentGrid.GridSquares[this.block.coordinates.XVal + x][this.block.coordinates.YVal
								+ y].SearchVertex = new Vertex(
										currentGrid.GridSquares[this.block.coordinates.XVal
												+ x][this.block.coordinates.YVal + y],
										currentGrid, this.searchType);
						succ.add(currentGrid.GridSquares[this.block.coordinates.XVal + x][this.block.coordinates.YVal
						                                  								+ y].SearchVertex);
					}
				}
			}
		}
		return succ;

	}

	public Vertex(GridSquare currentBlock, Grid currentGrid, String searchType) {
		this.block = currentBlock;
		this.currentGrid = currentGrid;
		this.searchType = searchType;
		if (searchType == "A*") {
			this.h = 1;
		} else if (searchType == "wA*") {
			this.h = 2;
		}
		this.h = 0;
	}
}
