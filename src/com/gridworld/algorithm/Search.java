package com.gridworld.algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.gridworld.algorithm.minHeap.minHeap;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;

public class Search {
	// mark gridsquares as you go through algorithm
	// tell us if what kind of search you are doing: ucost, A* or wA*=> also,
	// what is w?
	// that will determine what the heuristic function is. 0, h(), or w*h()
	private String searchType;
	private minHeap fringe;

	public Search() {

		this.searchType = "ucost";
		this.fringe = new minHeap();

	}

	public ArrayList<GridSquare> performSearch(Grid currentGrid, int which) {


		ArrayList<GridSquare> closed = new ArrayList<GridSquare>();

		GridSquare sStart = currentGrid.GridSquares[currentGrid.pathPoints
				.get(which).sStart.XVal][currentGrid.pathPoints.get(which).sStart.YVal];
		GridSquare sGoal = currentGrid.GridSquares[currentGrid.pathPoints.get(which).sGoal.XVal][currentGrid.pathPoints
				.get(which).sGoal.YVal];

		ArrayList<GridSquare> output = new ArrayList<GridSquare>();
		Vertex currentVertex = new Vertex(sStart, currentGrid, this.searchType);
		currentVertex.g = 0;
		currentVertex.Parent = currentVertex;
		fringe.insert(currentVertex);
		currentVertex.inFringe = true;
		while (!fringe.isEmpty()) {
			Vertex s = fringe.delete();
			s.inFringe = false;
			closed.add(s.block);
			s.closed = true;
			if (s.block == sGoal) {
				int iterator = 0;
				while(s!=sStart.SearchVertex && iterator<100){
					output.add(s.block);
					s = s.Parent;
					iterator++;
				}
				output.add(sGoal);
				currentGrid.SaveVertices();
				return output;
			}
			for (Vertex v : s.GetSucc()) {
				if (v != null) {
					if (v.closed != true) {
						UpdateVertex(s, v);
					}
				}
			}
		}
		return null;

	}

	private void UpdateVertex(Vertex s, Vertex v) {
		try {
			if (s.g + s.block.computeCost(v.block) < v.g) {
				v.g = s.g + s.block.computeCost(v.block);
				v.Parent = s;
				if(v.inFringe){
					fringe.delete(v);
				}
					fringe.insert(v);
					v.inFringe = true;
			}
		} catch (TraversalException t) {
			return;
		}

	}
}
