package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.types.Challenge;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
	@FXML
	private AnchorPane rootPane;
	// For Tie Breaker Selection
	private boolean goalscored = false;
	private ObservableList<String> possibleBreakers = FXCollections.observableArrayList();
	private final ObservableList<String> selectedBreaker = FXCollections.observableArrayList("Coin Toss");
	// for other classes
	private Btn btn = new Btn();
	private Image image = new Image("file:nologo.jpg");
	// shared variables
	private String TournamentName;
	private int noOfCompetitors;
	private int onOfRounds;
	private Boolean goalScored;
	private SportType goalsOrNoGoals;

	// for group
	private double winpoint;
	private double drawpoint;
	private double losspoint;
	// for Knock out
	private boolean sigleOrDouble;
	private boolean homeandAway;
	// for the images file count
	private int img1 = 0;
	private int img2 = 1;
	private int img3 = 2;
	private int img4 = 3;

	private enum TournamentTypes {
		KNOCKOUT, CHALLENGE, GROUP, MULTISTAGE
	};

	private TournamentTypes TournamentType;
	private Tournament tournament;
	private File[] file;
	private Competitor[] comps;
	private int tournamenttype;
	private int startValue = 0;
	private int endValue = 4;
	private int counter = 4;
	private int counter2;

	public void initialize() {
		String[] goalsAreScored = { "Goals Scored", "Goals Conceded", "Goals Difference", "Away Goals Scored",
				"Away Goals Conceeded", "Away Goals Difference", "Home Goals Scored", "Home Goals Conceeded",
				"Home Goals Difference", "Away Goal Against An Opponent" };
		String[] goalsAreNotScored = { "Home Wins", "Home Draws", "Home Loss", "Away Wins", "Away Draws", "Away Loss",
				"Total Wins", "Total Draws", "Total Loss", "Head To Head", };

		if (goalscored) {
			for (int i = 0; i < goalsAreScored.length; i++) {
				possibleBreakers.add(goalsAreScored[i]);
			}

			listPossibleBreaker.setItems(possibleBreakers);
		} else {
			for (int i = 0; i < goalsAreScored.length; i++) {
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
			selectedBreaker.add(potential);
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

	public void setKOtournament(String tn, Boolean goalScored, int noofcomp, boolean sigleTour, boolean homeAndAway) {
		TournamentName = tn;
		this.goalScored = goalScored;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.KNOCKOUT;
		homeandAway = homeAndAway;
		sigleOrDouble = sigleTour;
	}

	public void setChallengeTournament(String tn, Boolean goalScored, int rud) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = 2;
		TournamentType = TournamentTypes.CHALLENGE;
	}

	public void setGroupTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.GROUP;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
	}

	public void setMultiStageTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType, boolean KOSinDob) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.MULTISTAGE;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		sigleOrDouble = KOSinDob;
	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());

		switch (TournamentType) {
		case GROUP:
			InputCompetitorController ic1 = (InputCompetitorController) loader.getController();
			ic1.setGroupTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint, drawpoint,
					losspoint, tournamenttype);
			btn.next(rootPane, root, "InputCompetitorScreen.fxml");

			break;
		case MULTISTAGE:
			InputCompetitorController ic11 = (InputCompetitorController) loader.getController();
			ic11.setMultiStageTournament(TournamentName, goalScored, onOfRounds, noOfCompetitors, winpoint, drawpoint,
					losspoint, tournamenttype, sigleOrDouble);

			btn.next(rootPane, root, "InputCompetitorScreen.fxml");
			break;
		default:
			break;

		}
	}

}// end class
