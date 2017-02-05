package com.gridworld.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ViewController implements Initializable{
	
	@FXML Button displayButton;
	@FXML ListView<String> mapList;
	@FXML AnchorPane gridpaneHolder;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		// There needs to be a list of grids somewhere with start and stop points so I can add them to the list, and when they're clicked so I can add them to the gridpane
		
        
    }

}
