package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

public class WelcomeScreenController {
	@FXML
	private Label NewT;
	@FXML
	private Label ContinueT;
	@FXML
	private Label Settings;
	@FXML
	private Label About;
	@FXML
	private Label Help;
	@FXML
	private Text mainmenu;

	// Event Listener on Label[#NewT].onMouseClicked
	@FXML
	public void newTournament(MouseEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("TournamentTypeScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
		
		
		/*
		LayoutSizingAligning li = new LayoutSizingAligning();
		li.start(primaryStage);*/
		
	}
	
	//called when the class is created
//	  public void initialize() {
//	        int count = 1 ;
//	        for (int i = 0; i <3; i++) {
//				System.out.println("I am here "+ i);
//			}
//	    }
}
