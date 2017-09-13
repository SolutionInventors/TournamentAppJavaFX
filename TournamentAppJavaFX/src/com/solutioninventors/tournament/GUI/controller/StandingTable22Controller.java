package com.solutioninventors.tournament.GUI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solutioninventors.tournament.GUI.utility.StandingTable;
import com.solutioninventors.tournament.exceptions.GroupIndexOutOfBoundsException;
import com.solutioninventors.tournament.test.Test;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.GroupTournament;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class StandingTable22Controller {

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Label tourStage;
	private String[][] stringTable;
	private Tournament tournament;
	private TableView<StandingTable> table[];
	private TableColumn<StandingTable, String> name[];
	private TableColumn<StandingTable, Double> WDLFADP[][];
	private String tableLabel[] = { "P", "W", "D", "L", "GF", "GA", "GD", "PTS" };
	private String standingTableValue[] = { "gamesPlayed", "wins", "draw", "loss", "goalsFor", "goalsAgainst",
			"goalsDiff", "points" };
	int noOfGroups;
	private VBox vBox;

	public void setTournament(Tournament tour) {
		tournament = tour;
		if (tournament instanceof GroupTournament) {
			noOfGroups = 1;
			stringTable = ((GroupTournament) tournament).getTable().getStringTable();
			setupTable();// call utility method

		} // end if tournament is instance of
		else if (tournament instanceof Multistage) {
			for (int i = 0; i < ((Multistage) tournament).getNumberOfGroups(); i++) {
				try {
					stringTable = (((Multistage) tournament).getGroup(i).getTable().getStringTable());
					noOfGroups = ((Multistage) tournament).getNumberOfGroups();
				} catch (GroupIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}

			/*
			 * try { stringTable = (((Multistage)
			 * tournament).getGroup(0).getTable().getStringTable()); noOfGroups =
			 * ((Multistage) tournament).getNumberOfGroups(); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
			setupTable();// call utility method
		}

	}// end setTournament

	@SuppressWarnings("unchecked")
	public void setupTable() {
		name = new TableColumn[noOfGroups];
		for (int i = 0; i < name.length; i++) {
			name[i] = new TableColumn<>("Competitor Name");
			name[i].setMinWidth(200);
			name[i].setCellValueFactory(new PropertyValueFactory<>("competitorName"));
		} // end for loop for name setup

		WDLFADP = new TableColumn[noOfGroups][8];
		for (int row = 0; row < WDLFADP.length; row++) {
			for (int col = 0; col < WDLFADP[row].length; col++) {
				WDLFADP[row][col] = new TableColumn<>(tableLabel[col]);
				WDLFADP[row][col].setMinWidth(5);
				WDLFADP[row][col].setCellValueFactory(new PropertyValueFactory<>(standingTableValue[col]));
			}
		}
		List<ObservableList<StandingTable>> abc = setuptablevariable();
		table = new TableView[noOfGroups];

		// how to pass in multiple list
		for (int i = 0; i < table.length; i++) {
			table[i] = new TableView<>();
			table[i].setItems(abc.get(i));
			table[i].getColumns().add(name[i]);
			for (int col = 0; col < WDLFADP[i].length; col++) {
				table[i].getColumns().add(WDLFADP[i][col]);
			}
		}

		// setup display
		Label lbl[] = new Label[noOfGroups];
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new Label("Group " + (i + 1));
		}
		vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		for (int i = 0; i < noOfGroups; i++) {
			if (tournament instanceof Multistage)
				vBox.getChildren().add(lbl[i]);
			vBox.getChildren().add(table[i]);
		}

		scrollPane.setContent(vBox);
	}// end table setup

	public List<ObservableList<StandingTable>> setuptablevariable() {
		List<ObservableList<StandingTable>> mainList = FXCollections.observableArrayList();
		Map<Integer, ObservableList<StandingTable>> map = new HashMap<Integer, ObservableList<StandingTable>>();
		ObservableList<ObservableList<StandingTable>> items3 = FXCollections.observableArrayList();

		for (int i = 0; i < noOfGroups; i++) {
			map.put(i, FXCollections.observableArrayList());
			items3.add(i, FXCollections.observableArrayList());
			for (int j = 0; j < stringTable.length; j++) {
				if (j == 0) {
					map.put(i, items3.get(i))
							.add(new StandingTable(stringTable[j][0], stringTable[j][1], stringTable[j][2],
									stringTable[j][3], stringTable[j][4], stringTable[j][5], stringTable[j][6],
									stringTable[j][7], stringTable[j][8]));
				}
				map.put(i, items3.get(i))
						.add(new StandingTable(stringTable[j][0], stringTable[j][1], stringTable[j][2],
								stringTable[j][3], stringTable[j][4], stringTable[j][5], stringTable[j][6],
								stringTable[j][7], stringTable[j][8]));
			}
		}
		for (int i = 0; i < noOfGroups; i++)
			mainList.add(i, map.get(i));
		return mainList;
	}// end utility method

}// end class