package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
	private Label compName[];
	private Label VS[];
	private Label scores[];
	private ImageView logo[];
	// private Image img = new Image("file:nologo");
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];

	
	public void initialize() {
		font = cm.loadfonts();
		
		msgboxlbl.setFont(font[1]);//tournament Specs
		tourStage.setFont(font[1]);
		
	}
	
	public void setTournament(Tournament value) throws TournamentEndedException {
		msgboxrect.setVisible(false);
		msgboxlbl.setVisible(false);
		btnMoveNext.setVisible(true);
		tournament = value;
		
		
		if (!tournament.hasEnded()) {
			// GridPane settings
			GridPane grid = new GridPane();
			//grid.setHgrow(grid, arg1);
			grid.setPadding(new Insets(25,0,25,10));
			grid.setHgap(5);
			grid.setVgap(5);
			// ColumnSettings for all - columns
			ColumnConstraints column1 = new ColumnConstraints(110); //comp name
			ColumnConstraints column2 = new ColumnConstraints(100); //score input
			ColumnConstraints column3 = new ColumnConstraints(55);//vs
			ColumnConstraints column4 = new ColumnConstraints(30);//score input
			ColumnConstraints column5 = new ColumnConstraints(55);//comp name	
			ColumnConstraints column6 = new ColumnConstraints(100);//im
			 ColumnConstraints cc = new ColumnConstraints();
			    cc.setHgrow(Priority.NEVER);
			    RowConstraints rc = new RowConstraints();
			    rc.setVgrow(Priority.NEVER);

			grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6,cc);
			grid.getRowConstraints().add(rc);
			
			tourStage.setText(tournament.toString().toUpperCase());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new Label[currentFixtures.length * 2];
			VS = new Label[currentFixtures.length];
			logo = new ImageView[currentFixtures.length * 2];
			scores = new Label[currentFixtures.length * 2];
			int[] iIndex = new int[1];
			int[] jIndex = new int[1];
			
			Arrays.stream( currentFixtures ).forEach( current -> {
				compName[ iIndex[0]] = new Label(current.getCompetitorOne().toString());
				compName[iIndex[0] + 1] = new Label(current.getCompetitorTwo().toString());
				compName[iIndex[0] ].setFont(font[1]);
				//compName[i].setMaxSize(162, 76);
				compName[iIndex[0]].setPrefSize(162, 76);
				//compName[i].setMaxWidth(162);
				
				compName[iIndex[0] + 1].setFont(font[1]);
				compName[iIndex[0] + 1].setMaxSize(162, 76);
				compName[iIndex[0]].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
				compName[iIndex[0]+ 1].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
				
				VS[jIndex[0]] = new Label("VS");
				VS[jIndex[0]].setFont(font[1]);
				VS[jIndex[0]].setStyle("-fx-font-size: 19px; -fx-font-weight:bold; -fx-text-fill: red;");
				
				
				try {
					comp1 = current.getCompetitorOne();
					comp2 = current.getCompetitorTwo();

					String localUrl = comp1.getImage().toURI().toURL().toString();
					String local2 = comp2.getImage().toURI().toURL().toString();
					Image localImage1 = new Image(localUrl, false);
					Image localImage2 = new Image(local2, false);
					logo[iIndex[0]] = new ImageView(localImage1);
					// logo[i].setImage(localImage1);
					logo[iIndex[0]].setFitWidth(108);
					logo[iIndex[0]].setFitHeight(65);
					logo[iIndex[0]].setPreserveRatio(true);
					logo[iIndex[0] + 1] = new ImageView(localImage2);
					logo[iIndex[0] + 1].setFitWidth(108);
					logo[iIndex[0] + 1].setFitHeight(65);
					logo[iIndex[0] + 1].setPreserveRatio(true);

				} catch (MalformedURLException e) {
					//e.printStackTrace();
					System.out.println("Malformed URL");
				}

				// display results
				
				try {
					if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
						scores[iIndex[0]] = new Label(String.valueOf(current.getCompetitorOneScore()));
						scores[iIndex[0] + 1] = new Label(String.valueOf(current.getCompetitorTwoScore()));
					}else {
						scores[iIndex[0]] = new Label(getScoreWDL(String.valueOf(current.getCompetitorOneScore())));
						scores[iIndex[0] + 1] = new Label(getScoreWDL(String.valueOf(current.getCompetitorTwoScore())));
					}
				
				
				
				} catch (IncompleteFixtureException e) {
					msgboxrect.setVisible(true);
					msgboxlbl.setVisible(true);
					btnMoveNext.setVisible(false);
					
				}
				iIndex[0] += 2;// increment i by 2
				jIndex[0]++;
			});
			
			
//			for (int j = 0; j < currentFixtures.length; j++) {
//				compName[i] = new Label(currentFixtures[j].getCompetitorOne().toString());
//				compName[i + 1] = new Label(currentFixtures[j].getCompetitorTwo().toString());
//				compName[i].setFont(font[1]);
//				//compName[i].setMaxSize(162, 76);
//				compName[i].setPrefSize(162, 76);
//				//compName[i].setMaxWidth(162);
//				
//				compName[i + 1].setFont(font[1]);
//				compName[i + 1].setMaxSize(162, 76);
//				compName[i].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
//				compName[i+ 1].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
//				
//				VS[j] = new Label("VS");
//				VS[j].setFont(font[1]);
//				VS[j].setStyle("-fx-font-size: 19px; -fx-font-weight:bold; -fx-text-fill: red;");
//				
//				
//				try {
//					comp1 = currentFixtures[j].getCompetitorOne();
//					comp2 = currentFixtures[j].getCompetitorTwo();
//
//					String localUrl = comp1.getImage().toURI().toURL().toString();
//					String local2 = comp2.getImage().toURI().toURL().toString();
//					Image localImage1 = new Image(localUrl, false);
//					Image localImage2 = new Image(local2, false);
//					logo[i] = new ImageView(localImage1);
//					// logo[i].setImage(localImage1);
//					logo[i].setFitWidth(108);
//					logo[i].setFitHeight(65);
//					logo[i].setPreserveRatio(true);
//					logo[i + 1] = new ImageView(localImage2);
//					logo[i + 1].setFitWidth(108);
//					logo[i + 1].setFitHeight(65);
//					logo[i + 1].setPreserveRatio(true);
//
//				} catch (MalformedURLException e) {
//					//e.printStackTrace();
//					System.out.println("Malformed URL");
//				}
//
//				// display results
//				
//				try {
//					if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
//						scores[i] = new Label(String.valueOf(currentFixtures[j].getCompetitorOneScore()));
//						scores[i + 1] = new Label(String.valueOf(currentFixtures[j].getCompetitorTwoScore()));
//					}else {
//						scores[i] = new Label(getScoreWDL(String.valueOf(currentFixtures[j].getCompetitorOneScore())));
//						scores[i + 1] = new Label(getScoreWDL(String.valueOf(currentFixtures[j].getCompetitorTwoScore())));
//					}
//				
//				
//				
//				} catch (IncompleteFixtureException e) {
//					msgboxrect.setVisible(true);
//					msgboxlbl.setVisible(true);
//					btnMoveNext.setVisible(false);
//					System.out.println("incomplete fixture error");
//				}
//				i += 2;// increment i by 2
//
//			} // end for loop
			if (scores[0] != null) {
				
			
			int[] c = new int[1];
			int[] temp = new int[1];
			Arrays.stream( currentFixtures ).forEach(current -> {
				grid.add(logo[c[0]], 0, temp[0]);
				grid.add(compName[c[0]], 1, temp[0]);
				grid.add(scores[c[0]], 2, temp[0]);
				grid.add(VS[temp[0]], 3, temp[0]);
				grid.add(scores[c[0] + 1], 4, temp[0]);
				grid.add(compName[c[0] + 1], 5, temp[0]);
				grid.add(logo[c[0] + 1], 6, temp[0]);
				c[0] += 2;
				temp[0]++;
			});
			
			scrollPane.setContent(grid);
			}//end not null
		/*	try {
				if (tournament.getCurrentRound().isComplete()) {
					btnMoveNext.setVisible(false);
				}else {
					btnMoveNext.setVisible(true);
				}
			} catch (TournamentEndedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} // end if tournament has not ended

		else {
			btnMoveNext.setVisible(false);
			cm.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current

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
			cm.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());

	}// end nextRound
	

}// end class
