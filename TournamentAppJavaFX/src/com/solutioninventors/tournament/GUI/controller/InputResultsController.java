package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.GUI.utility.CustomTextField;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class InputResultsController {
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Label tourStage;
	@FXML
	private Button btnsubmit;
	private Label compName[];
	private Label VS[];
	private CustomTextField scores[];
	private ImageView logo[];
	private ArrayList<ComboBox<String>> scoresnoGoal = new ArrayList<>();
	// private Image img = new Image("file:nologo");
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;
	private ObservableList<String> WDL = FXCollections.observableArrayList("W", "D", "L");
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	private boolean goalsAreScore;

	public void initialize() {
		font = cm.loadfonts();

		tourStage.setFont(font[1]);// tournament Specs
	}

	public void setTournament(Tournament value) throws TournamentEndedException {
		tournament = value;
		if (!tournament.hasEnded()) {
			if (!tournament.getCurrentRound().isComplete()) {
				goalsAreScore = (tournament.getSportType() == SportType.GOALS_ARE_SCORED ? true : false);
				// GridPane settings
				GridPane grid = new GridPane();
				grid.setPadding(new Insets(25,0,25,10));
				grid.setHgap(5);
				grid.setVgap(5);
				// ColumnSettings for all five columns//statring points
				ColumnConstraints column1 = new ColumnConstraints(110); //comp name
				ColumnConstraints column2 = new ColumnConstraints(100); //score input
				column2.setHalignment(HPos.LEFT);
				ColumnConstraints column3 = new ColumnConstraints(35);//vs
				ColumnConstraints column4 = new ColumnConstraints(30);//score input
				ColumnConstraints column5 = new ColumnConstraints(35);//comp name	
				ColumnConstraints column6 = new ColumnConstraints(130);//image start
				column6.setHalignment(HPos.LEFT);
				//ColumnConstraints column7 = new ColumnConstraints(1);//end point for image

				grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);

				tourStage.setText(tournament.toString().toUpperCase());
				currentFixtures = tournament.getCurrentRound().getFixtures();
				compName = new Label[currentFixtures.length * 2];
				VS = new Label[currentFixtures.length];
				logo = new ImageView[currentFixtures.length * 2];
				scores = new CustomTextField[currentFixtures.length * 2];
				int i = 0;
				for (int j = 0; j < currentFixtures.length; j++) {
					compName[i] = new Label(currentFixtures[j].getCompetitorOne().toString());
					compName[i + 1] = new Label(currentFixtures[j].getCompetitorTwo().toString());
					compName[i].setFont(font[1]);
					compName[i].setWrapText(true);
					compName[i].setMaxSize(162, 76);
					compName[i].alignmentProperty().set(Pos.CENTER);
					//compName[i].setTextAlignment(new TextAlignment());
					compName[i + 1].setFont(font[1]);
					compName[i + 1].setWrapText(true);
					compName[i + 1].setMaxSize(162, 76);
					compName[i+ 1].alignmentProperty().set(Pos.CENTER);
					compName[i].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
					compName[i + 1].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");

					VS[j] = new Label("VS");
					VS[j].setFont(font[1]);
					VS[j].setStyle("-fx-font-size: 19px; -fx-font-weight:bold; -fx-text-fill: red;");

					try {
						comp1 = currentFixtures[j].getCompetitorOne();
						comp2 = currentFixtures[j].getCompetitorTwo();

						String localUrl = comp1.getImage().toURI().toURL().toString();
						String local2 = comp2.getImage().toURI().toURL().toString();
						Image localImage1 = new Image(localUrl, false);
						Image localImage2 = new Image(local2, false);
						logo[i] = new ImageView(localImage1);
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
						scores[i] = new CustomTextField();
						scores[i].setMaxHeight(30);
						scores[i].setNumericOnly(true);
						scores[i].setMaxWidth(30);
						scores[i + 1] = new CustomTextField();
						scores[i + 1].setMaxHeight(30);
						scores[i + 1].setMaxWidth(30);
						scores[i + 1].setNumericOnly(true);
					} else {
						scoresnoGoal.add(i, new ComboBox<String>());
						scoresnoGoal.get(i).getItems().addAll(WDL);
						scoresnoGoal.get(i).maxHeight(Region.USE_COMPUTED_SIZE);
						scoresnoGoal.get(i).maxWidth(Region.USE_COMPUTED_SIZE);
						scoresnoGoal.add(i + 1, new ComboBox<String>());
						scoresnoGoal.get(i + 1).getItems().addAll(WDL);
						scoresnoGoal.get(i + 1).maxHeight(Region.USE_COMPUTED_SIZE);
						scoresnoGoal.get(i + 1).maxWidth(Region.USE_COMPUTED_SIZE);
					}

					i += 2;// increment i by 2

				} // end for loop

				int c = 0;
				if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
					for (int temp = 0; temp < currentFixtures.length; temp++) {
						grid.add(logo[c], 0, temp);
						// GridPane.setHalignment(compName[c], HPos.RIGHT);
						grid.add(compName[c], 1, temp);
						compName[c].setAlignment(Pos.TOP_RIGHT);
						grid.add(scores[c], 2, temp);
						grid.add(VS[temp], 3, temp);
						grid.add(scores[c + 1], 4, temp);
					//	 GridPane.setHalignment(compName[c], HPos.LEFT);
						grid.add(compName[c + 1], 5, temp);
						grid.add(logo[c + 1], 6, temp);
						c += 2;
					}
				} else {
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
			} else {
				btnsubmit.setVisible(false);
			}

		} else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
			btnsubmit.setVisible(false);
		}
	}// end set current

	@FXML
	public void getResults(ActionEvent e) throws TournamentEndedException, ResultCannotBeSetException, IOException {
		boolean emptyBox = false, singleTieDraw = false, invalidnogoalScore = false;// DoubleElimDraw = false,
		double score1, score2;
		int count = 0;
		if (goalsAreScore) {
			for (int i = 0; i < scores.length; i++) {
				if (scores[i].getText().isEmpty()) {
					emptyBox = true;
					break;
				}
			}

		/*	if (emptyBox == false && tournament instanceof DoubleElimination)
				DoubleElimDraw = checkforDrawgoals();*/
			if (emptyBox == false && tournament instanceof SingleEliminationTournament)
				if (((SingleEliminationTournament) tournament).isTieRound())
					singleTieDraw = checkforDrawgoals();

		} else {
			for (int i = 0; i < scoresnoGoal.size(); i++) {
				try {
					if (scoresnoGoal.get(i).getValue().isEmpty()) {
						emptyBox = true;
						break;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					emptyBox = true;
					//e1.printStackTrace();
					System.out.println("from exception");
					break;
					
					
				}
			}

			/*if (emptyBox == false && tournament instanceof DoubleElimination)
				DoubleElimDraw = checkforDrawNoGoal();*/
			if (emptyBox == false && tournament instanceof SingleEliminationTournament)
				if (((SingleEliminationTournament) tournament).isTieRound())
					singleTieDraw = checkforDrawNoGoal();
			if (!emptyBox) {
			for (int i = 0; i < scoresnoGoal.size(); i+=2) {
				String s1 = (scoresnoGoal.get(i).getValue());
				String s2 = (scoresnoGoal.get(i + 1).getValue());

				if (s1.equals("W") && s2.equals("W") || s1.equals("L") && s2.equals("L")
						|| s1.equals("W") && s2.equals("D") || s1.equals("L") && s2.equals("D")) {
					invalidnogoalScore = true;
				}
			}
			}
		} // end if / else goals Scored

		if (emptyBox) {
			AlertBox.display("Empty Box", "Please check that all the boxes have been filled");
		} else if (singleTieDraw) {
			AlertBox.display("Tie Round", "You Cannot input draw in a tie Round");
		} /*else if (DoubleElimDraw) {
			AlertBox.display("No Draw", "Draw is not allowed in a Double Elimination");
		}*/ else if (invalidnogoalScore) {
			AlertBox.display("Invalid Result", "You cannot input a W D or L D etc");
		} else {
			for (int i = 0; i < currentFixtures.length; i++) {
				Competitor com1 = currentFixtures[i].getCompetitorOne();
				Competitor com2 = currentFixtures[i].getCompetitorTwo();

				if (goalsAreScore) {
					score1 = Double.valueOf(scores[count].getText());
					score2 = Double.valueOf(scores[count + 1].getText());
				} else {
					score1 = (scoresnoGoal.get(count).getValue().equals("W") ? 1 : 0);
					score2 = (scoresnoGoal.get(count + 1).getValue().equals("W") ? 1 : 0);
				}
				try {
					tournament.setResult(com1, score1, score2, com2);
					btnsubmit.setVisible(false);
				} catch (NoFixtureException ee) {
					ee.printStackTrace();
				}
				count += 2;
			}
		}
	}// end method get result

	/**
	 * boolean
	 * 
	 * @param count
	 * @param isDraw
	 * @return
	 */
	public boolean checkforDrawNoGoal() {
		boolean isDraw = false;
		for (int i = 0; i < scoresnoGoal.size(); i += 2) {
			double score1 = (scoresnoGoal.get(i).getValue().equals("D") ? 1 : 0);
			double score2 = (scoresnoGoal.get(i + 1).getValue().equals("D") ? 1 : 0);

			if (score1 == score2) {
				isDraw = true;
				break;
			}
		}
		return isDraw;
	}

	/**
	 * boolean
	 * 
	 * @param isDraw
	 * @return
	 */
	public boolean checkforDrawgoals() {
		boolean isDraw = false;
		for (int i = 0; i < scores.length; i += 2) {
			double score1 = Double.valueOf(scores[i].getText());
			double score2 = Double.valueOf(scores[i + 1].getText());

			if (score1 == score2) {
				isDraw = true;
				break;
			}
		}
		return isDraw;
	}

}// end class
