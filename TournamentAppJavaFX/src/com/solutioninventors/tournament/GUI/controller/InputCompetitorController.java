/**
 * @author Chinedu Oguejiofor
 *10 Aug. 2017
 * 3:23:29 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.ConfirmBox;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class InputCompetitorController {
	@FXML
	private Text close;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnPrevious;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private List<Label> SNArray;
	@FXML
	private List<TextField> txtArray;
	// @FXML private List<Button> btnimgArray;
	@FXML
	private List<ImageView> imgArray;
	@FXML
	private List<Label> lblArray;
	@FXML
	private Label lbltourtype;
	@FXML
	private Label lbltourapp;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	private Stage window;

	private URL url1 = getClass().getResource(Paths.images + "clickme.PNG");
	private URL url2 = getClass().getResource(Paths.images + "nologo.jpg");
	private Image image;
	// shared variables
	private String TournamentName;
	private int noOfCompetitors;
	private int onOfRounds;
	private Boolean goalScored;
	private SportType goalsOrNoGoals;
	Breaker[] breakers;

	// for group
	private double winpoint;
	private double drawpoint;
	private double losspoint;
	// for Knock out
	private boolean sigleOrDouble;
	private boolean homeandAway;
	// for the images file count
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
	private TieBreaker tieBreakers;

	public void initialize() {
		font = cm.loadfonts();

		lbltourtype.setFont(font[1]);// tournament Specs
		lbltourapp.setFont(font[0]);// TOURNAMNET APP

		for (Label currentlabel : lblArray) {
			currentlabel.setFont(font[0]);
		}
	}

	public void setTournamentName(String tournamentName) {
		TournamentName = tournamentName;
	}

	public void setKOtournament(String tn, Boolean goalScored, int noofcomp, boolean sigleTour, boolean homeAndAway) {
		TournamentName = tn;
		this.goalScored = goalScored;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.KNOCKOUT;
		homeandAway = homeAndAway;
		sigleOrDouble = sigleTour;
		// System.out.println("KnockOut tournament called");
		loadcomponents();
	}

	public void setChallengeTournament(String tn, Boolean goalScored, int rud) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = 2;
		TournamentType = TournamentTypes.CHALLENGE;
		loadcomponents();
	}

	public void setGroupTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType, Breaker[] tieBreaker) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.GROUP;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		breakers = tieBreaker;
		try {
			tieBreakers = new TieBreaker(breakers);
		} catch (InvalidBreakerException e) {
			e.printStackTrace();
		}
		loadcomponents();
	}

	public void setMultiStageTournament(String tn, Boolean goalScored, int rud, int noofcomp, double winp, double drawp,
			double lossp, int tourType, boolean KOSinDob, Breaker[] tieBreaker) {
		TournamentName = tn;
		this.goalScored = goalScored;
		onOfRounds = rud;
		noOfCompetitors = noofcomp;
		TournamentType = TournamentTypes.MULTISTAGE;
		winpoint = winp;
		drawpoint = drawp;
		losspoint = lossp;
		tournamenttype = tourType;
		sigleOrDouble = KOSinDob;
		breakers = tieBreaker;
		try {
			tieBreakers = new TieBreaker(breakers);
		} catch (InvalidBreakerException e) {
			e.printStackTrace();
		}
		loadcomponents();
	}

	public void loadcomponents() {
		// this was used to set the default no logo image
		try {
			image = new Image(new FileInputStream(new File(url1.toURI())));
		} catch (FileNotFoundException | URISyntaxException e) {
			e.printStackTrace();
		}
		comps = new Competitor[noOfCompetitors];
		file = new File[noOfCompetitors];

		btnPrevious.setVisible(false);
		btnNext.setVisible(noOfCompetitors <= 4 ? false : true);

		if (TournamentType == TournamentTypes.CHALLENGE) {
			txtArray.get(0).setPromptText("Champion");
			txtArray.get(1).setPromptText("Challenger");
			for (int i = 2; i <4; i++) {
				txtArray.get(i).setVisible(false);
				imgArray.get(i).setVisible(false);
				SNArray.get(i).setVisible(false);
			}
			endValue = 2;
		} // end if
		
	
		if (goalScored) {
			goalsOrNoGoals = SportType.GOALS_ARE_SCORED;
		} else {
			goalsOrNoGoals = SportType.GOALS_ARE_NOT_SCORED;
		}
	}// end load components

	@FXML
	public void cancel(ActionEvent event) {
		Btn btn = new Btn();
		try {
			btn.cancel(rootPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void previous(ActionEvent event) throws MalformedURLException, URISyntaxException {
		btnNext.setVisible(true);
		btnPrevious.setVisible(startValue - 4 == 0 ? false : true);
		for (int i = startValue; i < endValue; i++) {
			String tempCompName = (txtArray.get(i % 4).getText() == null ? "Player " + String.valueOf(i + 1)
					: txtArray.get(i % 4).getText());
			file[i] = (file[i] != null ? file[i] : new File(url1.toURI()));
			comps[i] = new Competitor(tempCompName, file[i]);
		}

		if (!txtArray.get(3).isVisible()) {
			for (int i = 0; i < 4; i++) {
				txtArray.get(i).setVisible(true);
				imgArray.get(i).setVisible(true);
				SNArray.get(i).setVisible(true);
			}
		}
		counter2 = startValue - 4;//add this line some where here && !comps[i].getName().trim().equals("Player " + (i+1))
		for (int i = startValue - 4; i < startValue; i++) {
			txtArray.get(i % 4).setText(comps[i].getName());
			String localUrl = comps[i].getImage().toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(i % 4).setImage(localImage);
			counter2++;
			SNArray.get(i % 4).setText(String.valueOf(counter2));

		}
		// reset the startvalue and endvalue
		startValue -= 4;
		counter -= 4;
		if (startValue + 4 > noOfCompetitors) {
			endValue = noOfCompetitors;
		} else
			endValue = startValue + 4;
		// for the image files
		img1 -= 4;
		img2 -= 4;
		img3 -= 4;
		img4 -= 4;
	}// end previous button

	@FXML
	public void next(ActionEvent event) throws MalformedURLException, URISyntaxException {
		btnPrevious.setVisible(true);
		counter = endValue;
		for (int i = startValue; i < endValue; i++) {
			// first check for if text box is empty if so fill with player x else use name
			// check if image is empty if so fill with default image else use image
			String tempCompName = (txtArray.get(i % 4).getText() == null ? "Player " + String.valueOf(i + 1)
					: txtArray.get(i % 4).getText());
			file[i] = (file[i] != null ? file[i] : new File(url1.toURI()));
			comps[i] = new Competitor(tempCompName, file[i]);

		}
		if (noOfCompetitors > endValue) {
			startValue = endValue;
			endValue = (endValue + 4 > noOfCompetitors ? noOfCompetitors : endValue + 4);
			btnNext.setVisible((endValue == noOfCompetitors ? false : true));
		}
		counter2 = 0;
		// this decides what to display
		for (int i = startValue; i < endValue; i++) {
			// if (playername.equals("Player " + (i+1) ) ) {
			if (comps[i] != null && !comps[i].getName().trim().equals("Player " + (i+1))) {
				txtArray.get(counter2).setText(comps[i].getName());
				String localUrl = comps[i].getImage().toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
				imgArray.get(counter2).setImage(localImage);
				System.out.println(comps[i].getName());
				System.out.println("Player "+ (i+1));
				System.out.println(comps[i].getName().trim().equals("Player " + (i+1))); 
			} else {
				txtArray.get(counter2).setText(null);
				imgArray.get(counter2).setImage(image);
				// imgArray.get(counter2).setVisible(false);
				// btnimgArray.get(counter2).setVisible(true);
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
		// for the image files
		img1 += 4;
		img2 += 4;
		img3 += 4;
		img4 += 4;
	}// end next button

	// work on the previous button it

	@FXML
	public void finish(ActionEvent event)
			throws IOException, InvalidBreakerException, TournamentEndedException, URISyntaxException {

		for (int i = startValue; i < endValue; i++) {
			String tempCompName = (txtArray.get(i % 4).getText() == null ? "Player " + String.valueOf(i + 1)
					: txtArray.get(i % 4).getText());
			file[i] = (file[i] != null ? file[i] : new File(url1.toURI()));
			comps[i] = new Competitor(tempCompName, file[i]);
		}
		// to fill the comp obj with default values

		for (int i = 0; i < comps.length; i++) {
			if (comps[i] == null) {
				file[i] = (file[i] == null || (file[i].equals(new File(url1.toURI()))) ?  new File(url2.toURI()) 
						:file[i]);
				comps[i] = new Competitor("Player " + String.valueOf(i + 1), file[i]);
			}
		}
		try {
			switch (TournamentType) {
			case KNOCKOUT:
				if (sigleOrDouble) {
					tournament = new SingleEliminationTournament(goalsOrNoGoals, comps, homeandAway);
				} else {
					tournament = new DoubleElimination(goalsOrNoGoals, comps);
				}

				break;
			case CHALLENGE:
				tournament = new Challenge(goalsOrNoGoals, comps, onOfRounds);
				break;
			case GROUP:

				switch (tournamenttype) {

				case 1:
					tournament = new SwissTournament(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint, tieBreakers,
							onOfRounds);
					break;
				case 2:
					tournament = new RoundRobinTournament(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint,
							tieBreakers, false);
					break;
				case 3:
					tournament = new RoundRobinTournament(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint,
							tieBreakers, true);
					break;
				}// end inner switch

				break;
			case MULTISTAGE:
				switch (tournamenttype) {
				case 1:
					tournament = new Multistage(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint, tieBreakers,
							onOfRounds, sigleOrDouble);
					break;
				case 2:
					tournament = new Multistage(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint, tieBreakers,
							false, sigleOrDouble);
					break;
				case 3:
					tournament = new Multistage(comps, goalsOrNoGoals, winpoint, drawpoint, losspoint, tieBreakers,
							true, sigleOrDouble);
					break;
				}// end inner switch

				break;
			default:
				break;
			}
			tournament.setName(TournamentName);
		} catch (TournamentException | InvalidBreakerException e) {
			e.printStackTrace();
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		window = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource(Paths.viewpath + "FRSCIScreen.fxml").openStream());
		FRSCIScreenController ic = (FRSCIScreenController) loader.getController();
		ic.setTournament(tournament);
		ic.init();
		Scene scene = new Scene(root);
		/*
		 * window.setOnCloseRequest(e -> { e.consume(); closeprogram(); });
		 */
		URL url1 = getClass().getResource(Paths.images + "logo.jpg");
		window.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
		window.setScene(scene);
		window.setResizable(false);
		window.show();
		window.setTitle(TournamentName);
	}// end finish method

	private void closeprogram() {
		Boolean answer = ConfirmBox.display("Close App", "Are you sure you want to exit");
		if (answer) {
			window.close();

		}
	}
	// should reverse the work done in the next button by retrieveing values from
	// the comp
	// also you need to change the static file in the image to be dynamic

	public void changeImage1(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures"));
		fc.getExtensionFilters().add(imageFilter);
		File file1 = fc.showOpenDialog(null);
		try {
			file[img1] = new File(file1.toURI());
		} catch (Exception e1) {
		}
		// for the image
		if (file1 != null) {
			String localUrl = file1.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			// System.out.println(localUrl);
			imgArray.get(0).setImage(localImage);
			// btnimgArray.get(0).setVisible(false);
			imgArray.get(0).setVisible(true);
		}
	}// end change image

	public void changeImage2(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures"));
		File file2 = fc.showOpenDialog(null);
		file[img2] = new File(file2.toURI());
		// System.out.println(file2);
		// for the image
		if (file2 != null) {
			String localUrl = file2.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(1).setImage(localImage);
			// btnimgArray.get(1).setVisible(false);
			imgArray.get(1).setVisible(true);
		}
	}// end change image

	public void changeImage3(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures"));
		File file3 = fc.showOpenDialog(null);
		file[img3] = new File(file3.toURI());
		// for the image
		if (file3 != null) {
			String localUrl = file3.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);
			imgArray.get(2).setImage(localImage);
			// btnimgArray.get(2).setVisible(false);
			imgArray.get(2).setVisible(true);
		}
	}// end change image

	public void changeImage4(MouseEvent e) throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Pictures"));
		File file4 = fc.showOpenDialog(null);
		file[img4] = new File(file4.toURI());
		// for the image
		if (file4 != null) {
			String localUrl = file4.toURI().toURL().toString();
			Image localImage = new Image(localUrl, false);

			imgArray.get(3).setImage(localImage);
			// btnimgArray.get(3).setVisible(false);
			imgArray.get(3).setVisible(true);
		}
	}// end change image

	public void changeimage1(ActionEvent e) throws MalformedURLException {
		changeImage1(null);
	}

	public void changeimage2(ActionEvent e) throws MalformedURLException {
		changeImage2(null);
	}

	public void changeimage3(ActionEvent e) throws MalformedURLException {
		changeImage3(null);
	}

	public void changeimage4(ActionEvent e) throws MalformedURLException {
		changeImage4(null);
	}
}// end class
