package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class HelpController {
	@FXML private TreeView<String> treeView;
	@FXML private TextArea txtArea;
	@FXML private AnchorPane  rootPane;
	 private CommonMethods cm = new CommonMethods();
	 private Font font[] = new Font[3];

	public void initialize() {
		font = cm.loadfonts();
		txtArea.setFont(new Font("Times New Roman",19));
		
		TreeItem<String> root, tournament,tournamentApp;
		//setup root
		root = new TreeItem<>();
		root.setExpanded(true);

		
		//tournament App
		tournamentApp = makeBranch("Tournament App", root);
        makeBranch("Create Tournament ", tournamentApp);
        makeBranch("Continue Tournament ", tournamentApp);
        makeBranch("Print Tournament ", tournamentApp);
		
		
        //tournament and children
		tournament = makeBranch("Tournament", root);
        makeBranch("Elimination", tournament);
        makeBranch("Group", tournament);
        makeBranch("Multistage", tournament);
        makeBranch("Challenge", tournament);
        
        treeView.setRoot(root);
		treeView.setShowRoot(false);
		
	}//end initialize method
	
	public void mouseClicked(MouseEvent e) {
		TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
		switch (item.getValue()) {
		case "Tournament":
			txtArea.setText("Tournament\r\n\n" + 
					"A tournament is a series of rounds in which a number of contestants compete and the one that prevails through the final round or that finishes with the best record is declared the winner. Each round contains a set of fixtures where each competitor is paired against each other.\r\n" + 
					"In some sports where goals are scored e.g. football, soccer, basketball etc. The winner of a fixture is the competitor with the higher number of goals scored. However, if goals are not scored eg chess, the winner cannot be gotten from scores since none exist.\r\n" + 
					"There are several types of tournaments including:\r\n" + 
					"•	Elimination Tournament\r\n" + 
					"•	Group Tournament\r\n" + 
					"•	Multistage Tournament\r\n" + 
					"•	Challenge\r\n" + 
					"");
			break;
		case "Elimination":
			txtArea.setText("Elimination Tournament\r\n\n" + 
					"An elimination tournament or knockout tournament is divided into successive rounds; each competitor plays in at least one fixture per round. As rounds progress, the number of competitors and fixtures decreases. The final round, usually known as the final or cup final, is played between two competitors and the winner of the final round is the tournament winner. There two types of elimination tournaments namely: Single Elimination and Double Elimination.\r\n" + 
					"\r\n" + 
					"1.	Single Elimination Tournament\r\n" + 
					"In a single-elimination tournament, only the winners of each round advance to the next round and all the losers are eliminated. If there are home and away fixtures, then the winner of a fixture would be determined by the number of cumulative score or points of both competitors in the home and away fixtures. The winner is decided with the minimum number of fixtures. However, most competitors will be eliminated after relatively few matches; a single bad or unlucky performance can nullify many preceding excellent ones.\r\n" + 
					"Ties are broken in a single elimination with a shoot-out until a winner eventually emerges.\r\n" + 
					"2.	Double Elimination Tournament\r\n" + 
					"In a double elimination tournament, a competitor is eliminated only after he losses twice in the tournament.\r\n" + 
					"After the first round of a double elimination tournament, competitors are placed into two brackets namely: winner’s bracket and losers bracket. Two set of fixtures would be played in the loser’s bracket: the minor loser bracket round and the major loser bracket round.\r\n" + 
					"The losers of the minor round would be eliminated while the winners of the minor round would be paired against the losers of the winner’s bracket round in the loser major bracket fixture \r\n" + 
					"Competitors would be eliminated until only two competitors are left and these would form the tournament final. In the tournament final the winners bracket finalist must lose twice before the loser’s bracket finalist is declared the winner\r\n" + 
					"For example, In a double elimination tournament with 4 competitors A, B, C, D. The initial fixture results may be\r\n" + 
					"		A	2		VS 	1		B\r\n" + 
					"C	3		VS 	2		D\r\n" + 
					"\r\n" + 
					"After this the competitors would be group into loser’s bracket and winner’s bracket as follows:\r\n" + 
					"\r\n" + 
					"Winners Bracket:\r\n" + 
					"	A			VS 			C\r\n" + 
					"Loser’s Bracket:\r\n" + 
					"B			VS 			D\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"When these two fixtures are played the losers of the loser’s bracket fixture are eliminated and the winners of the loser’s bracket minor round are paired against the losers of the winner’s bracket fixtures. For example; if\r\n" + 
					"Winners Bracket:\r\n" + 
					"	A		0	VS 		1	C\r\n" + 
					"Loser’s Bracket Minor Fixture:\r\n" + 
					"B		4	VS 		3	D\r\n" + 
					"	\r\n" + 
					"D is eliminated and the loser’s bracket major Fixture becomes:\r\n" + 
					"Loser’s Bracket Minor Fixture:\r\n" + 
					"B			VS 			A\r\n" + 
					"The loser is then eliminated as in \r\n" + 
					"B		2	VS 		4	A\r\n" + 
					"B is eliminated\r\n" + 
					"Currently only B and C is left so they play the tournament final as in:\r\n" + 
					"C			VS 			B\r\n" + 
					"\r\n" + 
					"Note that the winners bracket finalist (C in this case) must lose twice before the loser’s bracket finalist (B in this case) can be declared the winner. However, the winner’s bracket finalist needs just one win to be declared the tournament winner.\r\n" + 
					"");
			break;
		case "Group":
			txtArea.setText("Group Tournament\r\n\n" + 
					"In a group tournament, unlike a knockout tournament there is no scheduled decisive final match. Instead, all the competitors are ranked by examining the results of all the matches played in the tournament. Points are awarded for each match, with competitors ranked based either on total number of points or average points per match. Each competitor plays an equal number of matches, in which case rankings by total points and by average points are equivalent.\r\n" + 
					"The point for win, draw or loss is specified by the tournament organizers or the sport governing body. \r\n" + 
					"Ties are broken in group tournaments by using several criterias including:\r\n" + 
					"•	Head to head record: Here a competitor’s score against an opponent is considered and used to break ties.\r\n" + 
					"•	Goals scored\r\n" + 
					"•	Goals difference\r\n" + 
					"•	Home wins (or white wins in chess)\r\n" + 
					"•	Away wins (or black wins in chess)\r\n" + 
					"•	Etc.\r\n" + 
					"The order of the breakers would be determined by the tournament organizers\r\n" + 
					"There are two types of group tournaments namely Swiss- style tournament, round robin tournament and double round robin tournament.\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"1.	Round Robin Tournament:  In a round robin tournament, each competitor plays all the others an equal number of times, once in a single round-robin tournament and twice in a double round-robin tournament. This is often seen as producing the most reliable rankings. However, for large numbers of competitors it may require an unfeasibly large number of rounds. The rounds of a round robin are determined at the beginning of the tournaments\r\n" + 
					"\r\n" + 
					"2.	Swiss System Tournament:   attempts to determine a winner reliably, based on a smaller number of fixtures. Fixtures are scheduled one round at a time; a competitor will play another who has a similar record in previous rounds of the tournament. This allows the top (and bottom) competitors to be determined with fewer rounds than a round-robin, though the middle rankings are unreliable.\r\n" + 
					"");
			break;
			
		case "Multistage":
			txtArea.setText("Multi-stage Tournament\r\n\n" + 
					"Many tournaments are held in multiple stages, with the top teams in one stage progressing to the next. A group stage (also known as pool play or the pool stage) is a round-robin stage in a multi-stage tournament. The competitors are divided into multiple groups, which play separate round-robins or Swiss in parallel. Measured by a points-based ranking system, the top competitors in each group qualify for the next stage. In most editions of the FIFA World Cup finals tournament, the first round has been a group stage with groups of four teams, the top two qualifying for the \"knockout stage\" played as a single-elimination tournament. This format is common in many international team events, such as World Cups or Olympic tournaments. As well as a fixed number of qualifiers from each group, some may be determined by comparing between different groups: at the 1986 FIFA World Cup the best four of six third-place sides qualified; at the 1999 Rugby World Cup the best one of five third-place sides did so.	 This may also be taken from the best of 4th placed teams.\r\n" + 
					"");
			break;
		case "Challenge":
			txtArea.setText("Challenge \r\n" + 
					"In this format, champions retain their title until they are defeated by an opponent, known as the challenger. This system is used in professional boxing (see lineal championship), and the World Chess Championship. This is actually an alternative to a tournament\r\n" + 
					"");
			break;
		
		default:
			break;
		}
		
	/*	if (item.getValue() != null) {
			System.out.println("I have caught you");
		}
		System.out.println(item.getValue());
		
		if (e.getClickCount() == 2) {
			txtArea.setText("Value for help");
		}else
			txtArea.setText("default text goes here");*/
	}
	 public TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
	        TreeItem<String> item = new TreeItem<>(title);
	        item.setExpanded(true);
	        parent.getChildren().add(item);
	        return item;
	    }
	 
	public void mainmenu(ActionEvent event) throws IOException {
		Btn btn = new Btn();
		btn.previous(rootPane , event, "WelcomeScreen.fxml", "welcomeScreen.css", "Tournament App");
 
	}
}