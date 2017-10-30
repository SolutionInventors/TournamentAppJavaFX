package com.solutioninventors.tournament.GUI.controller;

import java.io.InputStream;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ErrorController {
	@FXML private AnchorPane rootPane;
	@FXML private Label lblError;
	@FXML private Text txtErrorDetail;
	@FXML private ImageView imgBackground;
	private InputStream backgroundURL = getClass().getResourceAsStream(Paths.images + "TournamentAppBackground.jpg");
       
	
	public void setMessage(String error, String detail) {
		lblError.setText(error);
		txtErrorDetail.setText(detail);
		Image image;
                image = new Image(backgroundURL);
                imgBackground.setImage(image);
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void next(ActionEvent event) {
		//((Node) event.getSource()).
		((Node) event.getSource()).getScene().getWindow().hide();
		
	}
}
