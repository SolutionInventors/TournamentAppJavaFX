/**
 * 
 */
package com.solutioninventors.tournament.GUI.StandingTable.copy;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author ChineduKnight
 *
 */
public class StandingTable {
	private final SimpleStringProperty competitorName;
	private final SimpleIntegerProperty gamesPlayed;
	private final SimpleIntegerProperty wins;
	private final SimpleIntegerProperty draw;
	private final SimpleIntegerProperty loss;
	private final SimpleIntegerProperty goalsFor;
	private final SimpleIntegerProperty goalsAgainst;
	private final SimpleIntegerProperty goalsDiff;
	private final SimpleIntegerProperty points;
	/*private final String competitorName;
	private final Integer gamesPlayed;
	private final Integer wins;
	private final Integer draw;
	private final Integer loss;
	private final Integer goalsFor;
	private final Integer goalsAgainst;
	private final Integer goalsDiff;
	private final Integer points;*/
	
	public StandingTable(String competitorName, Integer gamesPlayed,
			Integer wins, Integer draw, Integer loss,
			Integer goalsFor, Integer goalsAgainst, Integer goalsDiff,
			Integer points) {
		super();
		this.competitorName =new SimpleStringProperty (competitorName);
		this.gamesPlayed =new SimpleIntegerProperty( gamesPlayed);
		this.wins = new SimpleIntegerProperty(wins);
		this.draw = new SimpleIntegerProperty(draw);
		this.loss = new SimpleIntegerProperty(loss);
		this.goalsFor =new SimpleIntegerProperty( goalsFor);
		this.goalsAgainst = new SimpleIntegerProperty(goalsAgainst);
		this.goalsDiff = new SimpleIntegerProperty(goalsDiff);
		this.points =new SimpleIntegerProperty( points);
	}

	public String getCompetitorName() {
		return competitorName.get();
	}

	public Integer getGamesPlayed() {
		return gamesPlayed.get();
	}

	public Integer getWins() {
		return wins.get();
	}

	public Integer getDraw() {
		return draw.get();
	}

	public Integer getLoss() {
		return loss.get();
	}

	public Integer getGoalsFor() {
		return goalsFor.get();
	}

	public Integer getGoalsAgainst() {
		return goalsAgainst.get();
	}

	public Integer getGoalsDiff() {
		return goalsDiff.get();
	}

	public Integer getPoints() {
		return points.get();
	}
	
	
	
/*	public StandingTable(Integer id, String name, String surname) {
		super();
		this.sn = new SimpleIntegerProperty(id);
		this.competitorName =new SimpleStringProperty(name);
		this.eliminated = new SimpleStringProperty(surname);
	}*/


}//end class
