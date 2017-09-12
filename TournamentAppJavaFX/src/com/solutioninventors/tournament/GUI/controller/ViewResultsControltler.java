package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.exceptions.TournamentHasNotBeenSavedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.knockout.EliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewResultsControltler {
	@FXML
	private List<Label> lblcompArray;
	@FXML private Label stage;
	@FXML private List<ImageView> imgArray;
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
		// hide all the components
		for (Label lblcomp : lblcompArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblVsArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblGroupArray)
			lblcomp.setVisible(false);
		for (Label lblcomp : lblresults)
			lblcomp.setVisible(false);

	}// end method initialize

	public void setTournament(Tournament value) throws TournamentEndedException {

		tournament = value;
		if (!tournament.hasEnded()) {
			stage.setText(tournament.toString());
		
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures();
		if (tournament instanceof EliminationTournament) {
			competitors = ((EliminationTournament)tournament).getActiveCompetitors();
		} else {
			competitors = tournament.getCompetitors();
		}
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

			// display results
			lblresults.get(i).setVisible(true);
			lblresults.get(i).setText(String.valueOf(currentFixtures[j].getCompetitorOneScore()));
			lblresults.get(i + 1).setVisible(true);
			lblresults.get(i + 1).setText(String.valueOf(currentFixtures[j].getCompetitorTwoScore()));
			i += 2;// increment i by 2
		} // end for loop
		} else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}
		
		
	}// end set current

	@FXML
	public void nextRound(ActionEvent event) throws IOException {
		
			try {
				tournament.moveToNextRound();
			} catch (TournamentEndedException e) {
				//e.printStackTrace();
				//System.out.println("Error tournament ended");
			} catch (MoveToNextRoundException e) {
				//e.printStackTrace();
				//System.out.println("Error move to next round");
			}
			if (!tournament.hasEnded()) {
		/*	((Node) event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource(Paths.viewpath+"Fixtures.fxml").openStream());
			FixturesController fc = (FixturesController) loader.getController();
		fc.setTournament(tournament);
			
			//System.out.println(" passed tournament");
			Stage primaryStage = new Stage();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Tournament name");*/
				System.out.println(tournament.toString());
		} else {
			AlertBox.display("Tournament Finish", "This tournament is over the winner is " + tournament.getWinner());
		}

		
	}// end nextRound
	
	
	public void savetour(ActionEvent event)  {
		//FileChooser fileChooser = new FileChooser();
		//Stage primaryStage = new Stage();
		//File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		try {
			tournament.save();
		} catch (IOException | TournamentException | TournamentHasNotBeenSavedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void saveastour(ActionEvent event)  {
		FileChooser fileChooser = new FileChooser();
		Stage primaryStage = new Stage();
		File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		try {
			Tournament.saveAs(tournament, tournamentFile);
		} catch (IOException | TournamentException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void viewTable(ActionEvent event) throws IOException {
		//((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath+"Standing.fxml").openStream());
		StandingTable22Controller aa = (StandingTable22Controller) loader.getController();
		aa.setTournament(tournament);
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament name");
	}
	
	@FXML
	public void viewcompStat(ActionEvent event) throws IOException {
		//((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.GUIpath+"CompetitorTable/CompetitorStatus.fxml").openStream());
		CompetitorStatusController cc = (CompetitorStatusController) loader.getController();
		cc.setTournament(tournament);
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament name");
	}
	@FXML
	public void viewTab(ActionEvent event) throws IOException, TournamentEndedException {
		//((Node) event.getSource()).getScene().getWindow().hide();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath+"FRSCIScreen.fxml").openStream());
		//Parent root = loader.load(getClass().getResource(Paths.GUIpath+"tabscollect/Tabsaa.fxml").openStream());
		FRSCIScreenController ad = (FRSCIScreenController) loader.getController();
		ad.setTournament(tournament);
		ad.init();
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("Tournament name");
	}
	
}// end class


