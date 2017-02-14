package com.gridworld.grid;

import java.util.Stack;

import com.gridworld.algorithm.Vertex;
import com.gridworld.app.Main;
import com.gridworld.exceptions.CoordinateException;
import com.gridworld.exceptions.HighwayException;
import com.gridworld.exceptions.TraversalException;

/**
 * GridSquare Class
 * 
 * @author josephkotzker
 *
 *         Class representing a single grid in a map of terrain (represented by
 *         the Grid class).
 */
public class GridSquare {

	// Coordinates object to hold only allowed X and Y coordinate values
	public Coordinates coordinates;
	public Boolean debugger = false;
	// Boolean variables tracking highway membership
	public Boolean memberOfVerticalHighway = false;
	public Boolean memberOfHorizontalHighway = false;

	// Boolean variable tracking whether member of path
	public Vertex SearchVertex = null;
	public Stack<Vertex> SavedVertices = new Stack<Vertex>();

	// Color Enum
	public SquareColor color;

	/**
	 * Constructor for the GridSquare class
	 * 
	 * @param x
	 *            the x vale
	 * @param y
	 *            the y value
	 * @param color
	 *            the (initial) color
	 */
	public GridSquare(int x, int y, SquareColor color) {

		try {
			this.coordinates = new Coordinates(x, y);
		} catch (CoordinateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		this.color = color;

	}

	/**
	 * Compute cost of traversal from this to target
	 * 
	 * @param target
	 *            the target GridSquare
	 * @return double the cost of traversal as a double
	 * @throws TraversalException
	 */
	public double computeCost(GridSquare target) throws TraversalException {

		double cost = 0.0;
		int targetX = target.coordinates.XVal;
		int targetY = target.coordinates.YVal;

		int diffX = targetX - this.coordinates.XVal;
		int diffY = targetY - this.coordinates.YVal;
		SquareColor targetColor = target.color;
		boolean traversedHighway = (this.memberOfHorizontalHighway && target.memberOfHorizontalHighway)
				|| (this.memberOfVerticalHighway && target.memberOfVerticalHighway)
				|| (this.memberOfHorizontalHighway && target.memberOfVerticalHighway)
				|| (this.memberOfVerticalHighway && target.memberOfHorizontalHighway);

		if (this.color == SquareColor.WHITE && targetColor == SquareColor.WHITE) {

			if (diffX == 0) {
				// Horizontal move
				cost = Main.whiteCosts.get("horiz") + Main.whiteCosts.get("horiz");
				if (this.memberOfHorizontalHighway && target.memberOfHorizontalHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffY == 0) {
				// Vertical move
				cost = Main.whiteCosts.get("vert") + Main.whiteCosts.get("vert");
				if ((this.memberOfVerticalHighway && target.memberOfVerticalHighway)||traversedHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1) {
				// Diagonal Move
				cost = Main.whiteCosts.get("diagonal") + Main.whiteCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}

		} else if (this.color == SquareColor.LIGHT_GRAY && targetColor == SquareColor.LIGHT_GRAY) {

			if (diffX == 0) {
				// Horizontal Move
				cost = Main.lightgrayCosts.get("horiz") + Main.lightgrayCosts.get("horiz");
				if ((this.memberOfHorizontalHighway && target.memberOfHorizontalHighway)||traversedHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffY == 0) {
				// Vertical Move
				cost = Main.lightgrayCosts.get("vert") + Main.lightgrayCosts.get("vert");
				if (this.memberOfVerticalHighway && target.memberOfVerticalHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1) {
				// Diagonal Move
				cost = Main.lightgrayCosts.get("diagonal") + Main.lightgrayCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}

		} else if ((this.color == SquareColor.LIGHT_GRAY && targetColor == SquareColor.WHITE)
				|| (this.color == SquareColor.WHITE && targetColor == SquareColor.LIGHT_GRAY)) {

			if (diffX == 0) {
				// Horizontal Move
				cost = Main.lightgrayCosts.get("horiz") + Main.whiteCosts.get("horiz");
				if ((this.memberOfHorizontalHighway && target.memberOfHorizontalHighway)||traversedHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffY == 0) {
				// Vertical Move
				cost = Main.lightgrayCosts.get("vert") + Main.whiteCosts.get("vert");
				if ((this.memberOfVerticalHighway && target.memberOfVerticalHighway)||traversedHighway)
					cost = cost * 0.25;
				return cost;
			} else if (diffX == 1 || diffX == -1 || diffY == 1 || diffY == -1) {
				// Diagonal Move
				cost = Main.lightgrayCosts.get("diagonal") + Main.whiteCosts.get("diagonal");
				return cost;
			} else {
				throw new TraversalException("Cost computation failed, traversal is invalid.");
			}

		} else if (this.color == SquareColor.DARK_GRAY || targetColor == SquareColor.DARK_GRAY) {
			throw new TraversalException("Cannot traverse to or from a blocked square, traversal is invalid.");
		} else {
			throw new TraversalException("Cost computation failed, traversal is invalid.");
		}

	}

	/**
	 * Change GridSquare's color
	 * 
	 * @param newcol
	 *            the new Color for the GridSquare
	 */
	public void setColor(SquareColor newcol) {
		this.color = newcol;
		return;
	}

	/**
	 * Set the GridSquare as a member of a vertical highway
	 * 
	 * @throws HighwayException
	 *             Exception containing error message regarding failure to set
	 *             as highway member
	 */
	public void setMemberOfVerticalHighway() throws HighwayException {

		if (this.memberOfVerticalHighway == false && this.memberOfHorizontalHighway == false) {
			this.memberOfVerticalHighway = true;
			return;
		} else if (this.memberOfVerticalHighway == true) {
			throw new HighwayException(
					"Highway Error: Already a member of a vertical highway, duplicates not allowed.");
		} else if (this.memberOfHorizontalHighway == true) {
			throw new HighwayException(
					"Highway Error: Already a member of a horizontal highway, intersections not allowed.");
		} else {
			throw new HighwayException("Highway Error: Unknown error, this should never happen.");
		}
	}

	/**
	 * Set the GridSquare as a member of a vertical highway
	 * 
	 * @throws HighwayException
	 *             Exception containing error message regarding failure to set
	 *             as highway member
	 */
	public void setMemberOfHorizontalHighway() throws HighwayException {

		if (this.memberOfVerticalHighway == false && this.memberOfHorizontalHighway == false) {
			this.memberOfHorizontalHighway = true;
			return;
		} else if (this.memberOfHorizontalHighway == true) {
			throw new HighwayException(
					"Highway Error: Already a member of a horizontal highway, duplicates not allowed.");
		} else if (this.memberOfVerticalHighway == true) {
			throw new HighwayException(
					"Highway Error: Already a member of a vertical highway, intersections not allowed.");
		} else {
			throw new HighwayException("Highway Error: Unknown error, this should never happen.");
		}
	}

	public Vertex getSavedVertex(int n) {
		if (this.SavedVertices.size() <= n) {
			return null;
		}
		Vertex[] Copy = new Vertex[10];
		this.SavedVertices.copyInto(Copy);
		return Copy[n];
	}
}
