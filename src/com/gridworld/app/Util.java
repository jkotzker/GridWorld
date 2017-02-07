package com.gridworld.app;

import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;
import com.gridworld.grid.SquareColor;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Util {
	
	public static GridPane genGridPane(Grid grid, CoordinatePair pair) {
		
		
    	GridPane gridPane = new GridPane();
        for(int row = 0; row < 120; row++){
               for(int col = 0; col < 160; col++){
                   Rectangle rec = new Rectangle();
                   rec.setWidth(5);
                   rec.setHeight(5);
                   GridSquare thisSquare = grid.GridSquares[row][col];
                   if(pair.path.contains(thisSquare)){
                	   rec.setFill(Color.GREEN);
                   }  
                   else if (thisSquare.memberOfHorizontalHighway || thisSquare.memberOfVerticalHighway){
                	   rec.setFill(Color.BLUE);
                   } 
                   else if (thisSquare.color == SquareColor.DARK_GRAY) {
                	   rec.setFill(Color.BLACK);
                   }
                   else if (thisSquare.color == SquareColor.LIGHT_GRAY) {
                	   rec.setFill(Color.GRAY);
                   }
                   else {
                	   
                	   rec.setFill(Color.WHITE);
                   }
                   
                   GridPane.setRowIndex(rec, row);
                   GridPane.setColumnIndex(rec, col);
                   gridPane.getChildren().addAll(rec);
               }
        }
		
		
		return gridPane;		
	}

}
