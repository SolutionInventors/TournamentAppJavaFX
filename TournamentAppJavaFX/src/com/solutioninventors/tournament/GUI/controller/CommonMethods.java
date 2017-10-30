/**
 *@Author: Oguejiofor Chinedu Knight
 *CommonMethods.java
 *30 Sep. 2017
 *10:05:21 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.solutioninventors.tournament.GUI.utility.ConfirmBox;
import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.FileIsOpenException;
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
	InputStream logoURL = getClass().getResourceAsStream(Paths.images + "logo.png");

	public Font[] loadfonts()  {
		InputStream url1 = getClass().getResourceAsStream(Paths.fonts + "twcenmt.ttf");
		InputStream url2 = getClass().getResourceAsStream(Paths.fonts + "TwCenMTCondensed.ttf");
		InputStream url3 = getClass().getResourceAsStream(Paths.fonts + "Pristina.ttf");
		final Font font[] = new Font[3];
		font[0] = Font.loadFont(url1, 19);

		font[1] = Font.loadFont(url2, 19);
		font[2] = Font.loadFont(url3, 19);
		return font;
	}

	public void about() throws IOException, URISyntaxException {
		Stage aboutStage = new Stage();
		aboutStage.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "About.fxml"));
		Scene scene = new Scene(root);
		
		aboutStage.getIcons().add(new Image(logoURL));
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
		helpStage.getIcons().add(new Image(logoURL));
		helpStage.setResizable(false);
		helpStage.setScene(scene);
		helpStage.sizeToScene();
		helpStage.setTitle("How to use application");
		helpStage.show();
	}

	
	
	public void music(Stage musicStage) throws IOException, URISyntaxException {
		if (musicStage.isShowing()) {
			musicStage.hide();
		}else {
			musicStage.show();
		}
		
		
	}
	

	public void opentournament(MouseEvent event) throws URISyntaxException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Documents"));

		fc.getExtensionFilters().add(new ExtensionFilter("solutionInventorTournament ", " *sit"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*"));
		File seletedfile = fc.showOpenDialog(null); 
		
		if (seletedfile != null) {
			try {
				tournament = Tournament.loadTournament(seletedfile);
				if (event != null) {
					((Node) event.getSource()).getScene().getWindow().hide();
				}
				Stage window = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
				FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
				ic.setTournament(tournament);
				ic.init();
				window.getIcons().add(new Image(logoURL));
				Scene scene = new Scene(root);

				window.setOnCloseRequest(e -> {
					e.consume();
					closeprogram(window);
				});

				window.setScene(scene);
				window.setResizable(false);

				window.setTitle(tournament.getName());

				window.show();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (TournamentEndedException e) {
				e.printStackTrace();
			} catch (FileIsOpenException e) {
				ErrorMessage("File Open","Cannot open the same file");
			}
		}
	}

	private void closeprogram(Stage window) {
		Boolean answer = ConfirmBox.display("Save", "Do you want to save changes to "+tournament.getName());
		if (answer) {
			save(tournament);
		}
		window.close();
		Tournament.closeFile(tournament.getTournamentFile());
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

	public void ErrorMessage(String err, String message) {
		try {
		Stage errorWindow = new Stage();
		errorWindow.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath + "Error.fxml").openStream());
		ErrorController er = (ErrorController) loader.getController();
		er.setMessage(err, message);
		Scene scene = new Scene(root);
		errorWindow.getIcons().add(new Image(logoURL));
		errorWindow.setResizable(false);
		errorWindow.sizeToScene();
		scene.getStylesheets().add(getClass().getResource(Paths.css + "commonStyle.css").toExternalForm());
		errorWindow.setScene(scene);
		errorWindow.show();
		errorWindow.setTitle(err);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
