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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GroupStageScreenController {
	@FXML private Text		txtdisplay;
	@FXML private TextField txtnoOfrounds;
	@FXML private TextField txtnoOfcomps;
	@FXML private TextField txtwinpoint;
	@FXML private TextField txtdrawpoint;
	@FXML private TextField txtlosspoint;
	@FXML private Label noofround;
	@FXML private Label  lbltourtype; 
	@FXML private Label  lbltourapp;
	@FXML private RadioButton swiss;
	@FXML private RadioButton round;
	@FXML private RadioButton doubleround;
	@FXML private AnchorPane  rootPane;
	@FXML private Text txtTourHighlight;
	@FXML private CheckBox standardBreaker;
	private String TournamentName;
	private Btn btn = new Btn();
	private int tourType = 1;//1 swiss, 2 round, 3 doubleRound
	private String message;
	private Boolean goalScored;
	private Boolean standardbreaker =true;
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
		font = CommonMethods.loadfonts();

		lbltourtype.setFont(font[1]);//tournament Specs
		txtdisplay.setFont(font[0]);//the display
		lbltourapp.setFont(font[0]);//TOURNAMNET APP
		txtTourHighlight.setFont(font[0]);
		CommonMethods.isNumber(txtnoOfrounds);
		CommonMethods.isNumber(txtnoOfcomps);
		CommonMethods.isNumber (txtwinpoint);
		CommonMethods.isNumber (txtdrawpoint);
		CommonMethods.isNumber(txtlosspoint);
	}
	@FXML
	public void updateStandardBreaker(ActionEvent event){
		if (standardBreaker.isSelected()) {
			standardbreaker = true;
		} else {
			standardbreaker = false;
		}
	}// end updateStandardBreaker
	@FXML
	public void radioSelected(ActionEvent event) {
		//String noofrnd = String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2));
		if (swiss.isSelected()) {
			tourType = 1;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.editableProperty().set(true);
			txtnoOfrounds.setVisible(true);
			noofround.setVisible(false);
			message = "In a                     group tournament, the number of results is attempted to be shortened or reduced. The fixtures are determined from the current standing. This is achieved by pairing the top and bottom competitors";
			txtTourHighlight.setText("SWISS STYLE");
		} else if (round.isSelected()) {
			tourType = 2;
			//txtnoOfrounds.setText("2");
			txtnoOfrounds.editableProperty().set(false);
			txtnoOfrounds.setVisible(false);
			noofround.setVisible(true);
			noofround.setText(txtnoOfcomps.getText()== null || txtnoOfcomps.getText().equals("")? "0": String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1))));
			System.out.println(noofround.getText());
			message = "In a                         robin tournament, each competitor plays every other competitor once. The no of rounds is determined by the no of competitors. The winner is determined by the ranking table which is based on the no of Wins, Draws , Goals Scored etc";
			txtTourHighlight.setText("SINGLE ROUND");
		}  else if (doubleround.isSelected()) {
			tourType = 3;
			txtnoOfrounds.setText("2");
			txtnoOfrounds.setVisible(false);
			noofround.setVisible(true);
			noofround.setText(txtnoOfcomps.getText()== null || txtnoOfcomps.getText().equals("")? "0": String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2)));
			txtTourHighlight.setText("DOUBLE ROUND");
			message = "In a                          robin tournament, each competitor plays every other competitor twice.The no of rounds is determined by the no of competitors. Most association football leagues in the world are organized on a double round-robin basis, in which every team plays all others in its league once at home and once away.";

		}
		txtdisplay.setText(message);
	}


	@FXML
	public void updaterud(KeyEvent event)  {
		if (!txtnoOfcomps.getText().isEmpty()) {
			try {
				if (tourType == 2) {
					String noofrnd = String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2));
					noofround.setText(noofrnd);
				} else if((tourType == 3)) {
					String noofrnd = String.valueOf(((Integer.valueOf(txtnoOfcomps.getText()) - 1)*2)*2);
					noofround.setText(noofrnd);
				}

			} catch (NumberFormatException e) {
				noofround.setText("Please input the correct no of competitors");
			}
		}//end if

	}

	@FXML
	public void previous(ActionEvent event) throws IOException {

		btn.previous(rootPane,event, "TournamentTypeScreen.fxml", "tourtypecss.css", TournamentName);
	}
	@FXML
	public void cancel(ActionEvent event) throws IOException {
		btn.cancel(rootPane);
	}
	@FXML
	public void next(ActionEvent event) throws IOException {
		//gets the values of the win points and compare
		boolean correctPoints = true;
		try {
			double winpoint = Double.valueOf(txtwinpoint.getText());
			double drawpoint = Double.valueOf(txtdrawpoint.getText());
			double losspoint = Double.valueOf(txtlosspoint.getText());

			if (winpoint>drawpoint && winpoint>losspoint && drawpoint>losspoint) {
				correctPoints = false;
			} else {
				correctPoints = true;
			}
		} catch (NumberFormatException e ) {
			correctPoints = true;
			e.printStackTrace();
		}

		if (txtnoOfrounds.getText().isEmpty() || txtnoOfcomps.getText().isEmpty() || txtwinpoint.getText().isEmpty() || txtdrawpoint.getText().isEmpty()
				|| txtlosspoint.getText().isEmpty() ) {

			CommonMethods.ErrorMessage("Please check input", "Please check that all the textboxes are filled ");
		}else if ((tourType ==1) && (Integer.valueOf(txtnoOfcomps.getText())<=2 || Integer.valueOf(txtnoOfcomps.getText()) % 2 !=0 )) {
			CommonMethods.ErrorMessage("Invalid no of Competitors", "In a swiss tournament the No of comps must be a multiple of 2 and greater than 2 e.g 4,6,8,10,12");
		}else if( (tourType ==2 || tourType ==3) &&  Integer.valueOf(txtnoOfcomps.getText())<=3){
			CommonMethods.ErrorMessage("Invalid no of Competitors", "In a Robin tournament the No of comps must be a greater than 3 e.g 4,5,6,7");
		}else if(correctPoints){
			CommonMethods.ErrorMessage("Invalid Pointing System", "Please check the pointing System, win Point must be greater than draw point and loss point etc");
		}else {

			FXMLLoader loader = new FXMLLoader();
			Breaker[] standardBreakers;
			if (goalScored) {
				standardBreakers = Breaker.getStandardBreaker(SportType.GOALS_ARE_SCORED);
			}else {
				standardBreakers = Breaker.getStandardBreaker(SportType.GOALS_ARE_NOT_SCORED);
			}

			if (standardbreaker) {
				Pane root = loader.load(getClass().getResource(Paths.viewpath+"InputCompetitorScreen.fxml").openStream());
				InputCompetitorController ic = (InputCompetitorController) loader.getController();
				ic.setGroupTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()) ,tourType, standardBreakers );
				btn.next(rootPane, root, "InputCompetitorScreen.fxml","commonStyle.css");
			}else {
				Pane root = loader.load(getClass().getResource(Paths.viewpath+"TieBreaker.fxml").openStream());
				TieBreakerController tb = (TieBreakerController) loader.getController();
				tb.setGroupTournament(TournamentName, goalScored, Integer.valueOf(txtnoOfrounds.getText()),
						Integer.valueOf(txtnoOfcomps.getText()), Double.valueOf(txtwinpoint.getText()),
						Double.valueOf(txtdrawpoint.getText()), Double.valueOf(txtlosspoint.getText()) ,tourType );

				btn.next(rootPane, root, "TieBreaker.fxml","commonStyle.css");
			}

		}


	}
}
