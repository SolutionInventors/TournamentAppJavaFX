/**
 * 
 */
package com.solutioninventors.tournament.GUI.StandingTable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author ChineduKnight
 */
public class StandingTableController implements Initializable {

	@FXML
	private TableView<StandingTable> table;
	@FXML
	private TableColumn<StandingTable, String> competitorName;
	@FXML
	private TableColumn<StandingTable, Integer> gamesPlayed;
	@FXML
	private TableColumn<StandingTable, Integer> wins;
	@FXML
	private TableColumn<StandingTable, Integer> draw;
	@FXML
	private TableColumn<StandingTable, Integer> loss;
	@FXML
	private TableColumn<StandingTable, Integer> goalsFor;
	@FXML
	private TableColumn<StandingTable, Integer> goalsAgainst;
	@FXML
	private TableColumn<StandingTable, Integer> goalsDiff;
	@FXML
	private TableColumn<StandingTable, Integer> points;

	public ObservableList<StandingTable> list = FXCollections.observableArrayList(

			new StandingTable("Manu", 5, 2, 4, 3, 4, 2, 3, 4), new StandingTable("Arsenal", 5, 2, 4, 3, 4, 2, 3, 4),
			new StandingTable("Emma", 5, 2, 4, 3, 4, 2, 3, 4)

	);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		competitorName.setCellValueFactory(new PropertyValueFactory<StandingTable, String>("competitorName"));
		gamesPlayed.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("gamesPlayed"));
		wins.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("wins"));
		draw.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("draw"));
		loss.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("loss"));
		goalsFor.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("goalsFor"));
		goalsAgainst.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("goalsAgainst"));
		goalsDiff.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("goalsDiff"));
		points.setCellValueFactory(new PropertyValueFactory<StandingTable, Integer>("points"));
		table.setItems(list);
	}

}// end class
