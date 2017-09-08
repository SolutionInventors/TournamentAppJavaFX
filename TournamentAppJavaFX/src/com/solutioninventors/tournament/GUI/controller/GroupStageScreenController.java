package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GroupStageScreenController {
	@FXML private Text		THeader;
	@FXML private TextField txtnoOfrounds;
	@FXML private TextField txtnoOfcomps;
	@FXML private TextField txtwinpoint;
	@FXML private TextField txtdrawpoint;
	@FXML private TextField txtlosspoint;
	@FXML private RadioButton swiss;
	@FXML private RadioButton round;
	@FXML private RadioButton doubleround;
	@FXML private AnchorPane  rootPane;
		  private String TournamentName;
		  private Btn btn = new Btn();
		  private int tourType = 1;//1 swiss, 2 round, 3 doubleRound
		private String message;

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}
	@FXML
	public void radioSelected(ActionEvent event) {
		if (swiss.isSelected()) {
			tourType = 1;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.editableProperty().set(true);
			txtnoOfrounds.setVisible(true);
			message = "In a Swiss style group tournament, the number of results is attempted to be shortened or reduced. The fixtures are determined from the current standing. This is achieved by pairing the top and bottom competitors";
		} else if (round.isSelected()) {
			tourType = 2;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.editableProperty().set(false);
			txtnoOfrounds.setVisible(false);
			message = "In a single round-robin tournament, each competitor plays every other competitor once. The no of rounds is determined by the no of competitors. The winner is determined by the ranking table which is based on the no of Wins, Draws , Goals Scored etc";
			
		}  else if (doubleround.isSelected()) {
			tourType = 3;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.setVisible(true);
			txtnoOfrounds.editableProperty().set(false);
			message = "In a double-round-robin tournament, each competitor plays every other competitor twice.The no of rounds is determined by the no of competitors.  Most association football leagues in the world are organized on a double round-robin basis, in which every team plays all others in its league once at home and once away.";
		}
		THeader.setText(message);
		
	}
	
	
	@FXML
	public void previous(ActionEvent event) throws IOException {
		
		btn.previous(rootPane,event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
		InputCompetitorController ic = (InputCompetitorController) loader.getController();
		ic.setGroupTournament(TournamentName, Integer.valueOf(txtnoOfrounds.getText()),
				Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
				Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()) ,tourType );
		btn.next(rootPane, event, "InputCompetitorScreen.fxml");

	}
}
