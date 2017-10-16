package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FRSCIScreenController {
	@FXML private TabPane tabPane;
	@FXML private MenuItem MenuNew;
	@FXML private MenuItem MenuOpen;
	@FXML private MenuItem MenuSave;
	@FXML private MenuItem MenuSaveas;
	@FXML private MenuItem MenuClose;
	private CommonMethods control = new CommonMethods();
	private Tournament tournament;

	// ###################################Inject
	// part#############################################
	// Inject tab content
	@FXML
	private Tab tab1_fixtures;
	@FXML
	private Tab tab2_results;
	@FXML
	private Tab tab3_standingtable;
	@FXML
	private Tab tab4_Competitors;
	@FXML
	private Tab tab5_inputScores;

	// Inject tab controller
	@FXML private FixturesController tabfixController;// TabPaneRootView.fxml_include_fx:id="xxx_tab1foo_xxx" + "Controller"
	@FXML private ViewResultsController tabresultController;// TabPaneRootView.fxml_include_fx:id="xxx_tab2bar_xxx" +
	@FXML private StandingTableController tabstandController;
	@FXML private CompetitorStatusController tabcompController;
	@FXML private InputResultsController tabinputscoreController;

	// ###########################################################################################

	public void init() {
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {

					try {

					if (newValue == tab1_fixtures) {
						
							tabfixController.setTournament(tournament);
						
						
					} else if(newValue == tab2_results) {
						tabresultController.setTournament(tournament);
					}else if(newValue == tab3_standingtable) {
						tabstandController.setTournament(tournament);
					}else if(newValue == tab4_Competitors) {
						tabcompController.setTournament(tournament);
					}else if(newValue == tab5_inputScores) {
						tabinputscoreController.setTournament(tournament);
					}
					
				} catch (TournamentEndedException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
				});
	
		MenuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, 
	                KeyCombination.SHORTCUT_DOWN));
		MenuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, 
                KeyCombination.SHORTCUT_DOWN));
		MenuSave.setAccelerator(new KeyCodeCombination(KeyCode.S, 
                KeyCombination.SHORTCUT_DOWN));
		/*MenuSaveas.setAccelerator(new KeyCodeCombination(KeyCode.N, 
                KeyCombination.));*/
	//to create the music interface
		try {
		Stage musicStage = new Stage();
		musicStage.initModality(Modality.APPLICATION_MODAL);
		Parent root;
	
			root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "Music.fxml"));
		
		Scene scene = new Scene(root);
		URL url1 = getClass().getResource(Paths.images + "logo.jpg");
		musicStage.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
		musicStage.setResizable(false);
		musicStage.setScene(scene);
		musicStage.sizeToScene();
		musicStage.setTitle("Music");
		musicStage.show();
		} catch (IOException | URISyntaxException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void changetab(int tabtoswitch) {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tabtoswitch); //select by index starting with 0
	}
	public void setTournament(Tournament tour) throws TournamentEndedException {
		tournament = tour;
		tabfixController.setTournament(tournament);
	}
	@FXML
	public void newTour(ActionEvent e) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath+"TournamentTypeScreen.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
	}
	public void openTour(ActionEvent event) throws URISyntaxException {
		control.opentournament(null);
	}
	public void saveTour(ActionEvent e) {
		control.save(tournament);
	}
	public void saveTouras(ActionEvent e) {
		control.saveas(tournament);
	}
	public void close(ActionEvent e) {
		Platform.exit();
		System.exit(0);
	}
	public void music(ActionEvent e) throws IOException, URISyntaxException {
		control.music();
	}
	
	public void help(ActionEvent e) throws IOException, URISyntaxException {
		control.help();
	}
	public void about(ActionEvent e) throws IOException, URISyntaxException {
		control.about();
	}
	
}// end class