/**
 * 
 */
package com.solutioninventors.tournament.GUI.StandingTable.copy;

import java.net.URL;
import java.util.ResourceBundle;

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


	@FXML ScrollPane sp;
	 TableView<StandingTable> table[];
	 TableView<StandingTable> table2;
	 
	 @SuppressWarnings("unchecked")
	public void setupTable() {
	//Name column
     TableColumn<StandingTable, String> nameColumn = new TableColumn<>("Competitor Name");
     nameColumn.setMinWidth(200);
     nameColumn.setCellValueFactory(new PropertyValueFactory<>("competitorName"));
     
   //gamesPlayed column
     TableColumn<StandingTable, Double> played = new TableColumn<>("P");
     played.setMinWidth(5);
     played.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
     
   //won column
     TableColumn<StandingTable, Double> won = new TableColumn<>("W");
     won.setMinWidth(5);
     won.setCellValueFactory(new PropertyValueFactory<>("wins"));
   //draw column
     TableColumn<StandingTable, Double> draw = new TableColumn<>("D");
     draw.setMinWidth(5);
     draw.setCellValueFactory(new PropertyValueFactory<>("draw"));
   //loss column
     TableColumn<StandingTable, Double> loss = new TableColumn<>("L");
     loss.setMinWidth(5);
     loss.setCellValueFactory(new PropertyValueFactory<>("loss"));
   //goal for column
     TableColumn<StandingTable, Double> goalF = new TableColumn<>("GF");
     goalF.setMinWidth(5);
     goalF.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));
   //goal a column
     TableColumn<StandingTable, Double> goalA = new TableColumn<>("GA");
     goalA.setMinWidth(5);
     goalA.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));
   //gamesPlayed column
     TableColumn<StandingTable, Double> goalD = new TableColumn<>("GD");
     goalD.setMinWidth(5);
     goalD.setCellValueFactory(new PropertyValueFactory<>("goalsDiff"));
     //gamesPlayed column
     TableColumn<StandingTable, Double> pts = new TableColumn<>("PTS");
     pts.setMinWidth(5);
     pts.setCellValueFactory(new PropertyValueFactory<>("points"));
	 
     ObservableList<StandingTable> list = FXCollections.observableArrayList(

 			new StandingTable("Manu", 5, 2, 4, 3, 4, 2, 3, 4), 
 			new StandingTable("Arsenal", 5, 2, 4, 3, 4, 2, 3, 4),
 			new StandingTable("Emma", 5, 2, 4, 3, 4, 2, 3, 4)

 	);
     table = new TableView[2];
     table[0] = new TableView<>();
     table[0].setItems(list);
     table[0].getColumns().addAll(nameColumn, played, won,draw,loss,goalF,goalA,goalD,pts);
     
     ObservableList<StandingTable> list2 = FXCollections.observableArrayList(

				new StandingTable("Chinedu", 5, 2, 4, 3, 4, 2, 3, 4), 
				new StandingTable("Chidi", 5, 2, 4, 3, 4, 2, 3, 4),
				new StandingTable("John", 5, 2, 4, 3, 4, 2, 3, 4)

		);
     table2 = new TableView<>();
     table2.setItems(list2);
     table2.getColumns().addAll(nameColumn, played, won,draw,loss,goalF,goalA,goalD,pts);
	 
	 }//end table setup
	 
	
	 
	 
	 private VBox vBox;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("called");
		setupTable();
		Button btn = new Button("clickme");
		
		  vBox = new VBox();
	        vBox.setPadding(new Insets(10,10,10,10));
	        vBox.getChildren().addAll(new Label("Standing") , table[0],btn,table2);
	        		
	        		
		sp.setContent(vBox);
		System.out.println("called");
	}

}// end class
