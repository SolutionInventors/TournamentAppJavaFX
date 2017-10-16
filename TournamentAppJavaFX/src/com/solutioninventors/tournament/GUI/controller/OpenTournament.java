/**
 *@Author: Oguejiofor Chidiebere
 *OpenTournament.java
 *15 Oct. 2017
 *7:59:40 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OpenTournament implements Runnable {
	private Tournament tournament;
	private final MouseEvent event;
	private final File seletedfile;
	private Stage window;
	
	public OpenTournament(MouseEvent event, File seletedfile) {
		this.event= event;
		this.seletedfile = seletedfile;
		try {
		tournament = Tournament.loadTournament(seletedfile);
		if (event !=null) {
			 ((Node) event.getSource()).getScene().getWindow().hide();
		}
		URL url1 = getClass().getResource(Paths.images + "logo.jpg");
		window = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
		FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
		ic.setTournament(tournament);
		ic.init();
		
			window.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
		
		Scene scene = new Scene(root);
		/*
		 * window.setOnCloseRequest(e -> { e.consume(); closeprogram(); });
		 */
		
		window.setScene(scene);
		window.setResizable(false);
		
		window.setTitle(tournament.getName());
		window.show();
		} catch (URISyntaxException | IOException | TournamentEndedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		if (seletedfile != null) {
			
		}
	}
	

	

}
