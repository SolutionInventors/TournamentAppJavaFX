/**
 * 
 */
package com.solutioninventors.tournament.GUI.utility;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/** here i am no i am not
 * @author ChineduKnight
 *sdfdsfs
 */
public class CompetitorStatus {
	private final SimpleIntegerProperty sn;
	private final SimpleStringProperty competitorName;
	private final SimpleStringProperty eliminated;
	public CompetitorStatus(Integer id, String name, String surname) {
		super();
		this.sn = new SimpleIntegerProperty(id);
		this.competitorName =new SimpleStringProperty(name);
		this.eliminated = new SimpleStringProperty(surname);
	}
	public Integer getSn() {
		return sn.get();
	}
	public String getCompetitorName() {
		return competitorName.get();
	}
	public String getEliminated() {
		return eliminated.get();
	}
	
}//end class
