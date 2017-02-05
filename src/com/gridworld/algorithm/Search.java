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
	private PriorityQueue<Vertex> fringe = new PriorityQueue<Vertex>(int 1000,
            Comparator<Vertex> comparator);

	private double h(Vertex A) {
		if (this.searchType == "A*") {
			return 1;
		} else if (this.searchType == "wA*") {
			return 2;
		}
		return 0;
	}

	public String Search(Grid currentGrid) throws TraversalException {
		Vertex currentVertex = new Vertex(currentGrid.sStart, currentGrid);
		currentVertex.g = 0;
		currentVertex.Parent = currentVertex;
		fringe.insert(currentVertex, currentVertex.g+h(currentVertex));
		while (!fringe.isEmpty()) {
			Vertex s = fringe.remove();
			if (s.gridSquare == currentGrid.sGoal) {
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
			fringe.insert(v, v.g + h(v));
			fringe.
		}

	}
}
