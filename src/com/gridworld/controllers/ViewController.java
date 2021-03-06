package com.gridworld.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.gridworld.app.Main;
import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.FiftyGrids;
import com.gridworld.grid.Grid;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ViewController implements Initializable{
	
	@FXML Button displayButton;
	@FXML ListView<CoordinatePair> mapList;
	@FXML AnchorPane gridpaneHolder;
	
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
        	
        	GridPane newGridDisplay = com.gridworld.app.Util.genGridPane(grid, cord);
        	
        	gridpaneHolder.getChildren().addAll(newGridDisplay);
        }
	}

}
