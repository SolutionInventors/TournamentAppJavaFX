/**
 * 
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author ChineduKnight
 *
 */
public class Btn {
	private static Stage PRIMARY_STAGE;

	public void cancel(Pane root) throws IOException {
		home(root,"WelcomeScreen.fxml","welcomeScreen.css","Tournament App");
	}
	public void home(Pane root, String fxml, String Cssfile, String title) throws IOException {
		Parent stageView;
		try {
			stageView = (Pane) FXMLLoader.load(getClass().getResource(Paths.viewpath + fxml));
			Scene ns = new Scene(stageView);
			ns.getStylesheets().add(getClass().getResource(Paths.css + Cssfile).toExternalForm());
			Stage cS = (Stage) root.getScene().getWindow();

			cS.setScene(ns);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void previous(Pane root, ActionEvent event, String fxml, String Cssfile, String title) throws IOException {
		Parent stageView;
		try {
			stageView = (Pane) FXMLLoader.load(getClass().getResource(Paths.viewpath + fxml));
			Scene ns = new Scene(stageView);
			ns.getStylesheets().add(getClass().getResource(Paths.css + Cssfile).toExternalForm());
			Stage cS = (Stage) root.getScene().getWindow();

			cS.setScene(ns);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void next(Pane root, Pane nextView, String fxmlfile,String Cssfile) throws IOException {
		Parent newView = nextView;
		Scene newScene = new Scene(newView);
		newScene.getStylesheets().add(getClass().getResource(Paths.css + Cssfile).toExternalForm());
		PRIMARY_STAGE = (Stage) root.getScene().getWindow();
		PRIMARY_STAGE.setScene(newScene);

	}//end next button
	




}
