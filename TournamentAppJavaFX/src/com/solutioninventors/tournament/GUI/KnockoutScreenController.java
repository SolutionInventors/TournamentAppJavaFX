package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ToggleGroup;

public class KnockoutScreenController {
	@FXML
	private ToggleGroup ElimType;

	
	@FXML
	public void previous(ActionEvent event) throws IOException {
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");}
}//end class
