
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
	int w2 = 1;

	public ArrayList<GridSquare> performSearch2(Grid currentGrid, int which, int n) {
		for (int i = 0; i <= n; i++) {
			Open.add(new minHeap());
			Closed.add(new minHeap());

			// Get start and goal coordinates and vertices
			Coordinates Start = currentGrid.pathPoints.get(which).sStart;
			Coordinates Goal = currentGrid.pathPoints.get(which).sGoal;

			Vertex StartVertex = new Vertex(currentGrid.GridSquares[Start.XVal][Start.YVal], currentGrid, null);
			Vertex GoalVertex = new Vertex(currentGrid.GridSquares[Goal.XVal][Goal.YVal], currentGrid, null);
			StartVertex.setG(n, 0.0);
			GoalVertex.setG(n, Double.POSITIVE_INFINITY);

			while (Open.get(0).peek().getKey(0) < Double.POSITIVE_INFINITY) {

				for (int j = 1; j <= n; j++) {
					if (Open.get(j).peek().getKey(j) <= w2 * this.Open.get(0).peek().getKey(0)) {
						if (GoalVertex.getG(j) <= Open.get(j).peek().getKey(j)) {
							if (currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.getG(j) < Double.POSITIVE_INFINITY) {
								return currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.BP.get(j);
							}
						} else {
							Vertex S = Open.get(j).delete(j);
							ExpandStates(S, j);
							Closed.get(j).insert(S, j);
						}
					} else {
						if (GoalVertex.getG(0) <= Open.get(0).peek().getKey(0)) {
							if (GoalVertex.getG(0) <= Double.POSITIVE_INFINITY) {
								return currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.BP.get(0);
							}
						} else {
							Vertex S = Open.get(0).delete(0);
							ExpandStates(S, 0);
							Closed.get(0).insert(S, 0);
						}
					}
				}
			}
		}
		return null;
	}

	private void ExpandStates(Vertex S, int j) {
		for (Vertex s : S.GetSucc()) {
			s.setG(j, Double.POSITIVE_INFINITY);
			s.BP.add(null);
			try {
				if (s.getG(j) > S.getG(j) + S.block.computeCost(s.block)) {
					s.setG(j, S.getG(j) + S.block.computeCost(s.block));
					s.BP.get(j).add(s.block);
					if (!Closed.get(j).Contains(s)) {
						if (!Open.get(j).Contains(s)) {
							Open.get(j).insert(s, j);
						}
					}
				}
			} catch (TraversalException e) {
				return;
			}
		}
	}
}