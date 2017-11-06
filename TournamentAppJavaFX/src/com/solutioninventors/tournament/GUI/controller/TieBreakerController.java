package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.utils.Breaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TieBreakerController {
	// from FXML
	@FXML
	private Button btnPrevious;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnCancel;
	@FXML
	private ListView<String> listPossibleBreaker;
	@FXML
	private ListView<String> listSelectedBreaker;
	@FXML
	private Button btnsendLeft;
	@FXML
	private Button btnsendRight;
	@FXML
	private Button btnsendUp;
	@FXML
	private Button btnsendDown;
	@FXML private Label lbltourtype;
	@FXML private Text txtdisplay;
	@FXML private Label lbltourapp;
	@FXML
	private AnchorPane rootPane;
	// For Tie Breaker Selection
	private ObservableList<String> possibleBreakers = FXCollections.observableArrayList();
	private final ObservableList<String> selectedBreaker = FXCollections.observableArrayList();
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
	private boolean groupOrMultistage = true;//track who called used for the previous button
	private int tournamenttype;
	//others
	private Btn btn = new Btn();
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	private boolean shuffleCompetitors;
	private boolean isSingleKOhomeAndAway;

	public void initialize() {
		font = cm.loadfonts();

		lbltourtype.setFont(font[1]);// Knockout Specs
		txtdisplay.setFont(font[0]);// the display
		lbltourapp.setFont(font[0]);// TOURNAMNET APP
	}

	public void loadcomponents() {

		/*
		 * String[] goalsAreScored = { "Goals Scored", "Goals Conceded",
		 * "Goals Difference", "Away Goals Scored", "Away Goals Conceeded",
		 * "Away Goals Difference", "Home Goals Scored", "Home Goals Conceeded",
		 * "Home Goals Difference", "Away Goal Against An Opponent" }; String[]
		 * goalsAreNotScored = { "Home Wins", "Home Draws", "Home Loss", "Away Wins",
		 * "Away Draws", "Away Loss", "Total Wins", "Total Draws", "Total Loss",
		 * "Head To Head", };
		 */

		if (goalScored) {
			Breaker[] breakers = Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.ALL);
			String[] goalsAreScored = Breaker.convertToString(breakers);

			for (int i = 0; i < goalsAreScored.length; i++) {
				possibleBreakers.add(toTitleCase(goalsAreScored[i].toLowerCase()));
			}

			listPossibleBreaker.setItems(possibleBreakers);
		} else {
			Breaker[] breakers = Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.NOT_GOAL_DEPENDENT);
			String[] goalsAreNotScored = Breaker.convertToString(breakers);

			for (int i = 0; i < goalsAreNotScored.length; i++) {
				possibleBreakers.add(toTitleCase(goalsAreNotScored[i].toLowerCase()));
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
			// selectedBreaker.add(potential);
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
			double lossp, int tourType, boolean KOSinDob, boolean shuffleComps, boolean isSingleKOhomeandAway) {
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
		isSingleKOhomeAndAway = isSingleKOhomeandAway;
		shuffleCompetitors = shuffleComps;
		loadcomponents();
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		if (selectedBreaker.isEmpty()) {
			cm.ErrorMessage("NO Breaker Selected", "You Must Select at least one type of Breaker in order to proceed");
		} else {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());
			String choosenbreakers[] = new String[selectedBreaker.size()];
			for (int i = 0; i < selectedBreaker.size(); i++) {
				choosenbreakers[i] = selectedBreaker.get(i);
				choosenbreakers[i].toUpperCase();
				choosenbreakers[i] = choosenbreakers[i].toUpperCase();
			}

			Breaker[] tieBreaker = Breaker.convertToBreaker(choosenbreakers);
			if (groupOrMultistage) {
				InputCompetitorController ic1 = (InputCompetitorController) loader.getController();
				ic1.setGroupTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint, drawpoint,
						losspoint, tournamenttype, tieBreaker);
				btn.next(rootPane, root, "InputCompetitorScreen.fxml", "commonStyle.css");
			} else {

				InputCompetitorController ic11 = (InputCompetitorController) loader.getController();
				ic11.setMultiStageTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint,
						drawpoint, losspoint, tournamenttype, sigleOrDouble, isSingleKOhomeAndAway, tieBreaker, shuffleCompetitors);

				btn.next(rootPane, root, "InputCompetitorScreen.fxml", "commonStyle.css");

			}
		} // end if selected is empty
	}

	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	
	@FXML
	public void cancel(ActionEvent event) throws IOException {
		btn.cancel(rootPane);
	}
	
	@FXML
	public void previous(ActionEvent event) throws IOException {
		if (groupOrMultistage) {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(Paths.viewpath + "Groupstage.fxml").openStream());
			GroupStageScreenController ic1 = (GroupStageScreenController) loader.getController();
			ic1.setTournamentName(TournamentName, goalScored);
			ic1.uncheckStandardBreaker();
			btn.previousfromtie(rootPane, event,root, "Groupstage.fxml", "commonStyle.css", "Tournament App");
		} else {
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(Paths.viewpath + "MultiStage.fxml").openStream());
			MultiStageScreenController ic1 = (MultiStageScreenController) loader.getController();
			ic1.setTournamentName(TournamentName, goalScored);
			ic1.uncheckStandardBreaker();
			btn.previousfromtie(rootPane, event,root, "MultiStage.fxml", "commonStyle.css", "Tournament App");
	
				}
	}

}// end class
