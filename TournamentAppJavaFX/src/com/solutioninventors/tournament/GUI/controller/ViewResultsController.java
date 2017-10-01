package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.exceptions.TournamentHasNotBeenSavedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewResultsController {
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Label tourStage;
	private Label compName[];
	private Label VS[];
	private Label scores[];
	private ImageView logo[];
	// private Image img = new Image("file:nologo");
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;

	public void setTournament(Tournament value) throws TournamentEndedException, IncompleteFixtureException {
		tournament = value;
		if (!tournament.hasEnded()) {
			// GridPane settings
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(25));
			grid.setHgap(5);
			grid.setVgap(5);
			// ColumnSettings for all five columns
			ColumnConstraints column1 = new ColumnConstraints(110); //
			ColumnConstraints column2 = new ColumnConstraints(100); //
			ColumnConstraints column3 = new ColumnConstraints(50);
			ColumnConstraints column4 = new ColumnConstraints(50);
			ColumnConstraints column5 = new ColumnConstraints(50);
			ColumnConstraints column6 = new ColumnConstraints(50);
			ColumnConstraints column7 = new ColumnConstraints(100);

			grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7);

			tourStage.setText(tournament.toString());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new Label[currentFixtures.length * 2];
			VS = new Label[currentFixtures.length];
			logo = new ImageView[currentFixtures.length * 2];
			scores = new Label[currentFixtures.length * 2];
			int i = 0;
			for (int j = 0; j < currentFixtures.length; j++) {
				compName[i] = new Label(currentFixtures[j].getCompetitorOne().toString());
				compName[i + 1] = new Label(currentFixtures[j].getCompetitorTwo().toString());
				VS[j] = new Label("VS");
				try {
					comp1 = currentFixtures[j].getCompetitorOne();
					comp2 = currentFixtures[j].getCompetitorTwo();

					String localUrl = comp1.getImage().toURI().toURL().toString();
					String local2 = comp2.getImage().toURI().toURL().toString();
					Image localImage1 = new Image(localUrl, false);
					Image localImage2 = new Image(local2, false);
					logo[i] = new ImageView(localImage1);
					// logo[i].setImage(localImage1);
					logo[i].setFitWidth(108);
					logo[i].setFitHeight(65);
					logo[i].setPreserveRatio(true);
					logo[i + 1] = new ImageView(localImage2);
					logo[i + 1].setFitWidth(108);
					logo[i + 1].setFitHeight(65);
					logo[i + 1].setPreserveRatio(true);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				// display results
				scores[i] = new Label(String.valueOf(currentFixtures[j].getCompetitorOneScore()));
				scores[i + 1] = new Label(String.valueOf(currentFixtures[j].getCompetitorTwoScore()));
				i += 2;// increment i by 2

			} // end for loop

			int c = 0;
			for (int temp = 0; temp < currentFixtures.length; temp++) {
				grid.add(logo[c], 0, temp);
				grid.add(compName[c], 1, temp);
				grid.add(scores[c], 2, temp);
				grid.add(VS[temp], 3, temp);
				grid.add(scores[c + 1], 4, temp);
				grid.add(compName[c + 1], 5, temp);
				grid.add(logo[c + 1], 6, temp);
				c += 2;
			}
			scrollPane.setContent(grid);
		} // end if tournament has not ended

		else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current

	@FXML
	public void nextRound(ActionEvent event) throws IOException {
		try {
			tournament.moveToNextRound();
		} catch (TournamentEndedException | MoveToNextRoundException e) {
			e.printStackTrace();
		}
		if (tournament.hasEnded())
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());

	}// end nextRound
	
	public void savetour(ActionEvent event)  {
		try {
			tournament.save();
		} catch ( Exception e) {
			System.out.println("You haven't saved for the first time");
			saveastour();
		}
	}
	
	public void saveastour()  {
		FileChooser fileChooser = new FileChooser();
		Stage primaryStage = new Stage();
		File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		try {
			Tournament.saveAs(tournament, tournamentFile);
		} catch (IOException | TournamentException e) {
			e.printStackTrace();
		}
	}

}// end class
