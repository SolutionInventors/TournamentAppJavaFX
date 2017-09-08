package com.solutioninventors.tournament.GUI.utility;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;



public class Transition {
	private Point2D anchorPt;
	private Point2D previousLocation;
	private static Stage PRIMARY_STAGE;
	
	
	public void FadeOut(Pane firstPane, String fxmlfile ) {
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(1000));
		ft.setNode(firstPane);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.setOnFinished(e->{
			loadNextScene(firstPane,fxmlfile);
		});
		ft.play();
	}
	
	private void loadNextScene(Pane firstPane, String fxmlfile) {
		Parent sv;
		try {
			sv = (Pane) FXMLLoader.load(getClass().getResource(Paths.viewpath+fxmlfile));
			Scene ns = new Scene(sv);
			
			Stage cS = (Stage) firstPane.getScene().getWindow();
			PRIMARY_STAGE = cS;
			initMovablePlayer();
			cS.setScene(ns);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	}
	
	public static void FadeIn(Pane firstPane) {
		FadeTransition ft = new FadeTransition();
		ft.setDuration(Duration.millis(1000));
		ft.setNode(firstPane);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
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
	
}//end class

