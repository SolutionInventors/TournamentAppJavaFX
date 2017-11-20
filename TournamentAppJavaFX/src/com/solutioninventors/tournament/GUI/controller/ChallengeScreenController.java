package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChallengeScreenController {
	@FXML private AnchorPane  rootPane;
	@FXML private TextField txtnoOfrounds;
	@FXML private Text txtTourHighlight; 
	@FXML private Text txtdisplay;
	@FXML private Label  lbltourtype; 
	@FXML private Label lbltourapp;
	
	
	private String TournamentName;
	private Btn btn = new Btn();
	private Boolean goalScored;
	private Font font[] = new Font[3];
	  
	
	public String getTournamentName() {
		return TournamentName;
	}
	
	public void initialize() {
		font = CommonMethods.loadfonts();
		
		lbltourtype.setFont(font[1]);//Knockout Specs
		txtdisplay.setFont(font[0]);//the display
		lbltourapp.setFont(font[0]);//TOURNAMNET APP
		txtTourHighlight.setFont(font[0]);
		CommonMethods.isNumber(txtnoOfrounds);
	}
	
	
	public void setTournamentName(String tournamentName, Boolean goalScored) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		btn.previous(rootPane, event, "TournamentTypeScreen.fxml", "tourtypecss.css", "Tournament App");}
	
	@FXML
	public void next(ActionEvent event) throws IOException  {
		if (txtnoOfrounds.getText().isEmpty()) {
			CommonMethods.ErrorMessage("Please check input", "No of round cannot be empty");
		} else {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
			InputCompetitorController ic = (InputCompetitorController) loader.getController();
			ic.setChallengeTournament(TournamentName, goalScored,Integer.valueOf(txtnoOfrounds.getText()));
			btn.next(rootPane, root, "InputCompetitorScreen.fxml","commonStyle.css");
		}
	}
	@FXML
	public void cancel(ActionEvent event) throws IOException {
		btn.cancel(rootPane);
	}
	

}
