package com.solutioninventors.tournament.GUI.controller;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

public class ErrorController {
	@FXML private AnchorPane rootPane;
	@FXML private Label lblError;
	@FXML private Text txtErrorDetail;

	
	public void setMessage(String error, String detail) {
		lblError.setText(error);
		txtErrorDetail.setText(detail);
	}
	// Event Listener on Button.onAction
	@FXML
	public void next(ActionEvent event) {
		//((Node) event.getSource()).
		((Node) event.getSource()).getScene().getWindow().hide();
		
	}
}
