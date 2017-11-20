package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ViewResultsController {
	@FXML private ScrollPane scrollPane;
	@FXML private Label tourStage;
	@FXML private Label msgboxlbl;
	@FXML private Rectangle msgboxrect;
	@FXML private Button btnMoveNext;
	@FXML private CheckBox chkAllResults;
	private Round[] playedRounds;
	private ArrayList<Label> compName;
	private ArrayList<Label> VS;
	private ArrayList<ImageView> logo;
	private ArrayList<Label> scores;
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;
	private int fixturesLength[];
	private Font font[] = new Font[3];
	private int tempint;
	private boolean isFixturesComplete;
	private Label roundHeader[];

	public void initialize() {
		font = CommonMethods.loadfonts();

		msgboxlbl.setFont(font[1]);// tournament Specs
		tourStage.setFont(font[1]);

	}

	public void setTournament(Tournament value) throws TournamentEndedException {
		msgboxrect.setVisible(false);
		msgboxlbl.setVisible(false);
		btnMoveNext.setVisible(true);
		//chkAllResults.setVisible(true);

		tournament = value;
		chkAllResults.selectedProperty().set(false);
		if (!tournament.hasEnded()) {
			//chkAllResults.setVisible(true);
			tourStage.setText(tournament.toString().toUpperCase());
			currentFixtures = tournament.getCurrentRound().getFixtures();

			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			scores = new ArrayList<>();

			setupGrid(currentFixtures, 0);
			updateGridPane(null,false);

		} // end if tournament has not ended
		else {
			btnMoveNext.setVisible(false);
			//chkAllResults.setVisible(false);
			CommonMethods.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current

	private void setupGrid(Fixture[] currentFixtures, int i) {

		for (int j = 0; j < currentFixtures.length; j++) {
			compName.add(i, new Label(currentFixtures[j].getCompetitorOne().toString()));
			compName.add(i + 1, new Label(currentFixtures[j].getCompetitorTwo().toString()));
			compName.get(i).setFont(font[1]);
			compName.get(i).setPrefSize(162, 76);

			compName.get(i + 1).setFont(font[1]);
			compName.get(i + 1).setMaxSize(162, 76);
			compName.get(i).setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
			compName.get(i + 1).setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");

			VS.add(j, new Label("VS"));
			VS.get(j).setFont(font[1]);
			VS.get(j).setStyle("-fx-font-size: 19px; -fx-font-weight:bold; -fx-text-fill: red;");

			try {
				comp1 = currentFixtures[j].getCompetitorOne();
				comp2 = currentFixtures[j].getCompetitorTwo();

				String localUrl = comp1.getImage().toURI().toURL().toString();
				String local2 = comp2.getImage().toURI().toURL().toString();
				Image localImage1 = new Image(localUrl, false);
				Image localImage2 = new Image(local2, false);
				logo.add(i, new ImageView(localImage1));
				// logo[i].setImage(localImage1);
				logo.get(i).setFitWidth(108);
				logo.get(i).setFitHeight(65);
				logo.get(i).setPreserveRatio(true);
				logo.add(i + 1, new ImageView(localImage2));
				logo.get(i + 1).setFitWidth(108);
				logo.get(i + 1).setFitHeight(65);
				logo.get(i + 1).setPreserveRatio(true);

			} catch (MalformedURLException e) {
				// e.printStackTrace();
				System.out.println("Malformed URL");
			}

			// display results

			try {
				if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
					scores.add(i, new Label(String.valueOf((int)currentFixtures[j].getCompetitorOneScore())));
					scores.add(i + 1, new Label(String.valueOf((int)currentFixtures[j].getCompetitorTwoScore())));
					isFixturesComplete = true;
				} else {
					scores.add(i, new Label(getScoreWDL(String.valueOf(currentFixtures[j].getCompetitorOneScore()))));
					scores.add(i + 1,
							new Label(getScoreWDL(String.valueOf(currentFixtures[j].getCompetitorTwoScore()))));
					isFixturesComplete = true;
				}

			} catch (IncompleteFixtureException e) {
				isFixturesComplete = false;
				msgboxrect.setVisible(true);
				msgboxlbl.setVisible(true);
				//chkAllResults.setVisible(false);
				btnMoveNext.setVisible(false);
			}
			i += 2;// increment i by 2

		} // end for loop
		tempint = i;
	}

	@FXML
	public void updateResults(ActionEvent actionperformed) {
		tempint = 0;
		if (chkAllResults.isSelected()) {
			tourStage.setText("ALL RESULTS");
			playedRounds = tournament.getResults();
			compName = null;
			VS = null;
			logo = null;
			scores = null;

			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			scores = new ArrayList<>();
			fixturesLength = new int[playedRounds.length];
			for (int j = 0; j < playedRounds.length; j++) {
				currentFixtures = playedRounds[j].getFixtures();
				fixturesLength [j]= currentFixtures.length;
				setupGrid(currentFixtures, tempint);
			}
			updateGridPane(fixturesLength, true);

		} else {
			tourStage.setText(tournament.toString().toUpperCase());
			compName = null;
			VS = null;
			logo = null;
			scores = null;

			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			scores = new ArrayList<>();
			try {
				currentFixtures = tournament.getCurrentRound().getFixtures();
			} catch (TournamentEndedException e) {
				e.printStackTrace();
			}
			setupGrid(currentFixtures, 0);
			updateGridPane(null,false);
		}
	}

	private void updateGridPane(int[] length, boolean allResult) {
		// GridPane settings
		GridPane grid = new GridPane();
		// grid.setHgrow(grid, arg1);
		grid.setPadding(new Insets(25, 0, 25, 10));
		grid.setHgap(5);
		grid.setVgap(5);
		//grid.setGridLinesVisible(true);
		// ColumnSettings for all - columns
		ColumnConstraints column1 = new ColumnConstraints(110); // comp name
		ColumnConstraints column2 = new ColumnConstraints(100); // score input
		ColumnConstraints column3 = new ColumnConstraints(55);// vs
		ColumnConstraints column4 = new ColumnConstraints(30);// score input
		ColumnConstraints column5 = new ColumnConstraints(55);// comp name
		ColumnConstraints column6 = new ColumnConstraints(100);// im
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHgrow(Priority.NEVER);
		RowConstraints rc = new RowConstraints();
		rc.setVgrow(Priority.NEVER);

		grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, cc);
		grid.getRowConstraints().add(rc);
		if (isFixturesComplete) { // to confirm that a result has been entered
			int counter1 = 0;// increments by 2 to take care of logo1 logo2
			int counter2 = 0;// to be used for the VS
			int row = 0;
			if (allResult) {
				roundHeader = new Label[playedRounds.length];
				for (int i = 0; i < playedRounds.length; i++) {
					roundHeader[i] = new Label(playedRounds[i].toString().toUpperCase());
					
					roundHeader[i].setStyle("-fx-font-size: 22px; -fx-font-weight: BOLD; -fx-background-color:#ddd7d7; -fx-border-color:  white; -fx-border-width: 1px;");
					roundHeader[i].setPrefSize(400, 36);
					roundHeader[i].setAlignment(Pos.CENTER);
					grid.add(roundHeader[i], 1, row);
					GridPane.setColumnSpan(roundHeader[i], 5);
					//grid.add`
					row++;
					int arrayBounds = length[i];
					for (int temp = 0; temp < arrayBounds; temp++) {
						grid.add(logo.get(counter1), 0, row);
						grid.add(compName.get(counter1), 1, row);
						grid.add(scores.get(counter1), 2, row);
						grid.add(VS.get(counter2), 3, row);
						grid.add(scores.get(counter1 + 1), 4, row);
						grid.add(compName.get(counter1 + 1), 5, row);
						grid.add(logo.get(counter1 + 1), 6, row);
						counter1 += 2;
						row++;
						counter2++;
					}
				}
			} else {
				for (int temp = 0; temp < currentFixtures.length; temp++) {
					grid.add(logo.get(counter1), 0, temp);
					grid.add(compName.get(counter1), 1, temp);
					grid.add(scores.get(counter1), 2, temp);
					grid.add(VS.get(temp), 3, temp);
					grid.add(scores.get(counter1+1), 4, temp);
					grid.add(compName.get(counter1 + 1), 5, temp);
					grid.add(logo.get(counter1 + 1), 6, temp);
					counter1 += 2;
				}
			}

			scrollPane.setContent(grid);
		} // end not null

	}// end grid setup

	private String getScoreWDL(String num) {
		double scr = Double.valueOf(num);
		int score = (int) scr;
		String WDL;
		switch (score) {
		case 1:
			WDL = "W";
			break;
		case -1:
			WDL = "L";
			break;
		default:
			WDL = "D";
			break;
		}

		return WDL;
	}

	@FXML
	public void nextRound(ActionEvent event) throws IOException {
		try {
			tournament.moveToNextRound();
			btnMoveNext.setVisible(false);
		} catch (TournamentEndedException | MoveToNextRoundException e) {
			e.printStackTrace();
		}
		if (tournament.hasEnded())
			CommonMethods.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());

	}// end nextRound

}// end class
