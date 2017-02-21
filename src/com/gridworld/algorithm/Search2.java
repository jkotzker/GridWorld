
package com.gridworld.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

import com.gridworld.algorithm.minHeap.minHeap;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;

public class Search2 {

	public LinkedList<minHeap> Open = new LinkedList<minHeap>();
	public LinkedList<minHeap> Closed = new LinkedList<minHeap>();
	double w2 = 1;
	private ArrayList<GridSquare> output = new ArrayList<GridSquare>();

	public ArrayList<GridSquare> performSearch2(Grid currentGrid, int n) {

		int which = currentGrid.searchIterator;
		// Get start and goal coordinates and vertices
		Coordinates Start = currentGrid.pathPoints.get(which).sStart;
		Coordinates Goal = currentGrid.pathPoints.get(which).sGoal;

		Vertex StartVertex = new Vertex(currentGrid.GridSquares[Start.XVal][Start.YVal], currentGrid, null);
		Vertex GoalVertex = new Vertex(currentGrid.GridSquares[Goal.XVal][Goal.YVal], currentGrid, null);
		output.add(StartVertex.block);
		Open.add(new minHeap());
		Closed.add(new minHeap());
		for (int i = 0; i <= n; i++) {
			Open.add(new minHeap());
			Closed.add(new minHeap());

			StartVertex.setG(i, 0.0);
			GoalVertex.setG(i, Double.POSITIVE_INFINITY);
			Heuristics.storeNewKey(StartVertex, i);
			StartVertex.setBP(i, null);
			GoalVertex.setBP(i, null);
			Open.get(i).insert(StartVertex, i);
			StartVertex.inOpen[i] = true;
		}
		while (Open.get(0).peek().getKey(0) < Double.POSITIVE_INFINITY) {
			for (int j = 1; j <= n; j++) {
				if (Open.get(j).peek().getKey(j) <= w2 * this.Open.get(0).peek().getKey(0)) {
					if (GoalVertex.getG(j) <= Open.get(j).peek().getKey(j)) {
						if (GoalVertex.getG(j) < Double.POSITIVE_INFINITY) {
							return Output(GoalVertex, StartVertex, GoalVertex.block, currentGrid, j);
						}
					} else {
						Vertex S = Open.get(j).delete(j);
						S.inOpen[j] = false;
						for (Vertex s : S.GetSucc()) {
							ExpandStates(s, S, j);
						}
						Closed.get(j).insert(S, j);
						S.inClosed[j] = true;
					}
				} else {
					if (GoalVertex.getG(0) <= Open.get(0).peek().getKey(0)) {
						if (GoalVertex.getG(0) <= Double.POSITIVE_INFINITY) {
							return Output(GoalVertex, StartVertex, GoalVertex.block, currentGrid, 0);
						}
					} else {
						Vertex S = Open.get(0).delete(0);
						S.inOpen[0] = false;
						for (Vertex s : S.GetSucc()) {
							ExpandStates(s, S, 0);
						}
						Closed.get(0).insert(S, 0);
						S.inClosed[0] = true;
					}
				}
			}
		}
		return null;
	}

	private void ExpandStates(Vertex s, Vertex S, int j) {
		if (s.getG(j) == -1.0 || (s.getG(0) == 0 && j == 0)) {
			s.setG(j, Double.POSITIVE_INFINITY);
			s.setBP(j, null);
		}
		try {
			// need to calculate s from succ in method separate

			if (s.getG(j) > S.getG(j) + S.block.computeCost(s.block)) {
				s.setG(j, S.getG(j) + S.block.computeCost(s.block));
				Heuristics.storeNewKey(s, j);
				s.setBP(j, S.block);
				if (!S.inClosed[j] == true) {
					if (!s.inOpen[j]) {
						Open.get(j).insert(s, j);
						s.inOpen[j] = true;
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
		System.out.println("completed search");
		return output;
	}
}