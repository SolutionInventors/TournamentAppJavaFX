package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.SportType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MultiStageScreenController {
	@FXML private RadioButton swiss;
	@FXML private RadioButton round;
	@FXML private RadioButton doubleRound;
	@FXML private RadioButton KOSingle;
	@FXML private RadioButton KODouble;
	@FXML private Text THeader;
	@FXML private Text txtdisplay;
	@FXML private Text txtTourHighlight;
	@FXML private Label noofround;
	@FXML private Label lblHomeandAway;
	@FXML private Label lbltourtype; 
	@FXML private Label lbltourapp;
	@FXML private TextField txtnoOfrounds;
	@FXML private TextField txtnoOfcomps;
	@FXML private TextField txtwinpoint;
	@FXML private TextField txtdrawpoint;
	@FXML private TextField txtlosspoint;
	@FXML private CheckBox standardBreaker; 
	@FXML private CheckBox checkBoxShuffleComps;
	@FXML private CheckBox isSingleKOHomeandAway;
	@FXML private AnchorPane rootPane;
	private Btn btn = new Btn();
	private String TournamentName;
	private int tourType = 1;// 1 SWISS , 2 round, 3 doubleRound
	private boolean isKOSingle = true;
	private Boolean goalScored;
	private Boolean standardbreaker = true;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];

	public void setTournamentName(String tournamentName, Boolean goalScored) {
		TournamentName = tournamentName;
		this.goalScored = goalScored;
	}
	public void uncheckStandardBreaker() {
		standardBreaker.setSelected(false);
		standardbreaker = false;
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
			noofround.setText(String.valueOf(3));
			noofround.setVisible(true);
			// message = "In a single round-robin tournament, each competitor plays every
			// other competitor once. The no of rounds is determined by the no of
			// competitors. The winner is determined by the ranking table which is based on
			// the no of Wins, Draws , Goals Scored etc";

		} else if (doubleRound.isSelected()) {
			tourType = 3;
			txtnoOfrounds.setVisible(false);
			noofround.setText(String.valueOf(6));
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
		if (KOSingle.isSelected()) {
			isKOSingle = true;
			lblHomeandAway.setVisible(true);
			isSingleKOHomeandAway.setVisible(true);
		} else if (KODouble.isSelected()) {
			isKOSingle = false;
			lblHomeandAway.setVisible(false);
			isSingleKOHomeandAway.selectedProperty().set(false);
			isSingleKOHomeandAway.setVisible(false);
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
	public void next(ActionEvent event) throws IOException {
		if (txtnoOfrounds.getText().isEmpty() || txtnoOfcomps.getText().isEmpty() || txtwinpoint.getText().isEmpty()
				|| txtdrawpoint.getText().isEmpty() || txtlosspoint.getText().isEmpty()) {

			cm.ErrorMessage("Please check input",
					"Please check that all the textboxes are filled");
		} else if ( Integer.valueOf(txtnoOfcomps.getText()) <= 4 || Integer.valueOf(txtnoOfcomps.getText()) %4 !=0 ) {
			cm.ErrorMessage("Invalid no of Competitors",
					"In a Muliti-Stage tournament the No of competitors must be a greater than 4 and multiple of 4 e.g 4,8,12");
		} else {

			FXMLLoader loader = new FXMLLoader();
			Breaker[] standardBreakers =Breaker.getStandardBreaker(goalScored ? SportType.GOALS_ARE_SCORED : SportType.GOALS_ARE_NOT_SCORED); 
			if (standardbreaker) {
				Pane root = loader
						.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());
				InputCompetitorController ic = (InputCompetitorController) loader.getController();
				ic.setMultiStageTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()), tourType,
						isKOSingle, isSingleKOHomeandAway.isSelected(), standardBreakers, checkBoxShuffleComps.isSelected());

				btn.next(rootPane, root, "InputCompetitorScreen.fxml", "commonStyle.css");
			} else {
				Pane root = loader.load(getClass().getResource(Paths.viewpath + "TieBreaker.fxml").openStream());
				TieBreakerController tb = (TieBreakerController) loader.getController();
				tb.setMultiStageTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()), tourType,
						isKOSingle,isSingleKOHomeandAway.isSelected(), checkBoxShuffleComps.isSelected());

				btn.next(rootPane, root, "TieBreaker.fxml", "commonStyle.css");
			}
		}
	}// end next

	// just to remove cancel error
	@FXML
	public void cancel(ActionEvent event) {

	}

	

	@FXML
	public void previous(ActionEvent event) throws IOException {
		btn.previous(rootPane, event, "TournamentTypeScreen.fxml", "tourtypecss.css", "Tournament App");
	}// end previous

}// end class
