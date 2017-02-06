package com.gridworld.app;

import java.io.IOException;
import java.io.PrintWriter;

import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;

public class ReadAndWrite {
	
	
	/**
	public static void writeGridToFile(Grid grid) {
		
		try{
		    
			PrintWriter writer = new PrintWriter("out.map", "UTF-8");
		    
		    // Get coordinates of start and goal squares, build strings, and write to file
		    Coordinates startC = grid.sStart.coordinates; // Need to implement start and goal values in Grid
		    Coordinates goalC = grid.sGoal.coordinates;		    
		    String startval = "("+startC.XVal+", "+startC.YVal+")";
		    String goalval = "("+goalC.XVal+", "+goalC.YVal+")";
		    writer.println(startval);
		    writer.println(goalval);

		    
		    //TODO: coordinates of the centers of the hard to traverse regions
		    //TODO: character representation of the grid
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}

		
	}
	**/

}
