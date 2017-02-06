package com.gridworld.algorithm;

import com.gridworld.grid.*;
import java.util.LinkedList;

public class Vertex {
	public double g = Double.POSITIVE_INFINITY;
	public double h = 0;
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

	public Vertex(GridSquare currentBlock, Grid currentGrid, String searchType) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				
				if(currentBlock.coordinates.XVal + x == -1 || currentBlock.coordinates.YVal + y == -1){
					continue;
				} else if (currentBlock.coordinates.XVal + x == 0 && currentBlock.coordinates.YVal + y == 0) {
					continue;
				}
				else{
					succ.add(new Vertex(currentGrid.GridSquares[currentBlock.coordinates.XVal + x][currentBlock.coordinates.YVal + y], currentGrid, searchType));
				}
				if (x==1 && y==1)
					break;
			}
		}
		if (searchType == "A*") {
			this.h=  1;
		} else if (searchType == "wA*") {
			this.h=  2;
		}
		this.h=  0;
	}
}
