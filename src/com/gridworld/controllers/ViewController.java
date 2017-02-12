package com.gridworld.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.gridworld.app.Main;
import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.FiftyGrids;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridSquare;
import com.gridworld.grid.SquareColor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

public class ViewController implements Initializable{
	
	@FXML Button displayButton;
	@FXML ListView<CoordinatePair> mapList;
	@FXML AnchorPane gridpaneHolder;
	@FXML TextFlow infoText;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		// There needs to be a list of grids somewhere with start and stop points so I can add them to the list, and when they're clicked so I can add them to the gridpane
		
		//System.out.println("ViewController started");

		
		FiftyGrids list = Main.gridList;
		System.out.println(list);
		//System.out.println("got gridlist");

		ArrayList<Grid> grids = list.gridsList;
		//System.out.println("got internal list of grids");
		//System.out.println(grids);
		
		ArrayList<CoordinatePair> clists = new ArrayList<CoordinatePair>();
		for(int i=0; i < grids.size(); i++) {
			clists.addAll(grids.get(i).pathPoints);
		}
		
		
		
		ObservableList<CoordinatePair> oblist = FXCollections.observableArrayList(clists);
		mapList.setItems(oblist);
		//System.out.println("made listview with grids");
		/*
		mapList.setCellFactory(param -> new ListCell<Grid>() {
		    @Override
		    protected void updateItem(Grid item, boolean empty) {
		        super.updateItem(item, empty);
		        setText(item.toString());
		    }
		});
		*/
		
		//System.out.println("ListView should be working");

		
        displayButton.setOnAction(this::handleButtonAction);
		
    }
	
	@FXML
    private void handleButtonAction(ActionEvent e) {
        //System.out.println("Button pushed!");
        Button b = (Button) e.getSource();
        if (b == displayButton) {
        	gridpaneHolder.getChildren().clear();
        	
        	CoordinatePair cord = mapList.getSelectionModel().getSelectedItem();
        	
        	Grid grid = cord.parent;
        	
        	GridPane newGridDisplay = genGridPane(grid, cord);
        	
        	gridpaneHolder.getChildren().addAll(newGridDisplay);
        }
	}
	
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
                   
                   rec.setOnMouseClicked(new EventHandler<MouseEvent>()
                   {
                       public void handle(MouseEvent t) {
                    	                       	   
                    	   
                       }
                   });
                   
                   GridPane.setRowIndex(rec, row);
                   GridPane.setColumnIndex(rec, col);
                   gridPane.getChildren().addAll(rec);
               }
        }
		
		
		return gridPane;		
	}

}
