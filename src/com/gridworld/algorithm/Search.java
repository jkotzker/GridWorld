package com.gridworld.algorithm;


import com.gridworld.grid.GridSquare;
import com.gridworld.grid.Grid;
import java.util.PriorityQueue;
import java.util.List;
public class Search {
//mark gridsquares as you go through algorithm
//tell us if what kind of search you are doing: ucost, A* or wA*=> also, what is w?
	//that will determine what the heuristic function is. 0, h(), or w*h() 
	private String searchType = "";
	private PriorityQueue<GridSquare> fringe = new PriorityQueue<GridSquare>();
	private int h(GridSquare A){
		if (this.searchType=="A*"){
			return n;
		} else if (this.searchType=="wA*"){
			return w*n;
		}
		return 0;
	}
	Search(Grid currentGrid){
		Vertex currentVertex = new Vertex(currentGrid.sStart.getSquare(currentGrid));
		
	}
}
