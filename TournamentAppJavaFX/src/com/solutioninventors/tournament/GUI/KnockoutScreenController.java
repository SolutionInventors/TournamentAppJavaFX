package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KnockoutScreenController {
	@FXML
	private ToggleGroup ElimType;
	@FXML
	Spinner<Integer> spinner;
	//Spinner(int min, int max, int initialValue, int amountToStepBy)
	// Value factory.
	

	private String TournamentName;

	public String getTournamentName() {
		return TournamentName;
	}

	// public KnockoutScreenController() {
	// spinner.setValueFactory(valueFactory);}
	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
		System.out.println("name received");

		
		
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
		System.out.println(TournamentName);
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("InputCompetitorScreen.fxml").openStream());
		TestClass ic = (TestClass) loader.getController();
		//ic.setKOtournament(TournamentName, spinner.getValue());
		ic.setKOtournament(TournamentName,4);
		//System.out.println(spinner.getValue());
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
	}
}// end class
