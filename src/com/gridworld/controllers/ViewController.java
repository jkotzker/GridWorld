package com.gridworld.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.gridworld.*;
import com.gridworld.app.Main;
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
	@FXML ListView<Grid> mapList;
	@FXML AnchorPane gridpaneHolder;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		// There needs to be a list of grids somewhere with start and stop points so I can add them to the list, and when they're clicked so I can add them to the gridpane
		
		FiftyGrids list = Main.gridList;
		ArrayList<Grid> grids = list.gridsList;
		ObservableList oblist = FXCollections.observableArrayList(grids);
		mapList = new ListView<Grid>(oblist);
		
        displayButton.setOnAction(this::handleButtonAction);
		
    }
	
	@FXML
    private void handleButtonAction(ActionEvent e) {
        //System.out.println("Button pushed!");
        Button b = (Button) e.getSource();
        if (b == displayButton) {
        	gridpaneHolder.getChildren().clear();
        	
        	Grid grid = mapList.getSelectionModel().getSelectedItem();
        	
        	GridPane newGridDisplay = com.gridworld.app.Util.genGridPane(grid);
        	
        	gridpaneHolder.getChildren().addAll(newGridDisplay);
        }
	}

}
