package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class GroupStageScreenController {
	@FXML
	private ToggleGroup type;
	@FXML private TextField txtnoOfrounds;
	@FXML private TextField txtnoOfcomps;
	private String TournamentName;
	private ButtonsController btn = new ButtonsController();

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}
	
	@FXML
	public void next(ActionEvent event) throws IOException  {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("InputCompetitorScreen.fxml").openStream());
		InputCompetitorController ic = (InputCompetitorController) loader.getController();
		ic.setGroupTournament(TournamentName, Integer.valueOf(txtnoOfrounds.getText()), Integer.valueOf(txtnoOfcomps.getText()));
		btn.next(root, event, TournamentName);
		
	}
}
