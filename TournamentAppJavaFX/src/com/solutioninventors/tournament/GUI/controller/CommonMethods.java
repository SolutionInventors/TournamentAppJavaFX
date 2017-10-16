/**
 *@Author: Oguejiofor Chidiebere
 *CommonMethods.java
 *30 Sep. 2017
 *10:05:21 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CommonMethods {

	private Tournament tournament;

	public Font[] loadfonts() {
		URL url1 = getClass().getResource(Paths.fonts + "twcenmt.ttf");
		URL url2 = getClass().getResource(Paths.fonts + "TwCenMTCondensed.ttf");
		URL url3 = getClass().getResource(Paths.fonts + "Pristina.ttf");
		final Font font[] = new Font[3];
		try {

			font[0] = Font.loadFont(new FileInputStream(new File(url1.toURI())), 19);

			font[1] = Font.loadFont(new FileInputStream(new File(url2.toURI())), 19);
			font[2] = Font.loadFont(new FileInputStream(new File(url3.toURI())), 19);
		} catch (FileNotFoundException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return font;
	}

	public void about() throws IOException, URISyntaxException {
		Stage aboutStage = new Stage();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "About.fxml"));
		Scene scene = new Scene(root);
		URL url1 = getClass().getResource(Paths.images + "logo.jpg");
		aboutStage.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
		aboutStage.setResizable(false);
		aboutStage.sizeToScene();
		aboutStage.setScene(scene);
		aboutStage.show();
		aboutStage.setTitle("Credits");

	}

	public void help() throws IOException, URISyntaxException {
		Stage helpStage = new Stage();
		helpStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "Help.fxml"));
		Scene scene = new Scene(root);
		URL url1 = getClass().getResource(Paths.images + "logo.jpg");
		helpStage.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
		helpStage.setResizable(false);
		helpStage.setScene(scene);
		helpStage.sizeToScene();
		helpStage.setTitle("How to use application");
		helpStage.show();
	}

	public void startabs() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					help();
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		};
	}

	/*
	 * public void opentournament(MouseEvent event) { FileChooser fc = new
	 * FileChooser(); fc.setInitialDirectory( new
	 * File(System.getProperty("user.home") + System.getProperty("file.separator") +
	 * "Documents"));
	 * 
	 * fc.getExtensionFilters().add(new
	 * ExtensionFilter("solutionInventorTournament ", " *sit"));
	 * fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*")); File
	 * seletedfile = fc.showOpenDialog(null); // File tourFile = new
	 * File(seletedfile.getName() + ".sit");
	 * 
	 * 
	 * 
	 * OpenTournament newtournamnet = new OpenTournament(event,seletedfile);
	 * 
	 * 
	 * 
	 * ExecutorService executorService = Executors.newCachedThreadPool();
	 * executorService.execute(newtournamnet); // start task1
	 * executorService.shutdown();
	 * 
	 * }//end opentournament
	 */

	public void opentournament(MouseEvent event) throws URISyntaxException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"));

		fc.getExtensionFilters().add(new ExtensionFilter("solutionInventorTournament ", " *sit"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*"));
		File seletedfile = fc.showOpenDialog(null); 
		File tourFile = new	File(seletedfile.getName() + ".sit");
		if (seletedfile != null) {
			try {
				tournament = Tournament.loadTournament(seletedfile);
				if (event != null) {
					((Node) event.getSource()).getScene().getWindow().hide();
				}
				URL url1 = getClass().getResource(Paths.images + "logo.jpg");
				Stage window = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
				FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
				ic.setTournament(tournament);
				ic.init();
				window.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
				Scene scene = new Scene(root);

//				window.setOnCloseRequest(e -> {
//					e.consume();
//					closeprogram();
//				});

				window.setScene(scene);
				window.setResizable(false);

				window.setTitle(tournament.getName());

				window.show();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (TournamentEndedException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void save(Tournament tour) {
		try {
			tour.save();
		} catch (Exception e) {
			System.out.println("You haven't saved for the first time");
			saveas(tour);
		}
	}

	public void saveas(Tournament tour) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"));

		Stage primaryStage = new Stage();
		File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		if (tournamentFile != null) {
			try {
				Tournament.saveAs(tour, tournamentFile);
			} catch (IOException | TournamentException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * public void opentournament() { FileChooser fc = new FileChooser();
	 * fc.getExtensionFilters().add(new
	 * ExtensionFilter("solutionInventorTournament ", " *sit"));
	 * fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*")); File
	 * seletedfile = fc.showOpenDialog(null); // File tourFile = new
	 * File(seletedfile.getName() + ".sit"); if (seletedfile != null) { try {
	 * tournament = Tournament.loadTournament(seletedfile); Stage primaryStage = new
	 * Stage(); FXMLLoader loader = new FXMLLoader(); Parent root =
	 * loader.load(getClass().getResource(Paths.viewpath +
	 * "FRSCIScreen.fxml").openStream()); FRSCIScreenController ic =
	 * (FRSCIScreenController) loader.getController(); ic.setTournament(tournament);
	 * ic.init(); Scene scene = new Scene(root); primaryStage.setScene(scene);
	 * primaryStage.show(); primaryStage.setTitle(tournament.getName()); } catch
	 * (IOException e) { e.printStackTrace(); } catch (TournamentEndedException e) {
	 * e.printStackTrace(); } }
	 * 
	 * }
	 */

	// validates that the input is a number
	public void isNumber(TextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txt.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}
}// end class
