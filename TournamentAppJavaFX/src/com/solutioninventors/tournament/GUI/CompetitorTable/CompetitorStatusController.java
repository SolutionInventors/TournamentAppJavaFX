/**
 * 
 */
package com.solutioninventors.tournament.GUI.CompetitorTable;

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
public class CompetitorStatusController implements Initializable{

	@FXML private TableView<CompetitorStatus>table;
	@FXML private TableColumn<CompetitorStatus, Integer>sn;
	@FXML private TableColumn<CompetitorStatus, String>competitorName;
	@FXML private TableColumn<CompetitorStatus, String>eliminated;
	
	public ObservableList<CompetitorStatus> list = FXCollections.observableArrayList(
			
			new CompetitorStatus(1,"Norbert", "Yes"),
			new CompetitorStatus(2,"Kelvin", "No"),
			new CompetitorStatus(3,"Michel", "Yes"),
			new CompetitorStatus(4,"Segun", "No"),
			new CompetitorStatus(5,"mike", "Yes")
			);
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sn.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, Integer>("sn"));
		competitorName.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("competitorName"));
		eliminated.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("eliminated"));
		table.setItems(list);
	}

	
	
	
	
	
	
	
}//end class
