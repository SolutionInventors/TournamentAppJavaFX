package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ChallengeScreenController {
	@FXML private AnchorPane  rootPane;
	@FXML private TextField txtnoOfrounds;
	
	private String TournamentName;
	private Btn btn = new Btn();
	private Boolean goalScored;
	private Boolean standardbreaker;
	
	public String getTournamentName() {
		return TournamentName;
	}

	public void setTournamentName(String tournamentName, Boolean goalScored, Boolean standardbreaker) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
		this.standardbreaker = standardbreaker;
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		System.out.println(TournamentName);
		btn.previous(rootPane, event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");}
	
	@FXML
	public void next(ActionEvent event) throws IOException  {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
		if (standardbreaker) {
			InputCompetitorController ic = (InputCompetitorController) loader.getController();
			ic.setChallengeTournament(TournamentName, goalScored,Integer.valueOf(txtnoOfrounds.getText()));
			btn.next(rootPane, root, "InputCompetitorScreen.fxml");
		} else {
			TieBreakerController tb = (TieBreakerController) loader.getController();
			tb.setChallengeTournament(TournamentName, goalScored,Integer.valueOf(txtnoOfrounds.getText()));
			btn.next(rootPane, root, "TieBreaker.fxml");
		}
	
		
	}
	@FXML
	public void closeApp(MouseEvent event)  {
		Platform.exit();
	
	}
}
