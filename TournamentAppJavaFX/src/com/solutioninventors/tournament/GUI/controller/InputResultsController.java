package com.solutioninventors.tournament.GUI.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class InputResultsController {
	@FXML private ScrollPane scrollPane;
	@FXML private Label tourStage;
	@FXML private Button btnsubmit;
	private Label compName[];
	private Label VS[];
	private TextField scores[];
	private ImageView logo[];
	private ArrayList<ComboBox<String>> scoresnoGoal = new ArrayList<>();
	// private Image img = new Image("file:nologo");
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;
	private ObservableList<String> WDL = FXCollections.observableArrayList("W", "D", "L");

	public void setTournament(Tournament value) throws TournamentEndedException {
		tournament = value;
		if (!tournament.hasEnded() ) {
			if (!tournament.getCurrentRound().isComplete()) {
			// GridPane settings
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(25));
			grid.setHgap(5);
			grid.setVgap(5);
			// ColumnSettings for all five columns
			ColumnConstraints column1 = new ColumnConstraints(90); //
			ColumnConstraints column2 = new ColumnConstraints(100); //
			ColumnConstraints column3 = new ColumnConstraints(70);
			ColumnConstraints column4 = new ColumnConstraints(70);
			ColumnConstraints column5 = new ColumnConstraints(70);
			ColumnConstraints column6 = new ColumnConstraints(70);
			ColumnConstraints column7 = new ColumnConstraints(100);

			grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6, column7);

			tourStage.setText(tournament.toString());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new Label[currentFixtures.length * 2];
			VS = new Label[currentFixtures.length];
			logo = new ImageView[currentFixtures.length * 2];
			scores = new TextField[currentFixtures.length * 2];
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

				// display fixtures for the results to be inputed
				if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
					scores[i] = new TextField();
					scores[i].setMaxHeight(30);
					scores[i].setMaxWidth(30);
					scores[i + 1] = new TextField();
					scores[i+ 1].setMaxHeight(30);
					scores[i+ 1].setMaxWidth(30);
				}else {
					scoresnoGoal.add(i, new ComboBox<String>());
					scoresnoGoal.get(i).getItems().addAll(WDL);
					scoresnoGoal.get(i).maxHeight(Region.USE_COMPUTED_SIZE);
					scoresnoGoal.get(i).maxWidth(Region.USE_COMPUTED_SIZE);
					scoresnoGoal.add(i + 1, new ComboBox<String>());
					scoresnoGoal.get(i + 1).getItems().addAll(WDL);
					scoresnoGoal.get(i+ 1).maxHeight(Region.USE_COMPUTED_SIZE);
					scoresnoGoal.get(i+ 1).maxWidth(Region.USE_COMPUTED_SIZE);
				}
				
				i += 2;// increment i by 2

			} // end for loop

			int c = 0;
			if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
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
			}else {
				for (int temp = 0; temp < currentFixtures.length; temp++) {
					grid.add(logo[c], 0, temp);
					grid.add(compName[c], 1, temp);
					grid.add(scoresnoGoal.get(c), 2, temp);
					grid.add(VS[temp], 3, temp);
					grid.add(scoresnoGoal.get(c + 1), 4, temp);
					grid.add(compName[c + 1], 5, temp);
					grid.add(logo[c + 1], 6, temp);
					c += 2;
				}
			}
			btnsubmit.setVisible(true);
			scrollPane.setContent(grid);
			}else {
				btnsubmit.setVisible(false);
			}
		 
		}else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
			btnsubmit.setVisible(false);
		}
	}// end set current

	@FXML
	public void getResults(ActionEvent e) throws TournamentEndedException, ResultCannotBeSetException {
		int count = 0;
	/*	if (currentFixtures[]) {
			
		} else {

		}*/
		
		for (int i = 0; i < currentFixtures.length; i++) {
			Competitor com1 = currentFixtures[i].getCompetitorOne();
			Competitor com2 = currentFixtures[i].getCompetitorTwo();

			double score1 = Double.valueOf(scores[count].getText());
			double score2 = Double.valueOf(scores[count+1].getText());

			try {
				tournament.setResult(com1, score1, score2, com2);
			} catch (NoFixtureException ee) {
				ee.printStackTrace();
			}

			count += 2;
		}
	}
	
	
}// end class
