/**
 * @author Chinedu Oguejiofor
 *10 Aug. 2017
 * 3:23:29 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Challenge;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InputCompetitorController {
	@FXML private Text close;
	@FXML private AnchorPane  rootPane;
	 private Btn btn = new Btn();
	
	// shared variables
	private String TournamentName;
	private int noOfCompetitors;
	private int onOfRounds;
	// for group
	double winpoint;
	double drawpoint;
	double losspoint;
	//for Knock out
	private boolean sigleOrDouble;
	private boolean homeandAway;

	private enum TournamentTypes {
		KNOCKOUT, CHALLENGE, GROUP, MULTISTAGE
	};

	private TournamentTypes TournamentType;
	private Tournament tournament;
	@FXML
	private List<Label> SNArray;
	@FXML
	private List<TextField> txtArray;
	@FXML
	private List<ImageView> imgArray;

	private File[] file;
	// private TextField[] txtfield;

	Competitor[] comps;
	private int tournamenttype;

	@FXML public void chinedu() {

	}

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}

	public void setKOtournament(String tn, int noofcomp, boolean sigleTour, boolean homeAndAway) {
		TournamentName = tn;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.KNOCKOUT;
		homeandAway = homeAndAway;
		sigleOrDouble = sigleTour;
		System.out.println("KnockOut tournament called");
		loadcomponents();
	}

	public void setChallengeTournament(String tn, int rud) {
		TournamentName = tn;
		onOfRounds = rud;
		noOfCompetitors = 2;
		TournamentType = TournamentTypes.CHALLENGE;
		loadcomponents();
	}

	public void setGroupTournament(String tn, int rud, int noofcomp, double winp, double drawp, double lossp, int tourType) {
		TournamentName = tn;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.GROUP;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		loadcomponents();
	}

	public void setMultiStageTournament(String tn, int rud, int noofcomp, double winp, double drawp, double lossp, int tourType, boolean KOSinDob) {
		TournamentName = tn;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.MULTISTAGE;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		sigleOrDouble = KOSinDob;
		loadcomponents();
	}
			
		
	
	public void loadcomponents() {
		System.out.println("caqlled no of comp");
		file = new File[noOfCompetitors];
		/*Image image = new Image("file:nologo.jpg");
		for (int i = 0; i < noOfCompetitors; i++) {
			imgArray.get(i).setImage(image);
			file[i] = new File("nologo.jpg");
		}*/
System.out.println(noOfCompetitors);
System.out.println("no fo comp");
	}

	//JUST TO remove errors
	@FXML
	public void previous(ActionEvent event) throws IOException {
		btn.previous(rootPane,event, "WelcomeScreen.fxml", "lookfeel.css", "Tournament App");
	}
	
	@FXML
	public void cancel(ActionEvent event) {
		
	}
	
	
	@FXML
	public void close(MouseEvent event) {
		Platform.exit();
	}
	
	
	
	
	
	
	@FXML
	public void next(ActionEvent event) throws IOException, InvalidBreakerException, TournamentEndedException {
		Breaker[] breakers = { Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, Breaker.HEAD_TO_HEAD };
		TieBreaker tieBreakers = new TieBreaker(breakers);
		comps = new Competitor[noOfCompetitors];
		for (int i = 0; i < comps.length; i++) {
			comps[i] = new Competitor(txtArray.get(i).getText(), file[i]);
			//System.out.println(txtArray.get(i).getText());
		}
		try {
			switch (TournamentType) {
			case KNOCKOUT:
				if (sigleOrDouble) {
					tournament = new SingleEliminationTournament(comps, homeandAway);
				} else {
					tournament = new DoubleElimination(comps);
				}
				
				break;
			case CHALLENGE:
				tournament = new Challenge(comps, onOfRounds);
				break;
			case GROUP:
				
				
					switch (tournamenttype) {
						case 1:
							tournament = new SwissTournament(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint,
									tieBreakers, onOfRounds);
						break;
						case 2:
							tournament = new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 
									winpoint, drawpoint, losspoint, tieBreakers  ,  false  );
						break;
						case 3:
							tournament = new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 
									winpoint, drawpoint, losspoint, tieBreakers  ,  true  );
						break;
					}//end inner switch
					
				break;
			case MULTISTAGE:
				switch (tournamenttype) {
				case 1:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint, tieBreakers, onOfRounds,
							sigleOrDouble);
				break;
				case 2:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint, tieBreakers, false, sigleOrDouble);
				break;
				case 3:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint, tieBreakers, true, sigleOrDouble);
				break;
			}//end inner switch
				
				break;
			default:
				break;
			}
		} catch (TournamentException | InvalidBreakerException e) {
			e.printStackTrace();
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath+"FRSCIScreen.fxml").openStream());
		FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
		ic.setTournament(tournament);
		ic.init();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle(TournamentName);
	}

	public void changeImage1(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file1 = fc.showOpenDialog(null);
		file[0] = new File(file1.toURI());
		// for the image
		if (file1 != null) {
			String localUrl = file1.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			System.out.println(localUrl);
			imgArray.get(0).setImage(localImage);
		}
	}// end change image

	public void changeImage2(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file2 = fc.showOpenDialog(null);
		file[1] = new File(file2.toURI());
		System.out.println(file2);
		// for the image
		if (file2 != null) {
			String localUrl = file2.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(1).setImage(localImage);
		}
	}// end change image

	public void changeImage3(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file3 = fc.showOpenDialog(null);
		file[2] = new File(file3.toURI());
		// for the image
		if (file3 != null) {
			String localUrl = file3.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(2).setImage(localImage);
		}
	}// end change image

	public void changeImage4(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file4 = fc.showOpenDialog(null);
		file[3] = new File(file4.toURI());
		// for the image
		if (file4 != null) {
			String localUrl = file4.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);

			imgArray.get(3).setImage(localImage);
		}
	}// end change image

}// end class
