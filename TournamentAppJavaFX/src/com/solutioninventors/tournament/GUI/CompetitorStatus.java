/**
 * 
 */
package com.solutioninventors.tournament.GUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author ChineduKnight
 *
 */
public class CompetitorStatus {
	private final SimpleIntegerProperty SN;
	private final SimpleStringProperty CompetitorName;
	private final SimpleStringProperty Eliminated;
	public CompetitorStatus(Integer id, String name, String surname) {
		super();
		this.SN = new SimpleIntegerProperty(id);
		this.CompetitorName =new SimpleStringProperty(name);
		this.Eliminated = new SimpleStringProperty(surname);
	}
	public Integer getId() {
		return SN.get();
	}
	public String getName() {
		return CompetitorName.get();
	}
	public String getSurname() {
		return Eliminated.get();
	}
	
	
}//end class
