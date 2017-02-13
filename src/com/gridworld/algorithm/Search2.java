package com.gridworld.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.gridworld.algorithm.minHeap.minHeap;
import com.gridworld.exceptions.TraversalException;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;

public class Search2 {

	public LinkedList<minHeap> Open = new LinkedList<minHeap>();
	public LinkedList<minHeap> Closed = new LinkedList<minHeap>();

	public ArrayList<GridSquare> performSearch2(Grid currentGrid, int which, int n) {
		for (int i = 0; i <= n; i++) {
			Open.add(new minHeap());
			Closed.add(new minHeap());
			Coordinates Start = currentGrid.pathPoints.get(which).sStart;
			Coordinates Goal = currentGrid.pathPoints.get(which).sGoal;
			currentGrid.GridSquares[Start.XVal][Start.YVal].SearchVertex.G.set(n, 0.0);
			currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.G.set(n, Double.POSITIVE_INFINITY);
			while(Open.peek().g<Double.POSITIVE_INFINITY){
				for(int j = 1; j<=n;j++){
					if(Open.get(j).peek().g<= w2*this.Open.get(0).peek()){
						if(currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.G.get(j)<= Open.get(j).peek().g){
							if(currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.G.get(j)< Double.POSITIVE_INFINITY){
								return null;//TODO
							}
						} else{
							Vertex S = Open.get(j).delete();
							ExpandStates(S,j);
							Closed.get(j).insert(S);
						}
					}else{
						if(currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.G.get(0)<= Open.get(0).peek().g){
							if(currentGrid.GridSquares[Goal.XVal][Goal.YVal].SearchVertex.G.get(0)<= Double.POSITIVE_INFINITY){
								return null;//TODO
							}
						} else{
							Vertex S = Open.get(0).delete();
							ExpandStates(S,j);
							Closed.get(0).insert(S);
						}
					}
				}
			}
		}
		return null;
	}

	private void ExpandStates(Vertex s, int j) {
		// TODO Auto-generated method stub
		
	}
}
