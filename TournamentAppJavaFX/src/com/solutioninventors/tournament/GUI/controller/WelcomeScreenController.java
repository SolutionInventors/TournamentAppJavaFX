package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.GUI.utility.Transition;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeScreenController {
	@FXML private Label NewT;
	@FXML private Label ContinueT;
	@FXML private Label Settings;
	@FXML private Label About;
	@FXML private Label Help;
	@FXML private Text 	mainmenu;
	@FXML private Text  close;
	@FXML private AnchorPane  rootPane;
	CommonMethods ctr = new CommonMethods();
	Transition trans = new Transition();
	@FXML
	public void newTournament(MouseEvent event) throws IOException {
		 trans.FadeOut(rootPane, "TourScreen.fxml");
		
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
