/**
 * 
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author ChineduKnight
 *
 */
public class Btn {
	private Point2D anchorPt;
	private Point2D previousLocation;
	private static Stage PRIMARY_STAGE;

	/*
	 * public void previous(ActionEvent event, String fxml, String Cssfile, String
	 * title) throws IOException {
	 * ((Node)event.getSource()).getScene().getWindow().hide(); Stage primaryStage =
	 * new Stage(); Parent root =
	 * FXMLLoader.load(getClass().getResource(Paths.viewpath+fxml)); Scene scene =
	 * new Scene(root); primaryStage.setScene(scene);
	 * scene.getStylesheets().add(getClass().getResource(Paths.viewpath+Cssfile).
	 * toExternalForm()); primaryStage.show(); primaryStage.setTitle(title);
	 * 
	 * 
	 * 
	 * }
	 */

	public void previous(Pane root, ActionEvent event, String fxml, String Cssfile, String title) throws IOException {
		Parent stageView;
		try {
			stageView = (Pane) FXMLLoader.load(getClass().getResource(Paths.viewpath + fxml));
			Scene ns = new Scene(stageView);
			ns.getStylesheets().add(getClass().getResource(Paths.viewpath + Cssfile).toExternalForm());
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

			// newView = FXMLLoader.load(getClass().getResource(Paths.viewpath+fxmlfile));
			Scene newScene = new Scene(root2);
		
			// Stage currentStage = (Stage) root.getScene().getWindow();
			PRIMARY_STAGE = (Stage) root.getScene().getWindow();
			// PRIMARY_STAGE = currentStage;
			initMovablePlayer();
			PRIMARY_STAGE.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void next(Pane root, Pane nextView, String fxmlfile) throws IOException {
		Parent newView = nextView;
		Scene newScene = new Scene(newView);
		PRIMARY_STAGE = (Stage) root.getScene().getWindow();
		initMovablePlayer();
		PRIMARY_STAGE.setScene(newScene);

	}//end next button
	

	public void nextforGroup(Pane root, ActionEvent event, String fxmlfile,Pane root3) throws IOException {
		Parent newView;
		//newView = (Pane) FXMLLoader.load(getClass().getResource(Paths.viewpath + fxmlfile));
		newView=root3;
		Scene newScene = new Scene(newView);

		// Stage currentStage = (Stage) root.getScene().getWindow();
		PRIMARY_STAGE = (Stage) root.getScene().getWindow();
		// PRIMARY_STAGE = currentStage;
		initMovablePlayer();
		PRIMARY_STAGE.setScene(newScene);

	}//end next button

	public void cancel(ActionEvent event) {
	}

	private void initMovablePlayer() {
		Scene scene = PRIMARY_STAGE.getScene();
		// starting initial anchor point
		scene.setOnMousePressed(mouseEvent -> anchorPt = new Point2D(mouseEvent.getScreenX(), mouseEvent.getScreenY()));
		// dragging the entire stage
		scene.setOnMouseDragged(mouseEvent -> {
			if (anchorPt != null && previousLocation != null) {
				PRIMARY_STAGE.setX(previousLocation.getX() + mouseEvent.getScreenX() - anchorPt.getX());
				PRIMARY_STAGE.setY(previousLocation.getY() + mouseEvent.getScreenY() - anchorPt.getY());
			}
		});
		// set the current location
		scene.setOnMouseReleased(
				mouseEvent -> previousLocation = new Point2D(PRIMARY_STAGE.getX(), PRIMARY_STAGE.getY()));
		// Initialize previousLocation after Stage is shown
		PRIMARY_STAGE.addEventHandler(WindowEvent.WINDOW_SHOWN, (WindowEvent t) -> {
			previousLocation = new Point2D(PRIMARY_STAGE.getX(), PRIMARY_STAGE.getY());
		});
	}

}
