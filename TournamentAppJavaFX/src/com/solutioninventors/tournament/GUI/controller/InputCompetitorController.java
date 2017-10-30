/**
 * @author Chinedu Oguejiofor
 *10 Aug. 2017
 * 3:23:29 pm
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

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
import javafx.stage.Stage;

public class InputCompetitorController {
	@FXML private Text close;
	@FXML private Button btnNext;
	@FXML private Button btnPrevious;
	@FXML private AnchorPane rootPane;
	@FXML private List<Label> SNArray;
	@FXML private List<TextField> txtArray;
	@FXML private List<ImageView> imgArray;
	@FXML private List<Label> lblArray;
	@FXML private Label lbltourtype;
	@FXML private Label lbltourapp;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	private Stage window;

	private InputStream url11 = getClass().getResourceAsStream(Paths.images + "clickme.PNG");
	private File imageFileforChangeImage;
	private File clickToaddImageFile = new File("clicktoaddimgae.png");
	private File nologoImageFile = new File("nologo.png");
	private final URL url1 = getClass().getResource(Paths.images + "clickme.PNG");
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
	private int[] imageTracker;
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
		try {
			FileUtils.copyURLToFile(url1, clickToaddImageFile);
			FileUtils.copyURLToFile(url2, nologoImageFile);
		} catch (IOException ex) {
			Logger.getLogger(InputCompetitorController.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		// image = new Image(new FileInputStream(new File(url1.toURI())));
		image = new Image(url11);

		comps = new Competitor[noOfCompetitors];
		file = new File[noOfCompetitors];
		imageTracker = new int[noOfCompetitors];
		Arrays.fill(imageTracker, -1);
		for (int i = 0; i < txtArray.size(); i++) {
			txtArray.get(i).setText(null);
		}
		/*
		 * for (int i = 0; i < comps.length; i++) { comps[i] = new Competitor("Player "
		 * + String.valueOf(i + 1), new File(url1.toURI())); } fo
		 */
		btnPrevious.setVisible(false);
		btnNext.setVisible(noOfCompetitors <= 4 ? false : true);

		if (TournamentType == TournamentTypes.CHALLENGE) {
			
			txtArray.get(0).setPromptText("Champion");
			txtArray.get(1).setPromptText("Challenger");
			for (int i = 2; i < 4; i++) {
				txtArray.get(i).setVisible(false);
				imgArray.get(i).setVisible(false);
				SNArray.get(i).setVisible(false);
			}
			endValue = 2;
		} else if (noOfCompetitors == 3) {
			txtArray.get(3).setVisible(false);
			imgArray.get(3).setVisible(false);
			SNArray.get(3).setVisible(false);
		}

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
			String tempCompName = (txtArray.get(i % 4).getText() == null ? "Competitor " + String.valueOf(i + 1)
					: txtArray.get(i % 4).getText());
			if (file[i] != null) {
				file[i] = file[i];
			} else {
				// file[i] = new File(url1.toURI());
				file[i] = clickToaddImageFile;
				imageTracker[i] = i;// track the location in which Click to add image is used
			}
			comps[i] = new Competitor(tempCompName, file[i]);
		}

		if (!txtArray.get(3).isVisible()) {
			for (int i = 0; i < 4; i++) {
				txtArray.get(i).setVisible(true);
				imgArray.get(i).setVisible(true);
				SNArray.get(i).setVisible(true);
			}
		}
		counter2 = startValue - 4;// add this line some where here && !comps[i].getName().trim().equals("Player "
									// + (i+1))
		for (int i = startValue - 4; i < startValue; i++) {
			txtArray.get(i % 4)
					.setText(comps[i].getName().trim().equals("Competitor " + (i + 1)) ? null : comps[i].getName());
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
	public void next(ActionEvent event) throws MalformedURLException, URISyntaxException, IOException {
		btnPrevious.setVisible(true);
		counter = endValue;
		for (int i = startValue; i < endValue; i++) {
			// first check for if text box is empty if so fill with player x else use name
			// check if image is empty if so fill with default image else use image
			String tempCompName = (txtArray.get(i % 4).getText() == null ? "Competitor " + String.valueOf(i + 1)
					: txtArray.get(i % 4).getText());
			if (file[i] != null) {
				file[i] = file[i];
			} else {
				// file[i] = new File(url1.toString());

				// tempFile = FileUtils.toFile(url1);

				file[i] = clickToaddImageFile;

				System.out.println("from to string of url 1");
				System.out.println(url1.toString());
				// file[i] = new File(url1.toURI());
				imageTracker[i] = i;// track the location in which Click to add image is used
			}
			// System.out.println(file[i].getAbsolutePath());
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
			if (comps[i] != null) {
				if (!comps[i].getName().trim().equals("Competitor " + String.valueOf(i + 1))) {
					txtArray.get(counter2).setText(comps[i].getName());
				} else
					txtArray.get(counter2).setText(null);

			} else
				txtArray.get(counter2).setText(null);
			if (file[i] == null) {
				imgArray.get(counter2).setImage(image);
			} else {
				imgArray.get(counter2).setImage(new Image(file[i].toURI().toURL().toString(), false));
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
		if (!(TournamentType == TournamentTypes.CHALLENGE)) {

			for (int i = startValue; i < endValue; i++) {
				String tempCompName = ((txtArray.get(i % 4).getText() == null) ? "Competitor " + String.valueOf(i + 1)
						: txtArray.get(i % 4).getText());
				file[i] = (file[i] != null ? file[i] : nologoImageFile);//I think the error is here
				comps[i] = new Competitor(tempCompName, file[i]);
			}
		}else {
			
			String tempCompName1 = ((txtArray.get(0).getText() == null ) ? "Champion"	: txtArray.get(0).getText());
			String tempCompName2 = ((txtArray.get(1).getText() == null ) ? "Challenger"	: txtArray.get(1).getText());
				file[0] = (file[0] != null ? file[0] : nologoImageFile);
				file[1] = (file[1] != null ? file[1] : nologoImageFile);
			comps[0] = new Competitor(tempCompName1, file[0]);
			comps[1] = new Competitor(tempCompName2, file[1]);
			}
		

		// everywhere where clickimage was used to create image then replace with no
		// logo
		for (int i = 0; i < imageTracker.length; i++) {
			if (imageTracker[i] != -1) {
				comps[i] = new Competitor(comps[i].getName(), nologoImageFile);
			}
		}
		// to fill the comp obj with default values
		// if it has not been set
		for (int i = 0; i < comps.length; i++) {
			if (comps[i] == null) {
				file[i] = (file[i] == null || (file[i].equals(clickToaddImageFile)) ? nologoImageFile : file[i]);
				comps[i] = new Competitor("Competitor " + String.valueOf(i + 1), file[i]);
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

		window.setOnCloseRequest(e -> {
			e.consume();
			closeprogram();
		});

		InputStream url1 = getClass().getResourceAsStream(Paths.images + "logo.png");
		window.getIcons().add(new Image(url1));
		window.setScene(scene);
		window.sizeToScene();
		window.setResizable(false);
		window.show();
		window.setTitle(TournamentName);
	}// end finish method

	private void closeprogram() {
		int answer = ConfirmBox.display("Save", "Do you want to save changes to " + TournamentName);
		if (answer == 1) {
			if (cm.save(tournament)) {
				Tournament.closeFile(tournament.getTournamentFile());
				window.close();
			}
		} else if (answer == 0) {
			window.close();
		}

	}
	// should reverse the work done in the next button by retrieving values from
	// the comp
	// also you need to change the static file in the image to be dynamic

	public void changeImagecustom(MouseEvent e, int fileNum, int arrayNum) {

		imageFileforChangeImage = new File(cm.changeImage(e).toURI());
		file[fileNum] = imageFileforChangeImage;
		if (imageFileforChangeImage != null) {
			try {
				String localUrl = imageFileforChangeImage.toURI().toURL().toString();
				Image localImage = new Image(localUrl, false);
				imgArray.get(arrayNum).setImage(localImage);
				imgArray.get(arrayNum).setVisible(true);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}
	}// end change image

	public void changeImage1(MouseEvent e) {
		changeImagecustom(e, img1, 0);
	}// end change image

	public void changeImage2(MouseEvent e) {
		changeImagecustom(e, img2, 1);
	}// end change image

	public void changeImage3(MouseEvent e) {
		changeImagecustom(e, img3, 2);
	}// end change image

	public void changeImage4(MouseEvent e) {
		changeImagecustom(e, img4, 3);
	}// end change image

}// end class
