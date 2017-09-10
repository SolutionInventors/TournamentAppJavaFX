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
import javafx.scene.control.Button;
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
	@FXML private Button btnNext;
	@FXML private Button btnPrevious;
	@FXML private AnchorPane rootPane;
	@FXML
	private List<Label> SNArray;
	@FXML
	private List<TextField> txtArray;
	@FXML
	private List<ImageView> imgArray;

	private Btn btn = new Btn();
	Image image = new Image("file:nologo.jpg");
	// shared variables
	private String TournamentName;
	private int noOfCompetitors;
	private int onOfRounds;
	// for group
	private double winpoint;
	private double drawpoint;
	private double losspoint;
	// for Knock out
	private boolean sigleOrDouble;
	private boolean homeandAway;
	//for the images file count
	private int img1 = 0;
	private int img2 = 1;
	private int img3 = 2;
	private int img4 = 3;
	private enum TournamentTypes {
		KNOCKOUT, CHALLENGE, GROUP, MULTISTAGE
	};

	private TournamentTypes TournamentType;
	private Tournament tournament;
	private File[] file;
	private Competitor[] comps;
	private int tournamenttype;
	private int startValue = 0;
	private int endValue = 4;
	private int counter = 4;
	private int counter2;

	@FXML
	public void chinedu() {

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
		// System.out.println("KnockOut tournament called");
		loadcomponents();
	}

	public void setChallengeTournament(String tn, int rud) {
		TournamentName = tn;
		onOfRounds = rud;
		noOfCompetitors = 2;
		TournamentType = TournamentTypes.CHALLENGE;
		loadcomponents();
	}

	public void setGroupTournament(String tn, int rud, int noofcomp, double winp, double drawp, double lossp,
			int tourType) {
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

	public void setMultiStageTournament(String tn, int rud, int noofcomp, double winp, double drawp, double lossp,
			int tourType, boolean KOSinDob) {
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
		comps = new Competitor[noOfCompetitors];
		file = new File[noOfCompetitors];

		for (int i = 0; i < noOfCompetitors; i++) {

			file[i] = new File("nologo.jpg");
		}
		for (int j = 0; j < 4; j++) {
			imgArray.get(j).setImage(image);
		}

		btnPrevious.setVisible(false);
	}//end loadcomponents

	@FXML
	public void cancel(ActionEvent event) {

	}

	@FXML
	public void close(MouseEvent event) {
		Platform.exit();
	}

	// JUST TO remove errors
	@FXML
	public void previous(ActionEvent event) throws MalformedURLException {
		btnNext.setVisible(true);
		btnPrevious.setVisible(startValue-4==0?false:true);
		for (int i = startValue; i < endValue; i++)
			comps[i] = new Competitor(txtArray.get(i%4).getText(), file[i]);

		if (!txtArray.get(3).isVisible()) {
			for (int i = 0; i < 4; i++) {
				txtArray.get(i).setVisible(true);
				imgArray.get(i).setVisible(true);
				SNArray.get(i).setVisible(true);
			}
		}
		counter2 = startValue-4;
		for (int i = startValue-4; i < startValue; i++) {
			txtArray.get(i%4).setText(comps[i].getName());
			/*Image image = new Image(new File (comps[i].getImage()));
			imgArray.get(i).setImage(new Image(comps[i].getImage()));*/
			String localUrl = comps[i].getImage().toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(i%4).setImage(localImage);
			counter2++;
			SNArray.get(i%4).setText(String.valueOf(counter2));

		}
		//reset the startvalue and endvalue
		startValue-=4;
		counter-=4;
		if (startValue+4>noOfCompetitors) {
			endValue=noOfCompetitors;
		}else
			endValue=startValue+4;
		//for the image files
				img1-=4;img2-=4;img3-=4;img4-=4;
	}//end previous button

	@FXML
	public void next(ActionEvent event) throws MalformedURLException {
		btnPrevious.setVisible(true);
		counter=endValue;
		/*System.out.println(noOfCompetitors);
		System.out.println(startValue);
		System.out.println(endValue);*/
		for (int i = startValue; i < endValue; i++)
			comps[i] = new Competitor(txtArray.get(i%4).getText(), file[i]);

		if (noOfCompetitors > endValue) {
			startValue = endValue;
			endValue = (endValue + 4 > noOfCompetitors ? noOfCompetitors : endValue + 4);
			btnNext.setVisible((endValue==noOfCompetitors ? false:true));
		}
		counter2 = 0;
		for (int i = startValue; i < endValue; i++) {
			if (comps[i] != null) {
				txtArray.get(counter2).setText(comps[i].getName());
				String localUrl = comps[i].getImage().toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
				imgArray.get(counter2).setImage(localImage);
			} else {
				txtArray.get(counter2).setText(null);
				imgArray.get(counter2).setImage(image);
			}
			counter++;
			SNArray.get(counter2).setText(String.valueOf(counter));
			counter2++;

		}

		for (int i = counter2; i < 4; i++) {
			txtArray.get(i).setVisible(false);
			imgArray.get(i).setVisible(false);
			SNArray.get(i).setVisible(false);
			
		}
		//for the image files
		img1+=4;img2+=4;img3+=4;img4+=4;
		/*System.out.println();
		System.out.println(noOfCompetitors);
		System.out.println(startValue);
		System.out.println(endValue);*/
	}// end next button

	// work on the previous button it

	@FXML
	public void finish(ActionEvent event) throws IOException, InvalidBreakerException, TournamentEndedException {
		Breaker[] breakers = { Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, Breaker.HEAD_TO_HEAD };
		TieBreaker tieBreakers = new TieBreaker(breakers);
		//comps = new Competitor[noOfCompetitors];
		//for (int i = 0; i < comps.length; i++) {
			//comps[i] = new Competitor(txtArray.get(i).getText(), file[i]);
			// System.out.println(txtArray.get(i).getText());}
		
	for (int i = startValue; i < endValue; i++)
		comps[i] = new Competitor(txtArray.get(i%4).getText(), file[i]);
	
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
					tournament = new RoundRobinTournament(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint,
							losspoint, tieBreakers, false);
					break;
				case 3:
					tournament = new RoundRobinTournament(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint,
							losspoint, tieBreakers, true);
					break;
				}// end inner switch

				break;
			case MULTISTAGE:
				switch (tournamenttype) {
				case 1:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint,
							tieBreakers, onOfRounds, sigleOrDouble);
					break;
				case 2:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint,
							tieBreakers, false, sigleOrDouble);
					break;
				case 3:
					tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, winpoint, drawpoint, losspoint,
							tieBreakers, true, sigleOrDouble);
					break;
				}// end inner switch

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
		Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
		FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
		ic.setTournament(tournament);
		ic.init();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle(TournamentName);
	}// finish
		// should reverse the work done in the next button by retrieveing values from
		// the comp
		// also you need to change the static file in the image to be dynamic

	public void changeImage1(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file1 = fc.showOpenDialog(null);
		file[img1] = new File(file1.toURI());
		// for the image
		if (file1 != null) {
			String localUrl = file1.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			//System.out.println(localUrl);
			imgArray.get(0).setImage(localImage);
		}
	}// end change image

	public void changeImage2(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("C:\\Users\\Chinedu\\Pictures"));
		File file2 = fc.showOpenDialog(null);
		file[img2] = new File(file2.toURI());
		//System.out.println(file2);
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
		file[img3] = new File(file3.toURI());
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
		file[img4] = new File(file4.toURI());
		// for the image
		if (file4 != null) {
			String localUrl = file4.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);

			imgArray.get(3).setImage(localImage);
		}
	}// end change image

}// end class
