package com.solutioninventors.tournament.GUI.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.Transition;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomeScreenController {
	@FXML private AnchorPane  rootPane;
	@FXML private Text txttourapp;
	@FXML private Text lblsolution;
	@FXML private Label lblmainmenu;
	@FXML private List<Label> lblTour;
	private CommonMethods ctr = new CommonMethods();
	private Transition trans = new Transition();
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	
	public void initialize() throws FileNotFoundException, URISyntaxException {
		font = cm.loadfonts();
		for (int i = 0; i < lblTour.size(); i++) {
			lblTour.get(i).setFont(font[1]);
		}
		txttourapp.setFont(font[0]);
		lblsolution.setFont(font[2]);
	}
	@FXML
	public void newTournament(MouseEvent event) throws IOException {
		 trans.FadeOut(rootPane, "TournamentTypeScreen.fxml", "tourtypecss.css");
		
	}
	@FXML
	public void continuetour(MouseEvent event) throws IOException, TournamentEndedException {
		ctr.opentournament(event);
	}//end continue tour

	@FXML
	public void closeApp(MouseEvent event)  {
		Platform.exit();
	
	}
	public void About(MouseEvent event) throws IOException {
		
		ctr.about();
	}
	public void Help(MouseEvent event) throws IOException {
		ctr.help();
	
		
	}
	
}
