package com.solutioninventors.tournament.GUI.StandingTable;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.test.Test;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.InvalidBreakerException;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

//@author ChineduKnight
 
public class StandingTableController implements Initializable {
	//for swiss tournament
	File file = new File("arsenal.jpg");
	Competitor c1 = new Competitor("Chidiebere", file);
	Competitor c2 = new Competitor("Fred", file);
	Competitor c3 = new Competitor("Joshua", file);
	Competitor c4 = new Competitor("Chinedu", file);

	Competitor[] comps = { c1, c2, c3, c4 };

	Breaker[] breakers = { Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, Breaker.HEAD_TO_HEAD };

	String [][] tableswiss;
	Tournament tournament;
	@FXML
	ScrollPane sp;
	TableView<StandingTable> table[];
	TableColumn<StandingTable, String> name[];
	TableColumn<StandingTable, Double> WDLFADP[][];
	String tableLabel[] = { "P", "W", "D", "L", "GF", "GA", "GD", "PTS" };
	String standingTableValue[] = { "gamesPlayed", "wins", "draw", "loss", "goalsFor", "goalsAgainst", "goalsDiff",
			"points" };
	int noOfRounds = 1;
	private VBox vBox;

	public void setTournament(Tournament tour) {
		tournament = tour;
		
		//tableswiss = tournament.getTable().getStringTable();
		//SwissTournament
	}
	
	
	@SuppressWarnings("unchecked")
	public void setupTable() {
		//swiss
		SwissTournament tournament = null;
		try {
			TieBreaker tieBreakers = new TieBreaker(breakers);
			tournament = new SwissTournament(comps, SportType.GOALS_ARE_SCORED, 3, 1, 0, tieBreakers, 3);
		} catch (TournamentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			System.exit(0);
		} catch (InvalidBreakerException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

		Test.displayMessage("Swiss begins");
		StringBuilder builder = new StringBuilder(300);

		builder.append("The competitors are: \n");
		Competitor[] tournamentComps = tournament.getCompetitors();

		for (int i = 0; i < tournamentComps.length; i++) {
			builder.append((i + 1) + ". " + tournamentComps[i] + " \n");
		}

		Test.displayMessage(builder.toString());
		Test.displayStandingTable(tournament.getTable().getStringTable());
		System.out.println(tournament.getTable().getStringTable());
		tableswiss =tournament.getTable().getStringTable();
		//StandingTable abc = tournament.getTable();
		System.out.println("i raned");
		
		
		
		
		
		
		
		
		//end swiss
		name = new TableColumn[noOfRounds];
		for (int i = 0; i < name.length; i++) {
			name[i] = new TableColumn<>("Competitor Name");
			name[i].setMinWidth(200);
			name[i].setCellValueFactory(new PropertyValueFactory<>("competitorName"));
		} // end for loop for name setup

		WDLFADP = new TableColumn[noOfRounds][8];
		for (int row = 0; row < WDLFADP.length; row++) {
			for (int col = 0; col < WDLFADP[row].length; col++) {
				WDLFADP[row][col] = new TableColumn<>(tableLabel[col]);
				WDLFADP[row][col].setMinWidth(5);
				WDLFADP[row][col].setCellValueFactory(new PropertyValueFactory<>(standingTableValue[col]));
			}
		}
		List<ObservableList<StandingTable>> abc;
		abc = setuptablevariable();

		table = new TableView[noOfRounds];

		// how to pass in multiple list
		for (int i = 0; i < table.length; i++) {
			table[i] = new TableView<>();
			table[i].setItems(abc.get(i));
			table[i].getColumns().add(name[i]);
			for (int col = 0; col < WDLFADP[i].length; col++) {
				table[i].getColumns().add(WDLFADP[i][col]);
			}
		}
	}// end table setup

	public List<ObservableList<StandingTable>> setuptablevariable() {
		List<ObservableList<StandingTable>> mainList = FXCollections.observableArrayList();
		Map<Integer, ObservableList<StandingTable>> map = new HashMap<Integer, ObservableList<StandingTable>>();
		ObservableList<ObservableList<StandingTable>> items3 = FXCollections.observableArrayList();

		for (int i = 0; i < noOfRounds; i++) {
			map.put(i, FXCollections.observableArrayList());
			items3.add(i, FXCollections.observableArrayList());
			for (int j = 0; j < tableswiss.length; j++) {
				if (j == 0) {
					map.put(i, items3.get(i)).add(new StandingTable(tableswiss[j][0], "5", tableswiss[j][1], tableswiss[j][2], tableswiss[j][3], tableswiss[j][4], tableswiss[j][5], tableswiss[j][6], tableswiss[j][7]));
				}
				map.put(i, items3.get(i)).add(new StandingTable(tableswiss[j][0], "5", tableswiss[j][1], tableswiss[j][2], tableswiss[j][3], tableswiss[j][4], tableswiss[j][5], tableswiss[j][6], tableswiss[j][7]));
			}
		}
		for (int i = 0; i < noOfRounds; i++)
			mainList.add(i, map.get(i));
		return mainList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTable();// call utility method
		Label lbl[] = new Label[noOfRounds];
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new Label("Group " + (i + 1));
		}
		vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		for (int i = 0; i < noOfRounds; i++) {
			vBox.getChildren().add(lbl[i]);
			vBox.getChildren().add(table[i]);
		}

		sp.setContent(vBox);
	}
}// end class