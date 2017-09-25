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
	
	
	
		 // private Btn 	btn = new Btn();
	Transition trans = new Transition();
		  private Tournament tournament;
	// Event Listener on Label[#NewT].onMouseClicked
	@FXML
	public void newTournament(MouseEvent event) throws IOException {
		 trans.FadeOut(rootPane, "TourScreen.fxml");
		
	}
	@FXML
	public void continuetour(MouseEvent event) throws IOException, TournamentEndedException {
		FileChooser fc = new FileChooser();
		File seletedfile = fc.showOpenDialog(null);
		//File tourFile = new File(seletedfile.getName() + ".sit");
		
		try {
			tournament = Tournament.loadTournament(seletedfile);
		} catch (IOException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath+"FRSCIScreen.fxml").openStream());
		FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
		ic.setTournament(tournament);
		ic.init();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle(tournament.getName());
	}//end continue tour

	@FXML
	public void closeApp(MouseEvent event)  {
		Platform.exit();
	
	}
	public void About(MouseEvent event) throws IOException {
		Stage aboutStage = new Stage();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath+"About.fxml"));
		Scene scene = new Scene(root);
		aboutStage.setScene(scene);
		aboutStage.show();
		aboutStage.setTitle("Welcome hahaha");
	
		
	}
	
	public void Help(MouseEvent event) throws IOException {
		 trans.FadeOut(rootPane, "Help.fxml");
	
		
	}
	
}
