package com.solutioninventors.tournament.GUI;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ViewResultsController {
	@FXML
	private List<Label> lblcompArray;
	@FXML
	private List<ImageView> imgArray;
	@FXML
	private List<Label> lblVsArray;
	@FXML
	private List<Label> lblGroupArray;
	@FXML
	private List<Label> lblresults;
	@FXML
	private Button btnnextgroup;
	@FXML
	private Button btnpregroup;
	private Competitor[] competitors;
	private Tournament tournament;
	String[] abc = new String[4];

	public void initialize() {
		//hide all the components
		for (Label lblcomp : lblcompArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblVsArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblGroupArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblresults)
			lblcomp.setVisible(false);

	}// end method initialize

	public void setTournament(Tournament value) {

		tournament = value;
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures();
		competitors = tournament.getCompetitors();
		int i = 0;
		for (int j = 0; j < competitors.length / 2; j++) {

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

			
		//display results
			lblresults.get(i).setVisible(true);
			lblresults.get(i).setText(String.valueOf(currentFixtures[j].getCompetitorOneScore()));
			lblresults.get(i+1).setVisible(true);
			lblresults.get(i+1).setText(String.valueOf(currentFixtures[j].getCompetitorTwoScore()));
			i += 2;// increment i by 2
		} // end for loop

	}// end set current
	@FXML
	public void nextRound(ActionEvent event) throws IOException {
		try {
			tournament.moveToNextRound();
		} catch (TournamentEndedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MoveToNextRoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("Fixtures.fxml").openStream());
		FixturesController fc = (FixturesController) loader.getController();
		fc.setTournament(tournament);
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament name");
	}// end nextRound
}// end class
