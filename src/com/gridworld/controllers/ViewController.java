package com.gridworld.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.gridworld.algorithm.Vertex;
import com.gridworld.app.Main;
import com.gridworld.app.ReadAndWrite;
import com.gridworld.grid.CoordinatePair;
import com.gridworld.grid.FiftyGrids;
import com.gridworld.grid.Grid;
import com.gridworld.grid.GridRectangle;
import com.gridworld.grid.GridSquare;
import com.gridworld.grid.SquareColor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewController implements Initializable{
	
	@FXML Button displayButton;
	@FXML Button saveMapButton;
	@FXML Button loadMapButton;
	@FXML Button quitButton;
	@FXML ListView<CoordinatePair> mapList;
	@FXML AnchorPane gridpaneHolder;
	@FXML TextArea infoText;
	@FXML Parent root;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		// There needs to be a list of grids somewhere with start and stop points so I can add them to the list, and when they're clicked so I can add them to the gridpane
		
		//System.out.println("ViewController started");

		
		FiftyGrids list = Main.gridList;
		//System.out.println(list);
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
        saveMapButton.setOnAction(this::handleButtonAction);
        loadMapButton.setOnAction(this::handleButtonAction);
        quitButton.setOnAction(this::handleButtonAction);
		
    }
	
	@FXML
    private void handleButtonAction(ActionEvent e) {
		
        //System.out.println("Button pushed!");
        Button b = (Button) e.getSource();
        if (b == displayButton) {
        	infoText.clear();
        	gridpaneHolder.getChildren().clear();
        	
        	CoordinatePair cord = mapList.getSelectionModel().getSelectedItem();
        	
        	Grid grid = cord.parent;
     	   	int ind = mapList.getSelectionModel().getSelectedIndex() % 10;
     	   	//System.out.print("Index is "+ind);
        	
        	GridPane newGridDisplay = genGridPane(grid, cord, infoText, ind);
        	
        	gridpaneHolder.getChildren().addAll(newGridDisplay);
        }
        if (b == saveMapButton) {
        	CoordinatePair cord = mapList.getSelectionModel().getSelectedItem();
        	ReadAndWrite.writeGridToFile(cord.parent, cord, cord.toString());
        }
        if (b == loadMapButton){
        	Stage stage = new Stage();
        	FileChooser fileChooser = new FileChooser();
        	fileChooser.setTitle("Select Map File");
        	File file = fileChooser.showOpenDialog(stage);
        	Grid newGrid = ReadAndWrite.readGridFromFile(file.getAbsolutePath());
        	newGrid.performAllSearches(Main.heuristicVal, Main.searchVal);
        	Main.gridList.gridsList.add(newGrid);
        	
        	FiftyGrids list = Main.gridList;
    		//System.out.println(list);
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
        	
        }
        if (b == quitButton) {
        	
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        	System.exit(1);
        	
        }
	}
	 
	private static GridPane genGridPane(Grid grid, CoordinatePair pair, TextArea infoText, int index) {
		
		
    	GridPane gridPane = new GridPane();
        for(int row = 0; row < 120; row++){
               for(int col = 0; col < 160; col++){
                   GridSquare thisSquare = grid.GridSquares[row][col];
                   GridRectangle rec = new GridRectangle(thisSquare, index, infoText);
                   rec.setWidth(5);
                   rec.setHeight(5);
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
                    	   
                    	   GridSquare sq = rec.square;
                    	   Vertex vert = sq.getSavedVertex(index);
                    	   String info;
                    	   if (vert == null) {
                    		   info = "H: NULL \n G: NULL \n F: NULL";
                    	   } else {
                    		   
                        	   Double gval = vert.getG(0);
                        	   Double hval = vert.getH(0);
                        	   if(Double.toString(gval) == null)
                        		   gval = 0.0;
                        	   if(Double.toString(hval) == null)
                        		   hval = 0.0;
                        	   String gvalText = Double.toString(gval);
                        	   String hvalText = Double.toString(hval);
                        	   String fvalText = Double.toString(gval + hval);
                        	   info = "H: " + hvalText + "\n G: " + gvalText + "\n F: " + fvalText;
                    		   
                    	   }
                    	   infoText.setText(info);
                    	   
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
