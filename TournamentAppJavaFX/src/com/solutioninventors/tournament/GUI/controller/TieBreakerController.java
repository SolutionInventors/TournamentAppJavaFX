package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.utils.Breaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TieBreakerController {
	// from FXML
	@FXML private Button btnPrevious;
	@FXML private Button btnNext;
	@FXML private Button btnCancel;
	@FXML private ListView<String> listPossibleBreaker;
	@FXML private ListView<String> listSelectedBreaker;
	@FXML private Button btnsendLeft;
	@FXML private Button btnsendRight;
	@FXML private Button btnsendUp;
	@FXML private Button btnsendDown;
	@FXML private AnchorPane rootPane;
	// For Tie Breaker Selection
	private ObservableList<String> possibleBreakers = FXCollections.observableArrayList();
	private final ObservableList<String> selectedBreaker = FXCollections.observableArrayList();
	// for other classes
	private Btn btn = new Btn();
	// shared variables
	private String TournamentName;
	private int noOfCompetitors;
	private int onOfRounds;
	private Boolean goalScored;
	// for group
	private double winpoint;
	private double drawpoint;
	private double losspoint;
	// for Knock out
	private boolean sigleOrDouble;
	private boolean groupOrMultistage = true; 
	private int tournamenttype;
	
	
	public void loadcomponents() {
		
		/*String[] goalsAreScored = { "Goals Scored", "Goals Conceded", "Goals Difference", "Away Goals Scored",
				"Away Goals Conceeded", "Away Goals Difference", "Home Goals Scored", "Home Goals Conceeded",
				"Home Goals Difference", "Away Goal Against An Opponent" };
		String[] goalsAreNotScored = { "Home Wins", "Home Draws", "Home Loss", "Away Wins", "Away Draws", "Away Loss",
				"Total Wins", "Total Draws", "Total Loss", "Head To Head", };*/

		if (goalScored) {
			Breaker[] breakers  =  Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.GOAL_DEPENDENT );
			String[] goalsAreScored = Breaker.convertToString( breakers );
			
			for (int i = 0; i < goalsAreScored.length; i++) {
				possibleBreakers.add(goalsAreScored[i]);
			}

			listPossibleBreaker.setItems(possibleBreakers);
		} else {
			Breaker[] breakers  =  Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.NOT_GOAL_DEPENDENT );
			String[] goalsAreNotScored = Breaker.convertToString( breakers );
			
			for (int i = 0; i < goalsAreNotScored.length; i++) {
				possibleBreakers.add(goalsAreNotScored[i]);
			}
			listPossibleBreaker.setItems(possibleBreakers);
		}

		listSelectedBreaker.setItems(selectedBreaker);
	}// end method init

	@FXML
	public void sendRight(ActionEvent event) {

		String potential = listPossibleBreaker.getSelectionModel().getSelectedItem();
		if (potential != null) {
			listPossibleBreaker.getSelectionModel().clearSelection();
			possibleBreakers.remove(potential);
			selectedBreaker.add(0, potential);
			//selectedBreaker.add(potential);
		}
	}

	// deselect selectedBreaker
	@FXML
	public void sendLeft(ActionEvent event) {
		String notHero = listSelectedBreaker.getSelectionModel().getSelectedItem();
		if (notHero != null) {
			listSelectedBreaker.getSelectionModel().clearSelection();
			selectedBreaker.remove(notHero);
			possibleBreakers.add(notHero);
		}
	};

	@FXML
	public void sendUp(ActionEvent event) {
		String selected = listSelectedBreaker.getSelectionModel().getSelectedItem();
		if (selected != null && selectedBreaker.indexOf(selected) > 0) {
			int selectedIndex = selectedBreaker.indexOf(selected);
			int aboveselectedIndex = selectedIndex;
			aboveselectedIndex--;
			String aboveselected = selectedBreaker.get(aboveselectedIndex);
			selectedBreaker.set(aboveselectedIndex, selected);
			selectedBreaker.set(selectedIndex, aboveselected);
			listSelectedBreaker.getSelectionModel().select(aboveselectedIndex);
		}
	}

	@FXML
	public void senddown(ActionEvent event) {
		String selected = listSelectedBreaker.getSelectionModel().getSelectedItem();
		if (selected != null && selectedBreaker.indexOf(selected) != selectedBreaker.size() - 1) {
			int selectedIndex = selectedBreaker.indexOf(selected);
			int belowselectedIndex = selectedIndex;
			belowselectedIndex++;
			String aboveselected = selectedBreaker.get(belowselectedIndex);

			selectedBreaker.set(belowselectedIndex, selected);
			selectedBreaker.set(selectedIndex, aboveselected);
			listSelectedBreaker.getSelectionModel().select(belowselectedIndex);
		}
	}

	public void setGroupTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		groupOrMultistage = true;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		loadcomponents();
	}

	public void setMultiStageTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType, boolean KOSinDob) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		groupOrMultistage = false;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		sigleOrDouble = KOSinDob;
		loadcomponents();
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		if (selectedBreaker.isEmpty()) {
			AlertBox.display("NO Breaker Selected", "You Must Select at least one type of Breaker in order to proceed");
		} else {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());
		String choosenbreakers[] = new String[selectedBreaker.size()];
		for (int i = 0; i < selectedBreaker.size(); i++) {
			choosenbreakers[i] = selectedBreaker.get(i);
		}
		
		Breaker[] tieBreaker = Breaker.convertToBreaker(choosenbreakers);
		if (groupOrMultistage) {
			InputCompetitorController ic1 = (InputCompetitorController) loader.getController();
			ic1.setGroupTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint, drawpoint,
					losspoint, tournamenttype,tieBreaker);
			btn.next(rootPane, root, "InputCompetitorScreen.fxml");
		}else {
			
			InputCompetitorController ic11 = (InputCompetitorController) loader.getController();
			ic11.setMultiStageTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint, drawpoint,
					losspoint, tournamenttype, sigleOrDouble,tieBreaker);

			btn.next(rootPane, root, "InputCompetitorScreen.fxml");
		
		}
	}//end if selected is empty
		}
	@FXML
	public void cancel(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath + "TournamentTypeSreen.fxml").openStream());
		btn.next(rootPane, root, "TournamentTypeSreen.fxml");
	}

}// end class
