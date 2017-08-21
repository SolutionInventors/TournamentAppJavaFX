package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class InputResultsController {
	@FXML
	private List<Label> lblcompArray;
	@FXML
	private List<ImageView> imgArray;
	@FXML
	private List<Label> lblVsArray;
	@FXML
	private List<Label> lblGroupArray;
	@FXML
	private List<TextField> txtresults;
	@FXML
	private Button btnnextgroup;
	@FXML
	private Button btnpregroup;
	private Competitor[] competitors;
	private Tournament tournament;
	ViewResultsController vr;
	Fixture[] currentFixtures;
	String[] abc = new String[4];

	public void initialize() {
		for (Label lblcomp : lblcompArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblVsArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblGroupArray)
			lblcomp.setVisible(false);
		for (TextField lblcomp : txtresults)
			lblcomp.setVisible(false);

	}// end method initialize

	public void setTournament(Tournament value) {

		tournament = value;
		if(!tournament.hasEnded()) {
		currentFixtures = tournament.getCurrentRound().getFixtures();
		competitors = tournament.getCompetitors();
		int i = 0;
		for (int j = 0; j < currentFixtures.length; j++) {

			lblcompArray.get(i).setVisible(true);
			lblcompArray.get(i).setText(currentFixtures[j].getCompetitorOne().toString());
			lblcompArray.get(i + 1).setVisible(true);
			lblcompArray.get(i + 1).setText(currentFixtures[j].getCompetitorTwo().toString());
			lblVsArray.get(j).setVisible(true);
			lblVsArray.get(j).setText("VS");
			if (j == 0) {
				lblGroupArray.get(j).setVisible(true);
				lblGroupArray.get(j).setText("GROUP 1");
			}

			try {
				String localUrl = competitors[i].getImage().toURI().toURL().toString();
				String local2 = competitors[i + 1].getImage().toURI().toURL().toString();
				Image localImage1 = new Image(localUrl, false);
				Image localImage2 = new Image(local2, false);
				imgArray.get(i).setImage(localImage1);
				imgArray.get(i + 1).setImage(localImage2);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			txtresults.get(i).setVisible(true);
			txtresults.get(i + 1).setVisible(true);

			i += 2;// increment i by 2
		} // end for loop
	} else {
		AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
	}
	}// end set current

	@FXML
	public void getResults(ActionEvent e) throws IOException, NoFixtureException {
		int count = 0;
		for (int i = 0; i < currentFixtures.length; i++) {
			Competitor com1 = currentFixtures[i].getCompetitorOne();
			Competitor com2 = currentFixtures[i].getCompetitorTwo();

			double score1 = Double.valueOf(txtresults.get(count).getText());
			double score2 = Double.valueOf(txtresults.get(count + 1).getText());

			try {
				tournament.setResult(com1, score1, score2, com2);
			} catch (NoFixtureException ee) {
				ee.printStackTrace();
			}

			count += 2;
		}
		System.out.println("Done");
		
		/*// open results window
		///((Node) e.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"ViewResults.fxml").openStream());
		//vr = (ViewResultsController) loader.getController();
	//	vr.setTournament(tournament);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament Name");*/
	}// end getResults
}// end class
