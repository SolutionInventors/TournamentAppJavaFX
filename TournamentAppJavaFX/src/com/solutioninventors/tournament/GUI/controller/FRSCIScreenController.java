package com.solutioninventors.tournament.GUI.controller;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FRSCIScreenController {
	@FXML
	private TabPane tabPane;
	Tournament tournament;

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
	@FXML private StandingTable22Controller tabstandController;
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
				} catch (IncompleteFixtureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				});
	}

	public void setTournament(Tournament tour) throws TournamentEndedException {
		tournament = tour;
		tabfixController.setTournament(tournament);
	}

}// end class