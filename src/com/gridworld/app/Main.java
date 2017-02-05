package com.gridworld.app;

import com.gridworld.exceptions.CoordinateException;
import com.gridworld.grid.FiftyGrids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static FiftyGrids gridList;
	
	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/gridworld/views/mainScreen.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root,1366,768);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Map Generator v0.1");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("started");
		
		try {
			gridList = new FiftyGrids();
			System.out.println("gridlist generated");
		} catch (CoordinateException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("gridlist should be generated.");
		
		launch(args);
	}

}
