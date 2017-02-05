package com.gridworld.algorithm;

import com.gridworld.grid.GridSquare;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Grid;
import java.util.PriorityQueue;
import java.util.List;
import java.util.Comparator;

public class Search {
	// mark gridsquares as you go through algorithm
	// tell us if what kind of search you are doing: ucost, A* or wA*=> also,
	// what is w?
	// that will determine what the heuristic function is. 0, h(), or w*h()
	private String searchType = "";
	private PriorityQueue<Vertex> fringe = new PriorityQueue<Vertex>(100,
            new HeuristicComparator());



	public String Search(Grid currentGrid) throws TraversalException {
		GridSquare sStart = currentGrid.GridSquares[currentGrid.pathPoints.get(0).sStart.XVal][currentGrid.pathPoints.get(0).sStart.YVal];
		GridSquare sGoal = currentGrid.GridSquares[currentGrid.pathPoints.get(0).sGoal.XVal][currentGrid.pathPoints.get(0).sGoal.YVal];
		Vertex currentVertex = new Vertex(sStart, currentGrid, this.searchType);
		currentVertex.g = 0;
		currentVertex.Parent = currentVertex;
		fringe.add(currentVertex);
		while (!fringe.isEmpty()) {
			Vertex s = fringe.remove();
			if (s.gridSquare == sGoal) {
				return "path found";
			}
			for (Vertex v : currentVertex.succ) {
				if (!v.isClosed()) {
					UpdateVertex(currentVertex, v);
				}
			}
		}
		return "no path found";

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
