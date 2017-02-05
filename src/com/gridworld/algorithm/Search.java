package com.gridworld.algorithm;

public class Search {
//mark gridsquares as you go through algorithm
//tell us if what kind of search you are doing: ucost, A* or wA*=> also, what is w?
	//that will determine what the heuristic function is. 0, h(), or w*h() 
	private String searchType = "";
	private int h(n){
		if (this.searchType=="A*"){
			return n;
		} else if (this.searchType=="wA*"){
			return w*n;
		}
	}
	Search(){
		
	}
}
