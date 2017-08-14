package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ToggleGroup;

public class MultiStageScreenController {
	@FXML
	private ToggleGroup type;
	@FXML
	private ToggleGroup type1;

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
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}

}// end class
