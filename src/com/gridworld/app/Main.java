package com.gridworld.app;

import java.util.HashMap;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.grid.FiftyGrids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static FiftyGrids gridList;
	public static HashMap<String, Double> whiteCosts = new HashMap<String, Double>();
	public static HashMap<String, Double> lightgrayCosts = new HashMap<String, Double>();
	
	@Override
	public void start(Stage primaryStage) {
		
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(getClass().getResource("/com/gridworld/views/mainScreen.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root,1240,624);
			root.prefHeightProperty().bind(scene.heightProperty());
	        root.prefWidthProperty().bind(scene.widthProperty());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Map Generator v0.1.1");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("started");
		

		Main.whiteCosts.put("horiz", 0.5);
		Main.whiteCosts.put("vert", 0.5);
		Main.whiteCosts.put("diagonal", Math.pow(2, 0.5) / 2);

		Main.lightgrayCosts.put("horiz", 1.0);
		Main.lightgrayCosts.put("vert", 1.0);
		Main.lightgrayCosts.put("diagonal", Math.pow(2, 0.5));

		
		try {
			gridList = new FiftyGrids();
			//System.out.println("gridlist generated");
		} catch (CoordinateException e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	//	System.out.println("gridlist should be generated.");
		
		launch(args);
	}

}
