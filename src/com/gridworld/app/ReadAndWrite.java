package com.gridworld.app;

import java.io.IOException;
import java.io.PrintWriter;

import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.Coordinates;
import com.gridworld.grid.Grid;

public class ReadAndWrite {
	
	
	
	public static void writeGridToFile(Grid grid, CoordinatePair coords) {
		
		try{
		    
			PrintWriter writer = new PrintWriter("out.map", "UTF-8");
		    
		    // Get coordinates of start and goal squares, build strings, and write to file
			
			String start = coords.sStart.toString();
			String end = coords.sGoal.toString();
		    writer.println(start);
		    writer.println(end);
		    
		    
			
			
		    
		    //TODO: coordinates of the centers of the hard to traverse regions
		    //TODO: character representation of the grid
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}

		
	}
	

}
