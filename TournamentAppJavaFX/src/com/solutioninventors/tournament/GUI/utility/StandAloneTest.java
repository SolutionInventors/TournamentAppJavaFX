package com.solutioninventors.tournament.GUI.utility;

import com.solutioninventors.tournament.GUI.controller.CommonMethods;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StandAloneTest extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath+"Error"
					+ ".fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Stand Alone Test");
			/*CommonMethods cm = new CommonMethods();
			cm.ErrorMessage("Invalid no", "There was an error");*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
