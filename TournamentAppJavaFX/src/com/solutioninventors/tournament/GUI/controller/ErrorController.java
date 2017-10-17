package com.solutioninventors.tournament.GUI.controller;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ErrorController {
	@FXML private AnchorPane rootPane;
	@FXML private Label lblError;
	@FXML private Text txtErrorDetail;
	@FXML private ImageView imgBackground;
	private URL backgroundURL = getClass().getResource(Paths.images + "TournamentAppBackground.jpg");

	
	public void setMessage(String error, String detail) {
		lblError.setText(error);
		txtErrorDetail.setText(detail);
		Image image;
		try {
			image = new Image(new FileInputStream(new File(backgroundURL.toURI())));
			imgBackground.setImage(image);
		} catch (FileNotFoundException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void next(ActionEvent event) {
		//((Node) event.getSource()).
		((Node) event.getSource()).getScene().getWindow().hide();
		
	}
}
