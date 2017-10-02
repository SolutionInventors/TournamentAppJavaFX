/**
 * 
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

	public void home(Pane root, ActionEvent event, String fxml, String Cssfile, String title) throws IOException {
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

	public static void go(Pane root, ActionEvent event, String title) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();

		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle(title);
	}

	public void nextagain(Pane root, String fxmlfile) throws IOException {
		// Parent newView;
		try {
			FXMLLoader loader = new FXMLLoader();
			Pane root2 = loader
					.load(getClass().getResource(Paths.viewpath + "InputCompetitorScreen.fxml").openStream());

			Scene newScene = new Scene(root2);
		
			PRIMARY_STAGE = (Stage) root.getScene().getWindow();
			PRIMARY_STAGE.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void next(Pane root, Pane nextView, String fxmlfile) throws IOException {
		Parent newView = nextView;
		Scene newScene = new Scene(newView);
		PRIMARY_STAGE = (Stage) root.getScene().getWindow();
		PRIMARY_STAGE.setScene(newScene);

	}//end next button
	

	public void nextforGroup(Pane root, ActionEvent event, String fxmlfile,Pane root3) throws IOException {
		Parent newView;
		newView=root3;
		Scene newScene = new Scene(newView);
		PRIMARY_STAGE = (Stage) root.getScene().getWindow();
		PRIMARY_STAGE.setScene(newScene);

	}//end next button



}
