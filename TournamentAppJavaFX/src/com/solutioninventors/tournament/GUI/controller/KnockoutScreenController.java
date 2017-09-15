package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class KnockoutScreenController {
	@FXML private ToggleGroup ElimType;
	@FXML private TextField txtNoofcompetitors;
	@FXML private CheckBox homeandAway;
	@FXML private RadioButton singleelim;
	@FXML private RadioButton doubleelim;
	@FXML private Text txtTourHighlight;
	@FXML private Label  lblhomeAway;
	@FXML private AnchorPane  rootPane;
		  private boolean singleDoubleElim = true;
		  private boolean HomeandAwayFixture = false;
		  private Btn btn = new Btn();
		  private Boolean goalScored;
	
	//Spinner(int min, int max, int initialValue, int amountToStepBy)
	// Value factory.
	

	private String TournamentName;

	public String getTournamentName() {
		return TournamentName;
		
	}
	int abc;
	// public KnockoutScreenController() {
	// spinner.setValueFactory(valueFactory);}
	public void setTournamentName(String tournamentName, Boolean goalScored) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
	}
	
	@FXML
	public void tourselected(ActionEvent e) {
		if (singleelim.isSelected()) {
			singleDoubleElim=true;
			lblhomeAway.setVisible(true);
			homeandAway.setVisible(true);
			HomeandAwayFixture = false;
		} else if (doubleelim.isSelected()) {
			singleDoubleElim=false;
			lblhomeAway.setVisible(false);
			homeandAway.setVisible(false);
			HomeandAwayFixture = false;
		}
	}
	
	@FXML
	public void homeAway(ActionEvent e) {
		if (homeandAway.isSelected()) {
			HomeandAwayFixture = true;
		}else
			HomeandAwayFixture = false;
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
	
		btn.previous(rootPane,event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}

	
	@FXML
	public void next(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
		
		InputCompetitorController ic = (InputCompetitorController) loader.getController();
		ic.setKOtournament(TournamentName, goalScored, Integer.valueOf(txtNoofcompetitors.getText()), singleDoubleElim, HomeandAwayFixture);
		btn.next(rootPane, root, "Input.fxml");
		
		
	}
	@FXML
	public void cancel(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();
		
	}
}// end class
