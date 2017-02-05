package com.gridworld.app;

import com.gridworld.grid.Grid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static Grid grid;
	
	@Override
	public void start(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/com/gridworld/views/mainScreen.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root,1366,768);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Database Application");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		grid = new Grid();
		launch(args);
	}

}
