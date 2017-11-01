package com.solutioninventors.tournament.GUI.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;

import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
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
	@FXML private CheckBox chkAllFixtures;
	private ArrayList<Label> compName;
	private ArrayList<Label> VS;
	private ArrayList<ImageView> logo;
	GridPane grid;
	// private Image img = new Image("file:nologo");
	private Round[] pendingRounds;
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;

	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	private int tempint;

	public void initialize() {
		font = cm.loadfonts();

		tourStage.setFont(font[1]);// tournament Specs
		chkAllFixtures.setSelected(false);
		//tourStage.size
	}

	public void setTournament(Tournament value) throws TournamentEndedException {
		tournament = value;
		if (!tournament.hasEnded()) {
			// GridPane settings
			
			tourStage.setText(tournament.toString().toUpperCase());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			setupGridPane(currentFixtures,0);
			updateGrid(currentFixtures.length,false);
			
		} // end if tournament has not ended

		else {
			cm.ErrorMessage("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current
	
	@FXML
	public void updateFixtures(ActionEvent actionperformed){
		try {
			System.out.println(tournament.toString());
			tempint = 0;
		if (chkAllFixtures.isSelected()) {
			pendingRounds = tournament.getPendingRounds();
			compName = null;
			VS = null;
			logo = null;
			
			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			
			for (int j = 0; j < pendingRounds.length; j++) {
				currentFixtures = pendingRounds[j].getFixtures();
				setupGridPane(currentFixtures,tempint);
			}
			updateGrid(currentFixtures.length*pendingRounds.length,true);
		} else {
			compName = null;
			VS = null;
			logo = null;

			compName = new ArrayList<>();
			VS = new ArrayList<>();
			logo = new ArrayList<>();
			currentFixtures = tournament.getCurrentRound().getFixtures();
			setupGridPane(currentFixtures,0);
			updateGrid(currentFixtures.length,false);
		}
		} catch (TournamentEndedException e) {
			e.printStackTrace();
		}
		
	}
	private void setupGridPane(Fixture[] currentFixtures, int i) throws TournamentEndedException {
	
		

		
		for (int j = 0; j < currentFixtures.length; j++) {
			//compName.get(i) = new Label(currentFixtures[j].getCompetitorOne().toString());
			compName.add(i, new Label(currentFixtures[j].getCompetitorOne().toString()));
			
			/*compName[i + 1] = new Label(currentFixtures[j].getCompetitorTwo().toString());
			compName[i].setFont(font[1]);
			compName[i + 1].setFont(font[1]);
			compName[i].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
			compName[i+ 1].setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");*/
			compName.add(i + 1, new Label(currentFixtures[j].getCompetitorTwo().toString()));
			compName.get(i).setFont(font[1]);
			compName.get(i+1).setFont(font[1]);
			compName.get(i).setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
			compName.get(i+1).setStyle("-fx-font-size: 12px; -fx-font-weight:bold;");
			
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
				logo.add(i,new ImageView(localImage1));
				logo.get(i).setFitWidth(108);
				logo.get(i).setFitHeight(65);
				logo.get(i).setPreserveRatio(true);
				logo.add(i + 1, new ImageView(localImage2));
				logo.get(i+1).setFitWidth(108);
				logo.get(i+1).setFitHeight(65);
				logo.get(i+1).setPreserveRatio(true);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			i += 2;// increment i by 2

		} // end for loop

	tempint = i;
	}
	private void updateGrid(int arraylength, boolean currentround) {
		grid = new GridPane();
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

	int counter1 = 0;
	int counter2 = 0;
	int row = 0;
	//boolean value = false;
	if (currentround) {
		for (int i = 0; i < pendingRounds.length; i++) {
			grid.add(new Label("Group"+ (i+1)), 2, row);
			row++;
			for (int temp = 0; temp < currentFixtures.length; temp++) {
				grid.add(logo.get(counter1), 0, row);
				grid.add(compName.get(counter1), 1, row);
				grid.add(VS.get(counter2), 2, row);
				grid.add(compName.get(counter1+1), 3, row);
				grid.add(logo.get(counter1+1), 4, row);
				counter1 += 2;
				row++;
				counter2++;
		}
		}
	} else {
		for (int temp = 0; temp < currentFixtures.length; temp++) {
			grid.add(logo.get(counter1), 0, row);
			grid.add(compName.get(counter1), 1, row);
			grid.add(VS.get(temp), 2, row);
			grid.add(compName.get(counter1+1), 3, row);
			grid.add(logo.get(counter1+1), 4, row);
			counter1 += 2;
			row++;
	}
	}
	
	//grid.add(new Label("Round "+ temp +1), 2, row);
	//row++;
	
	//
	scrollPane.setContent(grid);
}
}
