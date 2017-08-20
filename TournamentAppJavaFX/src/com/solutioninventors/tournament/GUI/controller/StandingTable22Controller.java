package com.solutioninventors.tournament.GUI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solutioninventors.tournament.GUI.utility.StandingTable;
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
			
			tableswiss =((GroupTournament) tournament).getTable().getStringTable();
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
		
		
		@SuppressWarnings("unchecked")
		public void setupTable() {
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
			List<ObservableList<StandingTable>> abc = setuptablevariable();
			//abc 

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
		}//end utility method

	}// end class