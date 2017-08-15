/**
 * 
 */
package com.solutioninventors.tournament.GUI.StandingTable.copy;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * @author ChineduKnight
 */
public class StandingTableController implements Initializable {

	@FXML
	ScrollPane sp;
	TableView<StandingTable> table[];
	TableColumn<StandingTable, String> name[];
	TableColumn<StandingTable, Double> WDLFADP[][];
	String tableLabel[] = {"P","W","D","L","GF","GA","GD","PTS"};
	String standingTableValue[] = {"gamesPlayed","wins","draw","loss","goalsFor","goalsAgainst","goalsDiff","points"};
	int noOfRounds = 3;
	private VBox vBox;
	
	@SuppressWarnings("unchecked")
	public void setupTable() {
		name = new TableColumn[noOfRounds]; 
		   for (int i = 0; i < name.length; i++) {
	        	 name[i] = new TableColumn<>("Competitor Name");
	        	 name[i].setMinWidth(200);
	             name[i].setCellValueFactory(new PropertyValueFactory<>("competitorName"));
			}//end for loop for name setup
		   
		   WDLFADP = new TableColumn[noOfRounds][8];  
		   for (int row = 0; row < WDLFADP.length; row++) {
				for (int col = 0; col < WDLFADP[row].length; col++) {
					WDLFADP[row][col] = new TableColumn<>(tableLabel[col]);
			        WDLFADP[row][col].setMinWidth(5);
			        WDLFADP[row][col].setCellValueFactory(new PropertyValueFactory<>(standingTableValue[col]));
			        
				}
			}

		ObservableList<StandingTable> list = FXCollections.observableArrayList(
				
				new StandingTable("Manu", 5, 2, 4, 3, 4, 2, 3, 4), 
				new StandingTable("Arsenal", 5, 2, 4, 3, 4, 2, 3, 4),
				new StandingTable("Chelsea", 5, 2, 4, 3, 4, 2, 3, 4)

		);
		ObservableList<StandingTable> list2 = FXCollections.observableArrayList(

				new StandingTable("Everton", 5, 2, 4, 3, 4, 2, 3, 4),
				new StandingTable("Liverpool", 5, 2, 4, 3, 4, 2, 3, 4), 
				new StandingTable("ManCity", 5, 2, 4, 3, 4, 2, 3, 4)

		);
		table = new TableView[noOfRounds];
		
		//how to pass in multiple list
		for (int i = 0; i < table.length; i++) {
			table[i] = new TableView<>();
			table[i].setItems(list);
			table[i].getColumns().add(name[i]);
			for (int col = 0; col < WDLFADP[i].length; col++) {
				table[i].getColumns().add(WDLFADP[i][col]);
			}
		}
	}// end table setup



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupTable();//call utility method
		Label lbl[] = new Label[noOfRounds];
		for (int i = 0; i < lbl.length; i++) {
			lbl[i] = new Label("Group "+(i+1));
		}
		vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));
		for (int i = 0; i < noOfRounds; i++) {
			vBox.getChildren().add(lbl[i]);
			vBox.getChildren().add(table[i]);
		}

		sp.setContent(vBox);
	}

}// end class
