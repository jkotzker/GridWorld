package com.gridworld.algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;

public class Search {
	// mark gridsquares as you go through algorithm
	// tell us if what kind of search you are doing: ucost, A* or wA*=> also,
	// what is w?
	// that will determine what the heuristic function is. 0, h(), or w*h()
	private String searchType;
	private PriorityQueue<Vertex> fringe;
	
	public Search() {
		
		this.searchType = "ucost";
		this.fringe = new PriorityQueue<Vertex>(100,
	            new HeuristicComparator());
		
	}



	public ArrayList<GridSquare> performSearch(Grid currentGrid, int which) throws TraversalException {
		
		ArrayList<GridSquare> closed = new ArrayList<GridSquare>();
		
		GridSquare sStart = currentGrid.GridSquares[currentGrid.pathPoints.get(which).sStart.XVal][currentGrid.pathPoints.get(which).sStart.YVal];
		GridSquare sGoal = currentGrid.GridSquares[currentGrid.pathPoints.get(which).sGoal.XVal][currentGrid.pathPoints.get(which).sGoal.YVal];
		Vertex currentVertex = new Vertex(sStart, currentGrid, this.searchType);
		currentVertex.g = 0;
		currentVertex.Parent = currentVertex;
		fringe.add(currentVertex);
		while (!fringe.isEmpty()) {
			Vertex s = fringe.remove();
			closed.add(s.gridSquare);
			if (s.gridSquare == sGoal) {
				return closed;
			}
			for (Vertex v : currentVertex.succ) {
				if (!v.isClosed()) {
					UpdateVertex(currentVertex, v);
				}
			}
		}
		return null;

	}

	private void UpdateVertex(Vertex currentVertex, Vertex v) throws TraversalException {
		if (currentVertex.g + currentVertex.gridSquare.computeCost(v.gridSquare) < v.g) {
			v.g = currentVertex.g + currentVertex.gridSquare.computeCost(v.gridSquare);
			v.Parent = currentVertex;
			if(fringe.contains(v)){
				fringe.remove(v);
			}
			fringe.add(v);
		}

	}
}
