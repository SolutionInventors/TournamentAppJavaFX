package com.solutioninventors.tournament.GUI.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.GUI.utility.Transition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TournamentTypeScreenController {
	
	@FXML private Text txtdisplay;
	@FXML private Text txtTourHighlight;
	@FXML private Label lbltourtype;
	//@FXML private Label lbltourapp;
	@FXML private Label lbltapp;
	@FXML private RadioButton rbKnockOut;
	@FXML private ToggleGroup TorType;
	@FXML private TextField tournamentName;
	@FXML private RadioButton rbChallenge;
	@FXML private RadioButton rbMultiStage;
	@FXML private RadioButton rbGroup;
	@FXML private AnchorPane  rootPane;
	@FXML private CheckBox goalsScored;
	private String tournamentAppName;
	
	
	private String message;
	private String nextFxml = "Knockout.fxml";
	private Boolean goalScored = true;
	private Btn btn = new Btn();
	private Font font[] = new Font[3];
	
	
	@FXML public void radioSelected(ActionEvent event) {
		if (rbChallenge.isSelected()) {
			message = "In a                                     champions retain their title until they are defeated by an opponent, known as the challenger.The right to become a contender may be awarded through a tournament, as in chess, or through a ranking system";
			nextFxml = "Challenge.fxml";
			txtTourHighlight.setText("CHALLENGE TOURNAMENT");
		} else if (rbMultiStage.isSelected()) {
			message = "In a                                     The competitors are divided into multiple groups, which play separate round-robins in parallel.Many tournaments are held in multiple stages, with the top teams in one stage progressing to the next.";
			nextFxml = "MultiStage.fxml";
			txtTourHighlight.setText("MULTISTAGE TOURNAMENT");
		} else if (rbGroup.isSelected()) {
			message = "In a                                , league, division or conference involves all competitors playing a number of fixtures Points are awarded for each fixture, with competitors ranked based either on total number of points or average points per fixture.";
			nextFxml = "GroupStage.fxml";
			txtTourHighlight.setText("GROUP TOURNAMENT");
		} else {
			message = "In a                                      is divided into rounds each competitors plays at least one fixture per round thee winner of each fixture advances to the next round. Knock out tournament models include single elimination and double elimination";
			nextFxml = "Knockout.fxml";
			txtTourHighlight.setText("KNOCKOUT TOURNAMENT");
		} // end if

		txtdisplay.setText(message);

	}// end radio select

	@FXML
	public void updateGoalScored(ActionEvent event){
		if (goalsScored.isSelected()) {
			goalScored = true;
		} else {
			goalScored = false;
		}
	}// end updateGoalScored
	

	@FXML
	public void previous(ActionEvent event) throws IOException {
		Btn btn = new Btn();
		btn.cancel(rootPane);
	}// end previous

	@FXML
	public void next(ActionEvent event) throws IOException {
		if(tournamentName.getText().trim().equals("") || tournamentName.getText() == null) {
			tournamentAppName = "Tournament";
		}else
			tournamentAppName = tournamentName.getText();
		//((Node) event.getSource()).getScene().getWindow().hide();
		//Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+nextFxml).openStream());
		
		//to pass the tournament name to the next screen
		switch (nextFxml) {
		
		case "Challenge.fxml":
			ChallengeScreenController ch = (ChallengeScreenController) loader.getController();
			ch.setTournamentName(tournamentAppName,goalScored);
			break;
		case "MultiStage.fxml":
			MultiStageScreenController ms = (MultiStageScreenController) loader.getController();
			ms.setTournamentName(tournamentAppName,goalScored);
			break;
		case "GroupStage.fxml":
			GroupStageScreenController gr = (GroupStageScreenController) loader.getController();
			gr.setTournamentName(tournamentAppName,goalScored);
			break;
		default:
			KnockoutScreenController ko = (KnockoutScreenController) loader.getController();
			ko.setTournamentName(tournamentAppName,goalScored);
			break;
		}//end switch

		btn.next(rootPane, root, nextFxml,"commonStyle.css");
	}// end previous

	
	
	public void cancel(ActionEvent event) throws IOException {
		
		btn.previous(rootPane, event, "WelcomeScreen.fxml", "welcomeScreen.css", "Tournament App");
		
	}
	public void initialize() throws FileNotFoundException, URISyntaxException {
		rootPane.setOpacity(0);
		Transition.FadeIn(rootPane);
		font = CommonMethods.loadfonts();
		txtdisplay.setFont(font[0]);
		lbltourtype.setFont(font[1]);
		lbltapp.setFont(font[0]);
		
	}


}// end class
