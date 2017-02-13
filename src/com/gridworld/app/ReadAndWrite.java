package com.gridworld.app;

import java.io.IOException;
import java.io.PrintWriter;

import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.Grid;
import com.gridworld.grid.SquareColor;

public class ReadAndWrite {
	
	
	
	public static void writeGridToFile(Grid grid, CoordinatePair coords, String fileName) {
		
		System.out.println("Writer called.");
		System.out.println("Filename is "+fileName);
		
		try{
		    
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		    
		    // Get coordinates of start and goal squares, build strings, and write to file
			
			String start = coords.sStart.toString();
			String end = coords.sGoal.toString();
		    writer.println(start);
		    writer.println(end);
		    for(int i = 0; i < grid.centersOfHardRegions.size(); i++) {
		    	writer.println(grid.centersOfHardRegions.get(i).toString());
		    }
		    
		    for (int i = 0; i < 120; i++) {
				for (int j = 0; j < 160; j++) {
					
					if(grid.GridSquares[i][j].color == SquareColor.DARK_GRAY)
						writer.print("0");
					if(grid.GridSquares[i][j].color == SquareColor.WHITE && !grid.GridSquares[i][j].memberOfHorizontalHighway && !grid.GridSquares[i][j].memberOfVerticalHighway)
						writer.print("1");
					if(grid.GridSquares[i][j].color == SquareColor.LIGHT_GRAY && !grid.GridSquares[i][j].memberOfHorizontalHighway && !grid.GridSquares[i][j].memberOfVerticalHighway)
						writer.print("2");
					if(grid.GridSquares[i][j].color == SquareColor.WHITE && (grid.GridSquares[i][j].memberOfHorizontalHighway || grid.GridSquares[i][j].memberOfVerticalHighway))
						writer.print("a");
					if(grid.GridSquares[i][j].color == SquareColor.LIGHT_GRAY && (grid.GridSquares[i][j].memberOfHorizontalHighway || grid.GridSquares[i][j].memberOfVerticalHighway))
						writer.print("b");
				}
				writer.println();
			}
		    
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	

}
