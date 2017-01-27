package com.gridworld.grid;

import com.gridworld.grid.Color;

public class GridSquare {
	
	Coordinates coordinates = new Coordinates(0,0);
	
	Boolean memberOfVerticalHighway = false;
	Boolean memberOfHorizontalHighway = false;
	
	Color color;
	
	public GridSquare(int x, int y, Color color){
		
		this.coordinates.setX(x);
		this.coordinates.setY(y);
		
		this.color = color;
		
	}

}
