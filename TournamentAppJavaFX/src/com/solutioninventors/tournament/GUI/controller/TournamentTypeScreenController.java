package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.GUI.utility.Transition;

import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TournamentTypeScreenController implements Initializable {
	String message;
	String nextFxml = "Knockout.fxml";
	Btn btn = new Btn();
	@FXML private RadioButton rbKnockOut;
	@FXML private ToggleGroup TorType;
	@FXML private TextField tournamentName;
	@FXML private RadioButton rbChallenge;
	@FXML private RadioButton rbMultiStage;
	@FXML private RadioButton rbGroup;
	@FXML private Text txtAdisplay;
	@FXML private Text txtTourHighlight;
	@FXML private Button finish;
	@FXML private AnchorPane  rootPane;

	// Event Listener on RadioButton[#rbKnockOut].onAction
	@FXML public void radioSelected(ActionEvent event) {
		if (rbChallenge.isSelected()) {
			message = "In a                                          champions retain their title until they are defeated by an opponent, known as the challenger.The right to become a contender may be awarded through a tournament, as in chess, or through a ranking system";
			nextFxml = "Challenge.fxml";
			txtTourHighlight.setText("CHALLENGE TOURNAMENT");
		} else if (rbMultiStage.isSelected()) {
			message = "In a                                     The competitors are divided into multiple groups, which play separate round-robins in parallel.Many tournaments are held in multiple stages, with the top teams in one stage progressing to the next.";
			nextFxml = "MultiStage.fxml";
			txtTourHighlight.setText("MULTISTAGE TOURNAMENT");
		} else if (rbGroup.isSelected()) {
			message = "In a                                   , league, division or conference involves all competitors playing a number of fixtures Points are awarded for each fixture, with competitors ranked based either on total number of points or average points per fixture.";
			nextFxml = "GroupStage.fxml";
			txtTourHighlight.setText("GROUP TOURNAMENT");
		} else {
			message = "In a                                          is divided into rounds each competitors plays at least one fixture per round thee winner of each fixture advances to the next round. Knock out tournament models include single elimination and double elimination";
			nextFxml = "Knockout.fxml";
			txtTourHighlight.setText("KNOCKOUT TOURNAMENT");
		} // end if

		txtAdisplay.setText(message);

	}// end radio select

	@FXML
	public void previous(ActionEvent event) throws IOException {
		Btn btn = new Btn();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");

	}// end previous

	@FXML
	public void next(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+nextFxml).openStream());
		
		//to pass the tournament name to the next screen
		switch (nextFxml) {
		case "Challenge.fxml":
			ChallengeScreenController ch = (ChallengeScreenController) loader.getController();
			ch.setTournamentName(tournamentName.getText());
			break;
		case "MultiStage.fxml":
			MultiStageScreenController ms = (MultiStageScreenController) loader.getController();
			ms.setTournamentName(tournamentName.getText());
			break;
		case "GroupStage.fxml":
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
		Transition.FadeIn(rootPane);
		finish.disableProperty().set(true);
	}
	public void cancel(ActionEvent event) throws IOException {
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void close(MouseEvent event)  {
		Platform.exit();
	
	}
}// end class
