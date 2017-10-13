package com.solutioninventors.tournament.GUI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solutioninventors.tournament.GUI.utility.StandingTable;
import com.solutioninventors.tournament.exceptions.GroupIndexOutOfBoundsException;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.utils.SportType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StandingTableController {

	@FXML private ScrollPane scrollPane;
	@FXML private Label tourStage;
	@FXML private Label lblnostanding;
	private CommonMethods cm = new CommonMethods();
	private Font font[] = new Font[3];
	//private String[][] stringTable;
	private Map<Integer, String[][]> stringtableMap = new HashMap<Integer, String[][]>();
	private Tournament tournament;
	private TableView<StandingTable> table[];
	private TableColumn<StandingTable, String> compName[];
	private TableColumn<StandingTable, String> serialNo[];
	private TableColumn<StandingTable, Double> WDLFADP[][];
	private String tableLabelgoalScored[] = { "P", "W", "D", "L", "GF", "GA", "GD", "PTS" };
	private String tableLabelNOgoalScored[] = { "P", "W", "D", "L", "PTS" };
	private String standingTableValue[] = { "gamesPlayed", "wins", "draw", "loss", "goalsFor", "goalsAgainst",
			"goalsDiff", "points" };
	private String standingTableValuenoGoals[] = {  "gamesPlayed", "wins", "draw", "loss", "points" };
	int noOfGroups;
	private VBox vBox;
	
	public void initialize() {
		font = cm.loadfonts();
		
		lblnostanding.setFont(font[1]);//tournament Specs
		tourStage.setFont(font[1]);
	}
	
	

	public void setTournament(Tournament tour) {
		tournament = tour;
		if (tournament instanceof GroupTournament) {
			noOfGroups = 1;
			stringtableMap.put(0, ((GroupTournament) tournament).getTable().getStringTable());
			outputString(stringtableMap.get(0));
			setupTable();// call utility method

		} // end if tournament is instance of
		else if (tournament instanceof Multistage) {
			
			noOfGroups = ((Multistage) tournament).getNumberOfGroups();
			for (int i = 0; i < ((Multistage) tournament).getNumberOfGroups(); i++) {
				try {
					stringtableMap.put(i,(((Multistage) tournament).getGroup(i).getTable().getStringTable()));
					
				} catch (GroupIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}

			setupTable();// call utility method
		}

	}// end setTournament

	@SuppressWarnings("unchecked")
	public void setupTable() {
		
		serialNo = new TableColumn[noOfGroups];
		for (int i = 0; i < serialNo.length; i++) {
			serialNo[i] = new TableColumn<>("S/N");
			serialNo[i].setMinWidth(5);
			serialNo[i].setCellValueFactory(new PropertyValueFactory<>("sn"));
		} // end for loop for compName setup
		
		
		compName = new TableColumn[noOfGroups];
		for (int i = 0; i < compName.length; i++) {
			compName[i] = new TableColumn<>("Competitor Name");
			compName[i].setMinWidth(200);
			compName[i].setCellValueFactory(new PropertyValueFactory<>("competitorName"));
		} // end for loop for compName setup
		
		if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {
		WDLFADP = new TableColumn[noOfGroups][8];
		for (int row = 0; row < WDLFADP.length; row++) {
			for (int col = 0; col < WDLFADP[row].length; col++) {
				WDLFADP[row][col] = new TableColumn<>(tableLabelgoalScored[col]);
				WDLFADP[row][col].setMinWidth(5);
				WDLFADP[row][col].setCellValueFactory(new PropertyValueFactory<>(standingTableValue[col]));
			}
		}
		}else {
			WDLFADP = new TableColumn[noOfGroups][5];
			for (int row = 0; row < WDLFADP.length; row++) {
				for (int col = 0; col < WDLFADP[row].length; col++) {
					WDLFADP[row][col] = new TableColumn<>(tableLabelNOgoalScored[col]);
					WDLFADP[row][col].setMinWidth(5);
					WDLFADP[row][col].setCellValueFactory(new PropertyValueFactory<>(standingTableValuenoGoals[col]));
				}
			}
		}
		
		List<ObservableList<StandingTable>> abc = setuptablevariable();
		table = new TableView[noOfGroups];

		// how to pass in multiple list
		for (int i = 0; i < table.length; i++) {
			table[i] = new TableView<>();
			table[i].setItems(abc.get(i));
			table[i].getColumns().add(serialNo[i]);
			table[i].getColumns().add(compName[i]);
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
		//vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.setPrefSize(530,Region.USE_COMPUTED_SIZE);//width , height
		for (int i = 0; i < noOfGroups; i++) {
			if (tournament instanceof Multistage) {
				vBox.getChildren().add(lbl[i]);
				vBox.setPrefSize(530,700);//width , height
			}
				
			vBox.getChildren().add(table[i]);
		}

		scrollPane.setContent(vBox);
	}// end table setup

	public List<ObservableList<StandingTable>> setuptablevariable() {
		List<ObservableList<StandingTable>> mainList = FXCollections.observableArrayList();
		Map<Integer, ObservableList<StandingTable>> map = new HashMap<Integer, ObservableList<StandingTable>>();
		
		ObservableList<ObservableList<StandingTable>> items3 = FXCollections.observableArrayList();

		if (tournament instanceof GroupTournament || tournament instanceof Multistage) {
			updateTable(map, items3);
		}
		for (int i = 0; i < noOfGroups; i++)
			mainList.add(i, map.get(i));
		
		return mainList;
	}// end utility method

	
	public void updateTable(Map<Integer, ObservableList<StandingTable>> map,
			ObservableList<ObservableList<StandingTable>> items3) {
		for (int i = 0; i < noOfGroups; i++) { // outer for loop
			map.put(i, FXCollections.observableArrayList());
			items3.add(i, FXCollections.observableArrayList());
			int j = 0;
			if (tournament.getSportType() == SportType.GOALS_ARE_SCORED) {//you should get a better way of checking this
				for (j = 0; j < stringtableMap.get(i).length; j++) { //inner for loop
					if (j == 0) {
						map.put(i, items3.get(i))
								.add(new StandingTable(String.valueOf(j+1), stringtableMap.get(i)[j][0], stringtableMap.get(i)[j][1], stringtableMap.get(i)[j][2],
										stringtableMap.get(i)[j][3], stringtableMap.get(i)[j][4], stringtableMap.get(i)[j][5], stringtableMap.get(i)[j][6],
										stringtableMap.get(i)[j][7], stringtableMap.get(i)[j][8]));
					}
					try {
						map.put(i, items3.get(i))
								.add(new StandingTable( String.valueOf(j+1),stringtableMap.get(i)[j][0], stringtableMap.get(i)[j][1], stringtableMap.get(i)[j][2],
										stringtableMap.get(i)[j][3], stringtableMap.get(i)[j][4], stringtableMap.get(i)[j][5], stringtableMap.get(i)[j][6],
										stringtableMap.get(i)[j][7], stringtableMap.get(i)[j][8]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}//end inner for loop
			}else {
				for (j = 0; j < stringtableMap.get(i).length; j++) { //inner for loop
					if (j == 0) {
						map.put(i, items3.get(i))
								.add(new StandingTable(String.valueOf((j+1)),stringtableMap.get(i)[j][0], stringtableMap.get(i)[j][1], stringtableMap.get(i)[j][2],
										stringtableMap.get(i)[j][3], stringtableMap.get(i)[j][4], stringtableMap.get(i)[j][5]));
					}
					try {
						map.put(i, items3.get(i))
								.add(new StandingTable(String.valueOf((j+1)), stringtableMap.get(i)[j][0], stringtableMap.get(i)[j][1], stringtableMap.get(i)[j][2],
										stringtableMap.get(i)[j][3], stringtableMap.get(i)[j][4], stringtableMap.get(i)[j][5]));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}//end inner for loop
			}
		
		}//end outer for loop
	}
	
	public static void outputString(String[][] array) {
		// loop through array's rows
		for (int row = 0; row < array.length; row++) {
			// loop through columns of current row
			for (int column = 0; column < array[row].length; column++)
				System.out.printf("%s  ", array[row][column]);

			System.out.println(); // start new line of output
		} // end outer for
	} // end method outputArray
	

}// end class