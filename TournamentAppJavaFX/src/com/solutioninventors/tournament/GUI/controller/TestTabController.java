package com.solutioninventors.tournament.GUI.controller;

import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.control.TabPane;

import javafx.scene.control.Tab;

public class TestTabController {

	@FXML
	private TabPane tabPane;
	Tournament tournament;

	// #########################Injectpart#############################################
	// Inject tab content
	@FXML
	private Tab tab1_fixtures; // from TabPaneRootView.fxml: <Tab fx:id="tab1_foo" ...>
	@FXML
	private Tab tab2_standingtable; // from TabPaneRootView.fxml: <Tab fx:id="tab2_bar" ...>

	// Inject tab controller
	@FXML
	private FixturesController tabfixController;// TabPaneRootView.fxml_include_fx:id="xxx_tab1foo_xxx" + "Controller"
	@FXML
	private StandingTableController tabstandController;// TabPaneRootView.fxml_include_fx:id="xxx_tab2bar_xxx" +
													// "Controller"
	// ###########################################################################################

	public void init() {
		tabPane.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
						
					if (newValue == tab1_fixtures) {
						System.out.println("- 2.Tab bar -");
						System.out.println("xxx_tab2bar_xxxController=" + tabfixController); // if =null => inject problem
						try {
							tabfixController.setTournament(tournament);
						} catch (TournamentEndedException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}
					} else if (newValue == tab2_standingtable) {
						System.out.println("- 1.Tab foo -");
						System.out.println("xxx_tab1foo_xxxController=" + tabstandController); // if =null => inject problem
						tabstandController.setTournament(tournament);
					} else {
						System.out.println("- another Tab -");
					}
				});
	}

	public void setTournament(Tournament tour) {
		tournament = tour;
	}

}// end class