package com.gridworld.grid;

import com.gridworld.exceptions.CoordinateException;

public class Coordinates {
	
	public int XVal;
	public int YVal;
	
	public Coordinates(int x, int y) throws CoordinateException {
		if(x < 0 || x > 119) {
			throw new CoordinateException("Coordinate Error: x value is not in range.");
		}
		else if (y < 0 || y > 159) {
        	throw new CoordinateException("Coordinate Error: y value is not in range.");
        }
		else {
			this.XVal = x;
			this.YVal = y;
		}

	}
	
	public void setX(int newx) throws CoordinateException{
		if(newx < 0 || newx > 119) {
			throw new CoordinateException("Coordinate Error: x value is not in range.");
		} else {
			this.XVal = newx;
		}
	}
	
	public void setY(int newy) throws CoordinateException{
		if (newy < 0 || newy > 159) {
        	throw new CoordinateException("Coordinate Error: y value is not in range.");
		} else {
			this.YVal = newy;
		}
	}
	
	
	public String toString(){
		return this.XVal+","+this.YVal;
	}
	

}
