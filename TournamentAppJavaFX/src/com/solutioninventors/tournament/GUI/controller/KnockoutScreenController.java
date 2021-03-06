package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class KnockoutScreenController {
	@FXML private ToggleGroup ElimType;
	@FXML private TextField txtNoofcompetitors;
	@FXML private CheckBox homeandAway;
	@FXML private CheckBox checkBoxShuffleComps;
	@FXML private RadioButton singleelim;
	@FXML private RadioButton doubleelim;
	@FXML private Text txtTourHighlight; 
	@FXML private Text txtdisplay;
	@FXML private Label  lblhomeAway;
	@FXML private Label  lbltourtype; 
	@FXML private Label lbltourapp;
	@FXML private AnchorPane  rootPane;
	private boolean isKnockoutSingle = true;
	private boolean HomeandAwayFixture = false;
	private Btn btn = new Btn();
	private Boolean goalScored;
	private Font font[] = new Font[3];



	private String TournamentName;

	public String getTournamentName() {
		return TournamentName;

	}
	public void initialize() {
		font = CommonMethods.loadfonts();

		lbltourtype.setFont(font[1]);//Knockout Specs
		txtdisplay.setFont(font[0]);//the display
		lbltourapp.setFont(font[0]);//TOURNAMNET APP
		txtTourHighlight.setFont(font[0]);

		//only numbers
		// force the field to be numeric only
		CommonMethods.isNumber(txtNoofcompetitors);


	}


	// public KnockoutScreenController() {
	// spinner.setValueFactory(valueFactory);}
	public void setTournamentName(String tournamentName, Boolean goalScored) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
	}

	@FXML
	public void tourselected(ActionEvent e) {
		if (singleelim.isSelected()) {
			isKnockoutSingle=true;
			lblhomeAway.setVisible(true);
			homeandAway.setVisible(true);
			HomeandAwayFixture = false;
		} else if (doubleelim.isSelected()) {
			isKnockoutSingle=false;
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
	public void previous(ActionEvent event) throws IOException {

		btn.previous(rootPane,event,"TournamentTypeScreen.fxml", "tourtypecss.css", TournamentName);
	}


	@FXML
	public void next(ActionEvent event) throws IOException {
		double validator = 0;
		if (!txtNoofcompetitors.getText().isEmpty()) {
			validator = ( Math.log( Integer.valueOf(txtNoofcompetitors.getText()) ) )/
					( Math.log( 2 ) );
		}

		if ( txtNoofcompetitors.getText().isEmpty()) {
			//AlertBox.display("Invalid No of Rounds", "The no of rounds cannot be empty");
			CommonMethods.ErrorMessage("Error", "The Number of Competitors Cannot be empty");
		}else if ( Integer.valueOf(txtNoofcompetitors.getText())<2  ) {
			CommonMethods.ErrorMessage("Invalid of competitors", "The number of Competitors must greater than 2");
		}else if ( ! ( validator % 1 == 0.0f)  ) {
			CommonMethods.ErrorMessage("Invalid number", "The number of Competitors must be a power of 2 ie 4,8,16,32,64,128");
		}else {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
			InputCompetitorController ic = (InputCompetitorController) loader.getController();
			ic.setKOtournament(TournamentName, goalScored, Integer.valueOf(txtNoofcompetitors.getText()), isKnockoutSingle, HomeandAwayFixture,checkBoxShuffleComps.isSelected());
			btn.next(rootPane, root, "Input.fxml","commonStyle.css");
			//btn.next(rootPane, root);
		}


	}
	@FXML
	public void cancel(ActionEvent event) throws IOException {
		btn.cancel(rootPane);		
	}
}// end class
