package com.gridworld.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import com.gridworld.algorithm.minHeap.minHeap;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;
import com.gridworld.algorithm.Heuristics;

public class Search3 {

	public LinkedList<minHeap> Open = new LinkedList<minHeap>();
	public LinkedList<minHeap> Closed = new LinkedList<minHeap>();
	private int w2 = 1;
	private int n = 1;
	private ArrayList<GridSquare> output = new ArrayList<GridSquare>();
	private Vertex GoalVertex;

	public ArrayList<GridSquare> performSearch3(Grid currentGrid, int numberofHeuristics) {
		int which = currentGrid.searchIterator;
		this.n = numberofHeuristics;
		// Get start and goal coordinates and vertices
		Coordinates Start = currentGrid.pathPoints.get(which).sStart;
		Coordinates Goal = currentGrid.pathPoints.get(which).sGoal;

		Vertex StartVertex = new Vertex(currentGrid.GridSquares[Start.XVal][Start.YVal], currentGrid, null);
		this.GoalVertex = new Vertex(currentGrid.GridSquares[Goal.XVal][Goal.YVal], currentGrid, null);
		// TODO what is u?
		StartVertex.setG(0, 0.0);
		GoalVertex.setG(0, Double.POSITIVE_INFINITY);
		StartVertex.setBP(0, null);
		GoalVertex.setBP(0, null);
		for (int i = 0; i <= n; i++) {
			Open.add(new minHeap());
			Open.get(i).insert(StartVertex, i);
		}
		// Closed anchor
		Closed.add(new minHeap());
		// Closed inad
		Closed.add(new minHeap());

		while (Open.get(0).peek().getKey(0) < Double.POSITIVE_INFINITY) {

			for (int j = 1; j <= n; j++) {
				if (Open.get(j).peek().getKey(0) <= w2 * this.Open.get(0).peek().getKey(0)) {
					if (GoalVertex.getG(0) <= Open.get(j).peek().getKey(0)) {
						if (GoalVertex.getG(0) < Double.POSITIVE_INFINITY) {
							return Output(GoalVertex, StartVertex, GoalVertex.block, currentGrid, j);
						}
					} else {
						Vertex S = Open.get(j).delete(0);

						S.v = S.getG(0);
						for (Vertex s : S.GetSucc()) {
							ExpandStates(s, S, j);
						}
						Closed.get(1).insert(S, j);
					}
				} else {
					if (GoalVertex.getG(0) <= Open.get(0).peek().getKey(0)) {
						if (GoalVertex.getG(0) <= Double.POSITIVE_INFINITY) {
							return Output(GoalVertex, StartVertex, GoalVertex.block, currentGrid, 0);
						}
					} else {
						Vertex S = Open.get(0).delete(0);

						S.v = S.getG(0);
						for (Vertex s : S.GetSucc()) {
							ExpandStates(s, S, 0);
						}
						Closed.get(0).insert(S, 0);
					}
				}
			}

		}
		return null;
	}

	private void ExpandStates(Vertex s, Vertex S, int j) {
		if (s.getG(0) == Double.NaN) {
			s.setG(0, Double.POSITIVE_INFINITY);
			s.setBP(0, null);
			s.v = Double.POSITIVE_INFINITY;
		}
		try {
			if (s.getG(0) > S.getG(0) + S.block.computeCost(s.block)) {
				s.setG(0, S.getG(0) + S.block.computeCost(s.block));
				s.setBP(0, S.block);
				if (!Closed.get(1).Contains(s)) {
					for (int i = 1; i <= n; i++) {
						if (Heuristics.Key(s, i) <= w2 * Heuristics.Key(s, 0)) {
							s.setKey(1, Heuristics.Key(s, i));
						}
						Open.get(j).insert(s, 0);
					}
				}
			}
		} catch (TraversalException e) {
			return;
		}

	}

	private ArrayList<GridSquare> Output(Vertex s, Vertex StartVertex, GridSquare goalBlock, Grid currentGrid, int j) {
		int iterator = 0;
		while (s != StartVertex && iterator < 550) {
			output.add(s.block);
			s = s.getBP(j).SearchVertex;
			iterator++;
		}
		output.add(goalBlock);
		// Saves vertices h
		currentGrid.SaveVertices();
		return output;
	}

}
