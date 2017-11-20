/**
 * 
 */
package com.solutioninventors.tournament.GUI.controller;

import com.solutioninventors.tournament.GUI.utility.CompetitorStatus;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

/**
 * @author ChineduKnight
 */
public class CompetitorStatusController {

	@FXML private TableView<CompetitorStatus>table;
	@FXML private TableColumn<CompetitorStatus, Integer>sn;
	@FXML private TableColumn<CompetitorStatus, String>competitorName;
	@FXML private TableColumn<CompetitorStatus, String>eliminated;
	
	@FXML private Label tourStage;
	private Font font[] = new Font[3];
	
	private Tournament tournament;
	private ObservableList<CompetitorStatus> list;
	
	public void initialize() {
		font = CommonMethods.loadfonts();

		tourStage.setFont(font[1]);// tournament Specs
		//tourStage.size
	}
	
	public void setTournament(Tournament tour) {
		tournament = tour;
		
		
		sn.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, Integer>("sn"));
		competitorName.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("competitorName"));
		eliminated.setCellValueFactory(new PropertyValueFactory<CompetitorStatus, String>("eliminated"));
		
		table.setItems(setuptablevariable());
	}
	

	
	public ObservableList<CompetitorStatus> setuptablevariable() {
	list = FXCollections.observableArrayList();
	Competitor comp[];
	comp = tournament.getCompetitors();
	for (int i = 0; i < comp.length; i++) {
		list.add(new  CompetitorStatus(i+1,comp[i].getName(), (comp[i].isEliminated() ? "Yes" : "No")));
	}
	return list;
	
	}//end utility method
	
	
	
	
	
}//end class
