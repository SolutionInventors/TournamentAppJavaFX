package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.AlertBox;
import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.utils.Breaker;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MultiStageScreenController {
	@FXML
	private RadioButton swiss;
	@FXML
	private RadioButton round;
	@FXML
	private RadioButton doubleRound;
	@FXML
	private RadioButton singleelim;
	@FXML
	private RadioButton doubleelim;
	@FXML
	private Label noofround;
	@FXML
	private TextField txtnoOfrounds;
	@FXML
	private TextField txtnoOfcomps;
	@FXML
	private TextField txtwinpoint;
	@FXML
	private TextField txtdrawpoint;
	@FXML
	private TextField txtlosspoint;
	@FXML
	private Text THeader;
	@FXML private Text txtdisplay;
	@FXML private Text  txtTourHighlight;
	@FXML private Label  lbltourtype; 
	@FXML private Label lbltourapp;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private CheckBox standardBreaker;
	private Btn btn = new Btn();
	private String TournamentName;
	private int tourType = 1;// 1 swiss, 2 round, 3 doubleRound
	private boolean singleDoubleElim = false;
	private Boolean goalScored;
	private Boolean standardbreaker = true;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];

	public void setTournamentName(String tournamentName, Boolean goalScored) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
	}

	public void initialize() {
		noofround.setVisible(false);
		font = cm.loadfonts();

		 lbltourtype.setFont(font[1]);//tournament Specs
		 txtdisplay.setFont(font[0]);//the display
		 lbltourapp.setFont(font[0]);//TOURNAMNET APP
		 txtTourHighlight.setFont(font[0]);
		cm.isNumber(txtnoOfrounds);
		cm.isNumber(txtnoOfcomps);
		cm.isNumber(txtwinpoint);
		cm.isNumber(txtdrawpoint);
		cm.isNumber(txtlosspoint);
	}

	@FXML
	public void radioSelected(ActionEvent event) {
		String noofrnd = String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1) * 2));
		if (swiss.isSelected()) {
			tourType = 1;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.editableProperty().set(true);
			txtnoOfrounds.setVisible(true);
			noofround.setVisible(false);
			// message = "In a Swiss style group tournament, the number of results is
			// attempted to be shortened or reduced. The fixtures are determined from the
			// current standing. This is achieved by pairing the top and bottom
			// competitors";
		} else if (round.isSelected()) {
			tourType = 2;
			txtnoOfrounds.setVisible(false);
			noofround.setText(String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2)));
			noofround.setVisible(true);
			// message = "In a single round-robin tournament, each competitor plays every
			// other competitor once. The no of rounds is determined by the no of
			// competitors. The winner is determined by the ranking table which is based on
			// the no of Wins, Draws , Goals Scored etc";

		} else if (doubleRound.isSelected()) {
			tourType = 3;
			txtnoOfrounds.setVisible(false);
			noofround.setText(String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2)*2));
			noofround.setVisible(true);
			// message = "In a double-round-robin tournament, each competitor plays every
			// other competitor twice.The no of rounds is determined by the no of
			// competitors. Most association football leagues in the world are organized on
			// a double round-robin basis, in which every team plays all others in its
			// league once at home and once away.";
		}
		// THeader.setText(message);

	}

	@FXML
	public void tourselected(ActionEvent e) {
		if (singleelim.isSelected()) {
			singleDoubleElim = false;
		} else if (doubleelim.isSelected()) {
			singleDoubleElim = true;
		}
	}

	@FXML
	public void updateStandardBreaker(ActionEvent event) {
		if (standardBreaker.isSelected()) {
			standardbreaker = true;
		} else {
			standardbreaker = false;
		}
	}// end updateStandardBreaker

	@FXML
	public void updaterud(KeyEvent event) {
		if (!txtnoOfcomps.getText().isEmpty()) {
			try {
				String noofrnd = String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1) * 2));
				noofround.setText(noofrnd);
			} catch (NumberFormatException e) {
				noofround.setText("Please input the correct no of competitors");
			}
		} // end if

	}

	@FXML
	public void next(ActionEvent event) throws IOException {
		if (txtnoOfrounds.getText().isEmpty() || txtnoOfcomps.getText().isEmpty() || txtwinpoint.getText().isEmpty()
				|| txtdrawpoint.getText().isEmpty() || txtlosspoint.getText().isEmpty()) {

			AlertBox.display("Please check input",
					"Please check that all the textboxes are filled and that the no of competitors is a mutilple of 4 and greater than 4");
		} else if ((tourType == 1) && (Integer.valueOf(txtnoOfcomps.getText()) < 2)
				|| Integer.valueOf(txtnoOfcomps.getText()) % 2 != 0) {
			AlertBox.display("Invalid no of Competitors",
					"In a swiss tournament the No of comps must be a multiple of 2 and greater than 2 e.g 4,6,8,10,12");
		} else if ((tourType == 2 || tourType == 3) && Integer.valueOf(txtnoOfcomps.getText()) < 2) {
			AlertBox.display("Invalid no of Competitors",
					"In a Robin tournament the No of comps must be a greater than 2 e.g 3,4,5");
		} else {

			FXMLLoader loader = new FXMLLoader();
			Breaker[] standardBreakers = Breaker.getBreakers(Breaker.ALL, Breaker.GOAL_DEPENDENT);
			if (standardbreaker) {
				Pane root = loader
						.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());
				InputCompetitorController ic = (InputCompetitorController) loader.getController();
				ic.setMultiStageTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()), tourType,
						singleDoubleElim, standardBreakers);

				btn.next(rootPane, root, "InputCompetitorScreen.fxml", "commonStyle.css");
			} else {
				Pane root = loader.load(getClass().getResource(Paths.viewpath + "TieBreaker.fxml").openStream());
				TieBreakerController tb = (TieBreakerController) loader.getController();
				tb.setMultiStageTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()), tourType,
						singleDoubleElim);

				btn.next(rootPane, root, "TieBreaker.fxml", "commonStyle.css");
			}
		}
	}// end next

	// just to remove cancel error
	@FXML
	public void cancel(ActionEvent event) {

	}

	@FXML
	public void closeApp(MouseEvent event) {
		Platform.exit();

	}

	@FXML
	public void previous(ActionEvent event) throws IOException {
		btn.previous(rootPane, event, "TournamentTypeScreen.fxml", "tourtypecss.css", "Tournament App");
	}// end previous

}// end class
