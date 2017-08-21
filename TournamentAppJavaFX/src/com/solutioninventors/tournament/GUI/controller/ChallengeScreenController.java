package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ChallengeScreenController {
	@FXML private TextField txtnoOfrounds;
	
	private String TournamentName;
	private Btn btn = new Btn();
	
	public String getTournamentName() {
		return TournamentName;
	}

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		System.out.println(TournamentName);
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");}
	
	@FXML
	public void next(ActionEvent event) throws IOException  {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("InputCompetitorScreen.fxml").openStream());
		InputCompetitorController ic = (InputCompetitorController) loader.getController();
		ic.setChallengeTournament(TournamentName,Integer.valueOf(txtnoOfrounds.getText()));
		Btn.next(root, event, TournamentName);
		
	}

}