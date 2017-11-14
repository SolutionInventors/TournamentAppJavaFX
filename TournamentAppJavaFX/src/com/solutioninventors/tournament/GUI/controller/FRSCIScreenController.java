package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.solutioninventors.tournament.GUI.utility.ConfirmBox;
import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class FRSCIScreenController {
	@FXML private TabPane tabPane;
	@FXML private MenuItem MenuNew;
	@FXML private MenuItem MenuOpen;
	@FXML private MenuItem MenuSave;
	@FXML private MenuItem MenuSaveas;
	@FXML private MenuItem MenuClose;
	@FXML private MenuBar myMenuBar;
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
	//for the music
	private Stage musicStage;
	private Point2D anchorPt;
	private Point2D previousLocation;
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
		musicStage = new Stage();
	
		Parent root;
	
			root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "Music.fxml"));
		
		Scene scene = new Scene(root);
		InputStream url1 = getClass().getResourceAsStream(Paths.images + "logo.png");
		musicStage.getIcons().add(new Image(url1));
		musicStage.setResizable(false);
		musicStage.setScene(scene);
		musicStage.sizeToScene();
		musicStage.initStyle(StageStyle.TRANSPARENT);
		musicStage.setTitle("Music");
		musicStage.setX(10);
		initMovablePlayer(musicStage);
	//	musicStage.show();
		} catch (IOException e) {
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
		primaryStage.setOnCloseRequest(e1 -> {
			e.consume();
			closeprogram2(primaryStage);
		});
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament App");
		/*try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource(Paths.viewpath + "TournamentTypeScreen.fxml").openStream());
			Scene scene = new Scene(root);
			Stage window = new Stage();
			InputStream url1 = getClass().getResourceAsStream(Paths.images + "logo.png"); 
			window.getIcons().add(new Image(url1));
			window.centerOnScreen();
			window.setScene(scene);
			window.sizeToScene();
			window.setResizable(false);
			window.setOnCloseRequest(e1 -> {
				e.consume();
				closeprogram();
			});
			scene.getStylesheets().add(getClass().getResource(Paths.css + "welcomeScreen.css").toExternalForm());
			window.setTitle("Tournament APP");
			window.show();
		} catch (IOException e2) {
		}*/
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
	
		Stage stage = (Stage) myMenuBar.getScene().getWindow();
		closeprogram(stage);
		
		
	}
	public void music(ActionEvent e) throws IOException, URISyntaxException {
		control.music(musicStage);
	}
	
	public void help(ActionEvent e) throws IOException, URISyntaxException {
		control.help();
	}
	public void about(ActionEvent e) throws IOException, URISyntaxException {
		control.about();
	}
	
/*	public void closeprogram(Stage window) {
		int answer = ConfirmBox.display("Save", "Do you want to save changes to "+tournament.getName());
		if (answer ==1) {
			control.save(tournament);
		}
		window.close();
		Tournament.closeFile(tournament.getTournamentFile());
	}*/
	private void closeprogram(Stage window) {
		int answer = ConfirmBox.display("Save", "Do you want to save changes to " + tournament.getName());
		if (answer == 1) {
			if (control.save(tournament)) {
				Tournament.closeFile(tournament.getTournamentFile());
				window.close();
			}
		} else if (answer == 0) {
			window.close();
		}

	}
	
	private void closeprogram2(Stage window) {
		int answer = ConfirmBox.display("Close App", "Are you sure you want to exit");
		if (answer == 1) {
			window.close();

		}
	}
	//this makes the music player draggable
	 private void initMovablePlayer(Stage PRIMARY_STAGE) {
	      Scene scene = PRIMARY_STAGE.getScene();
	      // starting initial anchor point
	      scene.setOnMousePressed(mouseEvent
	              -> anchorPt = new Point2D(mouseEvent.getScreenX(),
	                      mouseEvent.getScreenY())
	      );

	      // dragging the entire stage
	      scene.setOnMouseDragged(mouseEvent -> {
	         if (anchorPt != null && previousLocation != null) {
	            PRIMARY_STAGE.setX(previousLocation.getX()
	                    + mouseEvent.getScreenX()
	                    - anchorPt.getX());
	            PRIMARY_STAGE.setY(previousLocation.getY()
	                    + mouseEvent.getScreenY()
	                    - anchorPt.getY());
	         }
	      });

	      // set the current location
	      scene.setOnMouseReleased(mouseEvent
	              -> previousLocation = new Point2D(PRIMARY_STAGE.getX(),
	                      PRIMARY_STAGE.getY())
	      );

	      // Initialize previousLocation after Stage is shown
	      PRIMARY_STAGE.addEventHandler(WindowEvent.WINDOW_SHOWN,
	              (WindowEvent t) -> {
	                 previousLocation = new Point2D(PRIMARY_STAGE.getX(),
	                         PRIMARY_STAGE.getY());
	              });
	   }
}// end class