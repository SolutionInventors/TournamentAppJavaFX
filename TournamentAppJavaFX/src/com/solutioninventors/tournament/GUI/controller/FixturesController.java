package com.solutioninventors.tournament.GUI.controller;

import java.net.MalformedURLException;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FixturesController {
	@FXML
	private List<Label> lblcompArray;
	@FXML
	private List<ImageView> imgArray;
	@FXML
	private List<Label> lblVsArray;
	@FXML
	private List<Label> lblGroupArray;
	@FXML
	private Button btnnextgroup;
	@FXML
	private Button btnpregroup;
	private Competitor[] competitors;
	private Tournament tournament;
	String[] abc = new String[4];

	public void initialize() {
		for (Label lblcomp : lblcompArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblVsArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblGroupArray)
			lblcomp.setVisible(false);
		

	}// end method initialize

	public void setTournament(Tournament value) {
	
		tournament = value;
		if(!tournament.hasEnded()) {
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures();
		competitors = tournament.getCompetitors();
		int i = 0;
		for (int j = 0; j < currentFixtures.length; j++) {
			
			lblcompArray.get(i).setVisible(true);
			lblcompArray.get(i).setText(currentFixtures[j].getCompetitorOne().toString());
			lblcompArray.get(i+1).setVisible(true);
			lblcompArray.get(i+1).setText(currentFixtures[j].getCompetitorTwo().toString());
			lblVsArray.get(j).setVisible(true);
			lblVsArray.get(j).setText("VS");
			if (j==0) {
				lblGroupArray.get(j).setVisible(true);
				lblGroupArray.get(j).setText("GROUP 1");
			}
			
			try {
				String localUrl = competitors[i].getImage().toURI().toURL().toString();
				String local2 = competitors[i+1].getImage().toURI().toURL().toString();
				Image localImage1 = new Image(localUrl, false);
				Image localImage2 = new Image(local2, false);
				imgArray.get(i).setImage(localImage1);
				imgArray.get(i+1).setImage(localImage2);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			i+=2;//increment i by 2
		}//end for loop
	   }//end if tournament has not ended
	 else {
		AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
	}
	}// end set current
	
	/*@FXML
	public void inputresults(ActionEvent event) throws IOException {
		//((Node) event.getSource()).getScene().getWindow().hide();
	//	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputResults.fxml").openStream());
		InputResultsController vr = (InputResultsController) loader.getController();
		vr.setTournament(tournament);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament Name");
		Btn.next(root, event, tournament.getName());
	}*/

}// end class
