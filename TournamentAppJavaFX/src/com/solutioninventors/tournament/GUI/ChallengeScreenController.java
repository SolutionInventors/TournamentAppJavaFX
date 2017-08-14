package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ChallengeScreenController {
	
	private String TournamentName;

	public String getTournamentName() {
		return TournamentName;
	}

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		System.out.println(TournamentName);
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");}

}
