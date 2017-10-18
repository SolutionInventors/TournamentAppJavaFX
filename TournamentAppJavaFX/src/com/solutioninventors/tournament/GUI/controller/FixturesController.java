package com.solutioninventors.tournament.GUI.controller;

import java.net.MalformedURLException;
import java.util.Arrays;

import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class FixturesController {
	@FXML private ScrollPane scrollPane;
	@FXML private Label tourStage;
	private Label compName[];
	private Label VS[];
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

		tourStage.setFont(font[1]);// tournament Specs
		//tourStage.size
	}

	public void setTournament(Tournament value) throws TournamentEndedException {
		tournament = value;
		if (!tournament.hasEnded()) {
			// GridPane settings
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(25,0,25,10));
			grid.setHgap(5);
			grid.setVgap(5);
			//grid.setGridLinesVisible(true);
			// ColumnSettings for all five columns
			ColumnConstraints column1 = new ColumnConstraints(110); //comp name
			ColumnConstraints column2 = new ColumnConstraints(155); //VS
			ColumnConstraints column3 = new ColumnConstraints(85);//comp name 2	
			ColumnConstraints column4 = new ColumnConstraints(100);//image
			ColumnConstraints column5 = new ColumnConstraints(100);

			grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

			tourStage.setText(tournament.toString().toUpperCase());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new Label[currentFixtures.length * 2];
			VS = new Label[currentFixtures.length];
			logo = new ImageView[currentFixtures.length * 2];
			int i = 0;
			int[] jIndex = new int[1];
			int[] iIndex = new int[1];
			
			Arrays.stream(currentFixtures).forEach( current -> {
				compName[iIndex[0]] = new Label(current.getCompetitorOne().toString());
				compName[iIndex[0] + 1] = new Label(current.getCompetitorTwo().toString());
				compName[iIndex[0]].setFont(font[1]);
				compName[iIndex[0] + 1].setFont(font[1]);
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
					e.printStackTrace();
				}

				iIndex[0] += 2;// increment i by 2
				jIndex[0]++;
			});
			
//			for (int j = 0; j < currentFixtures.length; j++) {
//				compName[i] = new Label(currentFixtures[j].getCompetitorOne().toString());
//				compName[i + 1] = new Label(currentFixtures[j].getCompetitorTwo().toString());
//				compName[i].setFont(font[1]);
//				compName[i + 1].setFont(font[1]);
//				compName[i].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
//				compName[i+ 1].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
//				
//				VS[j] = new Label("VS");
//				VS[j].setFont(font[1]);
//				VS[j].setStyle("-fx-font-size: 19px; -fx-font-weight:bold; -fx-text-fill: red;");
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
//					e.printStackTrace();
//				}
//
//				i += 2;// increment i by 2
//
//			} // end for loop

			int[] c = new int[1] ;
			Arrays.stream(currentFixtures ).forEach( current -> {
				grid.add(logo[c[0]], 0, c[0]/2);
				grid.add(compName[c[0]], 1, c[0]/2);
				grid.add(VS[c[0]/2], 2, c[0]/2);
				grid.add(compName[c[0] + 1], 3, c[0]/2);
				grid.add(logo[c[0] + 1], 4, c[0]/2);
				c[0] += 2;
			});
//			for (int temp = 0; temp < currentFixtures.length; temp++) {
//				grid.add(logo[c], 0, temp);
//				grid.add(compName[c], 1, temp);
//				grid.add(VS[temp], 2, temp);
//				grid.add(compName[c + 1], 3, temp);
//				grid.add(logo[c + 1], 4, temp);
//				c += 2;
//			}
			scrollPane.setContent(grid);
		} // end if tournament has not ended

		else {
			cm.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current

}
