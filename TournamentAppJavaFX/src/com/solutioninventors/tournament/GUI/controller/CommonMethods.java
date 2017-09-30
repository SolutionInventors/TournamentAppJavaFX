/**
 *@Author: Oguejiofor Chidiebere
 *CommonMethods.java
 *30 Sep. 2017
 *10:05:21 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CommonMethods {

	private Tournament tournament;

	public void about() throws IOException {
		Stage aboutStage = new Stage();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "About.fxml"));
		Scene scene = new Scene(root);
		aboutStage.setScene(scene);
		aboutStage.show();
		aboutStage.setTitle("Credits");

	}

	public void help() throws IOException {
		Stage helpStage = new Stage();
		helpStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "Help.fxml"));
		Scene scene = new Scene(root);
		helpStage.setScene(scene);
		helpStage.show();
		helpStage.setTitle("How to use application");

	}

	public void opentournament(MouseEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("solutionInventorTournament ", " *sit"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*"));
		File seletedfile = fc.showOpenDialog(null);
		// File tourFile = new File(seletedfile.getName() + ".sit");
		if (seletedfile != null) {
			try {
				tournament = Tournament.loadTournament(seletedfile);

				 ((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
				FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
				ic.setTournament(tournament);
				ic.init();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.setTitle(tournament.getName());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TournamentEndedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void save(Tournament tour)  {
		try {
			tour.save();
		} catch ( Exception e) {
			System.out.println("You haven't saved for the first time");
			saveas(tour);
		}
	}
	
	public void saveas(Tournament tour)  {
		FileChooser fileChooser = new FileChooser();
		Stage primaryStage = new Stage();
		File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		if (tournamentFile !=null) {
			try {
				Tournament.saveAs(tour, tournamentFile);
			} catch (IOException | TournamentException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void opentournament() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("solutionInventorTournament ", " *sit"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*"));
		File seletedfile = fc.showOpenDialog(null);
		// File tourFile = new File(seletedfile.getName() + ".sit");
		if (seletedfile != null) {
			try {
				tournament = Tournament.loadTournament(seletedfile);
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
				FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
				ic.setTournament(tournament);
				ic.init();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
				primaryStage.setTitle(tournament.getName());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TournamentEndedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}// end class
