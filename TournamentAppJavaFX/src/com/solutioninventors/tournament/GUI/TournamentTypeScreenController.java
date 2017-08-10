package com.solutioninventors.tournament.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TournamentTypeScreenController implements Initializable {
	String message;
	String nextFxml = "KnockoutScreen.fxml";

	@FXML
	private RadioButton rbKnockOut;
	@FXML
	private ToggleGroup TorType;
	@FXML
	private TextField tournamentName;
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
		} else if (rbMultiStage.isSelected()) {
			message = "In a Challenge tournament";
			nextFxml = "MultiStageScreen.fxml";
		} else if (rbGroup.isSelected()) {
			message = "In a Group tournament";
			nextFxml = "GroupStageScreen.fxml";
		} else {
			message = "A knockout tournament is divided into rounds each competitors plays at least one fixture per round thee winner of each fixture advances to the next round. Knock out tournament models include single elimination and double elimination";
			nextFxml = "KnockoutScreen.fxml";
		} // end if

		txtAdisplay.setText(message);

	}// end radio select

	@FXML
	public void previous(ActionEvent event) throws IOException {
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");

	}// end previous

	@FXML
	public void next(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(nextFxml).openStream());
		
		//to pass the tournament name to the next screen
		switch (nextFxml) {
		case "ChallengeScreen.fxml":
			ChallengeScreenController ch = (ChallengeScreenController) loader.getController();
			ch.setTournamentName(tournamentName.getText());
			break;
		case "MultiStageScreen.fxml":
			MultiStageScreenController ms = (MultiStageScreenController) loader.getController();
			ms.setTournamentName(tournamentName.getText());
			break;
		case "GroupStageScreen.fxml":
			GroupStageScreenController gr = (GroupStageScreenController) loader.getController();
			gr.setTournamentName(tournamentName.getText());
			break;
		default:
			KnockoutScreenController ko = (KnockoutScreenController) loader.getController();
			ko.setTournamentName(tournamentName.getText());
			break;
		}//end switch

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
	}// end previous

	public void initialize() {
		finish.disableProperty().set(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}// end class
