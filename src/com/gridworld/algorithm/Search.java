package com.gridworld.algorithm;

import java.util.ArrayList;

import com.gridworld.algorithm.minHeap.minHeap;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;

public class Search {
	// mark gridsquares as you go through algorithm
	// tell us if what kind of search you are doing: ucost, A* or wA*=> also,
	// what is w?
	// that will determine what the heuristic function is. 0, h(), or w*h()
	private String searchType;
	private minHeap fringe;
	private ArrayList<GridSquare> closed;
	private ArrayList<GridSquare> output = new ArrayList<GridSquare>();

	public Search() {

		this.searchType = "ucost";
		this.fringe = new minHeap();

	}

	public ArrayList<GridSquare> performSearch(Grid currentGrid, int which) {
		Coordinates sStart = currentGrid.pathPoints.get(which).sStart;
		Vertex StartVertex = currentGrid.GridSquares[sStart.XVal][sStart.YVal].SearchVertex;
		Coordinates sGoal = currentGrid.pathPoints.get(which).sGoal;
		GridSquare GoalBlock = currentGrid.GridSquares[sGoal.XVal][sGoal.YVal];
		StartVertex = new Vertex(currentGrid.GridSquares[sStart.XVal][sStart.YVal], currentGrid, this.searchType);

		StartVertex.setG(0, 0);
		StartVertex.Parent = StartVertex;

		fringe = new minHeap();
		fringe.insert(StartVertex, 0);
		StartVertex.inFringe = true;

		closed = new ArrayList<GridSquare>();

		this.output.add(StartVertex.block);
		Vertex s = null;
		
		while (!fringe.isEmpty()) {
			s = fringe.delete(0);
			s.inFringe = false;
			if (s.block == GoalBlock) {
				return Output(s, StartVertex, GoalBlock, currentGrid);
			}

			closed.add(s.block);
			s.closed = true;
			for (Vertex sPrime : s.GetSucc()) {
				if (sPrime != null) {
					if (sPrime.closed != true) {
						if(sPrime.inFringe==false){
							sPrime.setG(0, Double.POSITIVE_INFINITY);
							sPrime.Parent = null;
						}
						UpdateVertex(s, sPrime);
					}
				}
			}
		}
		return null;

	}

	private ArrayList<GridSquare> Output(Vertex s, Vertex StartVertex, GridSquare goalBlock, Grid currentGrid) {
		int iterator = 0;
		while (s != StartVertex && iterator < 550) {
			output.add(s.block);
			s = s.Parent;
			iterator++;
		}
		output.add(goalBlock);
		// Saves vertices h
		currentGrid.SaveVertices();
		return output;
	}

	private void UpdateVertex(Vertex s, Vertex sPrime) {
		try {
			if (s.getG(0) + s.block.computeCost(sPrime.block) < sPrime.getG(0)) {
				sPrime.setG(0, s.getG(0) + s.block.computeCost(sPrime.block));
				sPrime.Parent = s;
				if (sPrime.inFringe) {
					fringe.delete(sPrime, 0);
					sPrime.inFringe = false;
				}
				fringe.insert(sPrime, 0);
				sPrime.inFringe = true;
			}
		} catch (TraversalException t) {
			return;
		}

	}
}