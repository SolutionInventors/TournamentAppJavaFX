package com.solutioninventors.tournament.GUI.controller;

import java.net.MalformedURLException;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TemplateController {
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Label tourStage;
	private Label compName[];
	private Label VS[];
	private ImageView logo[];
	private Image img = new Image("file:nologo");
	private Tournament tournament;
	private Competitor comp1;
	private Competitor comp2;
	private Fixture[] currentFixtures;

	public void setTournament(Tournament value) throws TournamentEndedException {
		tournament = value;
		if (!tournament.hasEnded()) {
			tourStage.setText(tournament.toString());
			currentFixtures = tournament.getCurrentRound().getFixtures();
			compName = new Label[currentFixtures.length*2];
			VS = new Label[currentFixtures.length];
			logo = new ImageView[currentFixtures.length*2];
			int i = 0;
			for (int j = 0; j < currentFixtures.length; j++) {

				
				compName[i].setText(currentFixtures[j].getCompetitorOne().toString());
				compName[i + 1].setText(currentFixtures[j].getCompetitorTwo().toString());
				VS[j].setText("VS");
				
				
				try {
					comp1= currentFixtures[j].getCompetitorOne();
					comp2= currentFixtures[j].getCompetitorTwo();
					
					String localUrl = comp1.getImage().toURI().toURL().toString();
					String local2 = comp2.getImage().toURI().toURL().toString();
					Image localImage1 = new Image(localUrl, false);
					Image localImage2 = new Image(local2, false);
					logo[i].setImage(localImage1);
					logo[i + 1].setImage(localImage2);
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}

				i += 2;// increment i by 2
			} // end for loop
		} // end if tournament has not ended
		
		else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
	}// end set current

}
