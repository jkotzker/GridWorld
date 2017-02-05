package com.gridworld.algorithm;

import com.gridworld.grid.*;
import java.util.LinkedList;

public class Vertex {
	public double g = Double.POSITIVE_INFINITY;
	public Vertex Parent = null;
	public GridSquare gridSquare = null;
	public LinkedList<Vertex> succ = new LinkedList<Vertex>();
	// || this.gridSquare.color == SquareColor.DARK_GRAY

	public boolean isClosed() {
		if (this.gridSquare.isPath == true) {
			return true;
		}
		return false;
	}

	public Vertex(GridSquare currentBlock, Grid currentGrid) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				succ.add(new Vertex(currentGrid.GridSquares[currentBlock.coordinates.XVal + x][currentBlock.coordinates.YVal + y], currentGrid));
			}
		}
	}
}
