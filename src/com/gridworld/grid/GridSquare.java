package com.gridworld.grid;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.exceptions.HighwayException;
import com.gridworld.grid.Color;

/**
 * GridSquare Class
 * @author josephkotzker
 *
 * Class representing a single grid in a map of terrain (represented by the Grid class).
 */
public class GridSquare {
	
	// Coordinates object to hold only allowed X and Y coordinate values
	Coordinates coordinates;
	
	// Boolean variables tracking highway membership
	Boolean memberOfVerticalHighway = false;
	Boolean memberOfHorizontalHighway = false;
	
	// Color Enum
	Color color;
	
	/**
	 * Constructor for the GridSquare class
	 * 
	 * @param x			the x vale
	 * @param y			the y value
	 * @param color 	the (initial) color
	 */
	public GridSquare(int x, int y, Color color){
		
		try {
			this.coordinates = new Coordinates(x,y);
		} catch (CoordinateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		this.color = color;
		
	}
	
	/**
	 * Compute cost of traversal from this to target
	 * @param target 	the target GridSquare
	 * @return double 	the cost of traversal as a double 
	 */
	public double computeCost(GridSquare target){
		
		double cost = 0.0;
		//TODO: Compute cost of traversal from this to target
		return cost;
		
	}
	
	/**
	 * Change GridSquare's color
	 * @param newcol 	the new Color for the GridSquare
	 */
	public void setColor(Color newcol){
		this.color = newcol;
		return;
	}
	
	/**
	 * Set the GridSquare as a member of a vertical highway
	 * @throws HighwayException 	Exception containing error message regarding failure to set as highway member
	 */
	public void setMemberOfVerticalHighway() throws HighwayException{
		
		if (this.memberOfVerticalHighway == false && this.memberOfHorizontalHighway == false){
			this.memberOfVerticalHighway = true;
			return;
		} else if (this.memberOfVerticalHighway == true){
			throw new HighwayException("Highway Error: Already a member of a vertical highway, duplicates not allowed.");
		} else if(this.memberOfHorizontalHighway == true) {
			throw new HighwayException("Highway Error: Already a member of a horizontal highway, intersections not allowed.");
		} else {
			throw new HighwayException("Highway Error: Unknown error, this should never happen.");
		}
	}
	
	/**
	 * Set the GridSquare as a member of a vertical highway
	 * @throws HighwayException 	Exception containing error message regarding failure to set as highway member
	 */
	public void setMemberOfHorizontalHighway() throws HighwayException{
		
		if (this.memberOfVerticalHighway == false && this.memberOfHorizontalHighway == false){
			this.memberOfHorizontalHighway = true;
			return;
		} else if (this.memberOfHorizontalHighway == true){
			throw new HighwayException("Highway Error: Already a member of a horizontal highway, duplicates not allowed.");
		} else if(this.memberOfVerticalHighway == true) {
			throw new HighwayException("Highway Error: Already a member of a vertical highway, intersections not allowed.");
		} else {
			throw new HighwayException("Highway Error: Unknown error, this should never happen.");
		}
	}
	
	
}
