package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class GroupStageScreenController {
	@FXML
	private ToggleGroup type;
	
	public void showme(ActionEvent e) {
		
	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		ButtonsController btn = new ButtonsController();
		btn.previous(event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");}
}
