package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class GroupStageScreenController {
	@FXML private ToggleGroup type;
	@FXML private TextField txtnoOfrounds;
	@FXML private TextField txtnoOfcomps;
	@FXML private TextField txtwinpoint;
	@FXML private TextField txtdrawpoint;
	@FXML private TextField txtlosspoint;
	@FXML private RadioButton swiss;
	@FXML private RadioButton round;
	@FXML private RadioButton doubleround;
		  private String TournamentName;
		  private Btn btn = new Btn();
		  private int tourType;//1 swiss, 2 round, 3 doubleRound

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}
	@FXML
	public void radioSelected(ActionEvent event) {
		if (swiss.isSelected()) {
			
		} else if (round.isSelected()) {
			
		}  else if (doubleround.isSelected()) {
			
		}

		
	}
	
	
	@FXML
	public void previous(ActionEvent event) throws IOException {
		
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
		InputCompetitorController ic = (InputCompetitorController) loader.getController();
		ic.setGroupTournament(TournamentName, Integer.valueOf(txtnoOfrounds.getText()),
				Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
				Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()));
		Btn.next(root, event, TournamentName);

	}
}
