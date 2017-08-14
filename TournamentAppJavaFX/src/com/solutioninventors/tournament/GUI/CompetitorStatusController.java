/**
 * 
 */
package com.solutioninventors.tournament.GUI;

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
	@FXML private TableColumn<CompetitorStatus, Integer>SN;
	@FXML private TableColumn<CompetitorStatus, String>CompetitorName;
	@FXML private TableColumn<CompetitorStatus, String>Eliminated;
	
	public ObservableList<CompetitorStatus> list = FXCollections.observableArrayList(
			
			new CompetitorStatus(1,"Norbert", "Edomah"),
			new CompetitorStatus(2,"Kelvin", "Enumah"),
			new CompetitorStatus(3,"Michel", "John"),
			new CompetitorStatus(4,"Segun", "francis"),
			new CompetitorStatus(5,"chidi", "Ogu")
			);

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SN.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, Integer>("SN"));
		CompetitorName.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("CompetitorName"));
		Eliminated.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("Eliminated"));
		table.setItems(list);
		System.out.println(list);
	}

	
	
	
	
	
	
	
}//end class
