package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class TournamentTypeScreenController {
	String message;
	String nextFxml = "KnockoutScreen.fxml";
	
	@FXML
	private RadioButton rbKnockOut;
	@FXML
	private ToggleGroup TorType;
	@FXML
	private RadioButton rbChallenge;
	@FXML
	private RadioButton rbMultiStage;
	@FXML
	private RadioButton rbGroup;
	@FXML
	private TextArea txtAdisplay;
	@FXML
	private Button finish;

	// Event Listener on RadioButton[#rbKnockOut].onAction
	@FXML
	public void radioSelected(ActionEvent event) {
		if (rbChallenge.isSelected()) {
			message = "In a Challenge tournament";
			nextFxml = "ChallengeScreen.fxml";
		}else if(rbMultiStage.isSelected()) {
			message = "In a Challenge tournament";
			nextFxml = "MultiStageScreen.fxml";
		}else if(rbGroup.isSelected()) {
			message = "In a Group tournament";
			nextFxml = "GroupStageScreen.fxml";
		}else {
			message = "A knockout tournament is divided into rounds each competitors plays at least one fixture per round thee winner of each fixture advances to the next round. Knock out tournament models include single elimination and double elimination";
			nextFxml = "KnockoutScreen.fxml";
		}//end if
		
		txtAdisplay.setText(message);
		
		
	}//end radio select
	
	@FXML
	public void previous(ActionEvent event) throws IOException {
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
		
		/*((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("lookfeel.css").toExternalForm());
		primaryStage.show();
		primaryStage.setTitle("Tournament App");*/
}//end previous
	
	@FXML
	public void next(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(nextFxml));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
}//end previous
	
}//end class
