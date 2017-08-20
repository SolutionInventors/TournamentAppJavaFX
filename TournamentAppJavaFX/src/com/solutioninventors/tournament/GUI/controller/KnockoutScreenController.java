package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class KnockoutScreenController {
	@FXML
	private ToggleGroup ElimType;
	@FXML
	private TextField txtNoofcompetitors;
	Btn btn = new Btn();
	//Spinner(int min, int max, int initialValue, int amountToStepBy)
	// Value factory.
	

	private String TournamentName;

	public String getTournamentName() {
		return TournamentName;
	}
	int abc;
	// public KnockoutScreenController() {
	// spinner.setValueFactory(valueFactory);}
	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;

	}

	@FXML
	public void setvalue(MouseEvent e) {
		//final int initialValue = 2;
	/*	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4,
				initialValue);
		 spinner.setValueFactory(valueFactory);*/
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
		ic.setKOtournament(TournamentName,Integer.valueOf(txtNoofcompetitors.getText()));
		Btn.next(root, event, "Tournament App");
	}
	@FXML
	public void cancel(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
		
	}
}// end class
