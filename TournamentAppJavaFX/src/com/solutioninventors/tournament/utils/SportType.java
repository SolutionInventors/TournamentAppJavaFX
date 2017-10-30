/**
 * author: Oguejiofor Chidiebere
 * Jul 31, 2017
 * SportType.java
 * 11:47:48 PM
 *
 */
package com.solutioninventors.tournament.utils;

import java.io.Serializable;

/**
 * A {@code SportType } stores the kind of sport that is being played in
 * a {@code Tournament}. This is used by a {@code GroupTournament}'s  to
 * create a {@code StandingTable}.
 * <br>A {@code StandingTable } object uses it to determine the format of table
 * that would be displayed either P W D L PTS(with SportType.GOALS_ARE_NOT_SCORED)
 * <br> or P W D L GF GA GD PTS ( with SportType.GOALS_ARE_SCORED)
 * 
 * @author Oguejiofor Chidiebere
 * @since v1.0
 * @see com.solutioninventors.tournament.types.Tournament
 * @see com.solutioninventors.tournament.types.group.StandingTable
 * @see com.solutioninventors.tournament.types.group.GroupTournament
 * */
public enum SportType implements Serializable
{
	GOALS_ARE_SCORED , 
	GOALS_ARE_NOT_SCORED ;
	
}
