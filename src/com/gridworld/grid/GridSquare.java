package com.gridworld.grid;

import com.gridworld.app.Main;
import com.gridworld.exceptions.CoordinateException;
import com.gridworld.exceptions.HighwayException;
import com.gridworld.exceptions.TraversalException;
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
	 * @throws TraversalException 
	 */
	public double computeCost(GridSquare target) throws TraversalException{
		
		double cost = 0.0;
		//TODO: Compute cost of traversal from this to target
		int targetX = target.coordinates.XVal;
		int targetY = target.coordinates.YVal;
		
		int diffX = targetX - this.coordinates.XVal;
		int diffY = targetY - this.coordinates.YVal;
		Color targetColor = target.color;
		
		if (this.color ==  Color.WHITE && targetColor == Color.WHITE){
			
			if(diffX == 0){
				// Vertical Move
				cost = Main.grid.whiteCosts.get("vert")+Main.grid.whiteCosts.get("vert");
				return cost;
			} else if (diffY == 0) {
				// Horizontal Move
				cost = Main.grid.whiteCosts.get("horiz")+Main.grid.whiteCosts.get("horiz");
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1){
				// Diagonal Move
				cost = Main.grid.whiteCosts.get("diagonal")+Main.grid.whiteCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}
			
		} else if (this.color ==  Color.LIGHT_GRAY && targetColor == Color.LIGHT_GRAY) {
			
			if(diffX == 0){
				// Vertical Move
				cost = Main.grid.lightgrayCosts.get("vert")+Main.grid.lightgrayCosts.get("vert");
				return cost;
			} else if (diffY == 0) {
				// Horizontal Move
				cost = Main.grid.lightgrayCosts.get("horiz")+Main.grid.lightgrayCosts.get("horiz");
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1){
				// Diagonal Move
				cost = Main.grid.lightgrayCosts.get("diagonal")+Main.grid.lightgrayCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}
			
		} else if ((this.color ==  Color.LIGHT_GRAY && targetColor == Color.WHITE) || (this.color ==  Color.WHITE && targetColor == Color.LIGHT_GRAY)) {
			
			if(diffX == 0){
				// Vertical Move
				cost = Main.grid.lightgrayCosts.get("vert")+Main.grid.whiteCosts.get("vert");
				return cost;
			} else if (diffY == 0) {
				// Horizontal Move
				cost = Main.grid.lightgrayCosts.get("horiz")+Main.grid.whiteCosts.get("horiz");
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1){
				// Diagonal Move
				cost = Main.grid.lightgrayCosts.get("diagonal")+Main.grid.whiteCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}
			
		} else if (this.color == Color.DARK_GRAY || targetColor == Color.DARK_GRAY) {
			throw new TraversalException("Cannot traverse to or from a blocked square, traversal is invalid.");
		} else {
			throw new TraversalException("Cost computation failed, traversal is invalid.");
		}
		
		//TODO: Include highways in cost calculations
		
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
