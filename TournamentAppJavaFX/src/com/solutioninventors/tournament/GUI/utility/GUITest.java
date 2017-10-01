/**
 * 
 */
package com.solutioninventors.tournament.GUI.utility;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author ChineduKnight
 */

public class GUITest extends Application {
	private Point2D anchorPt;
	private Point2D previousLocation;
	private static Stage PRIMARY_STAGE;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "WelcomeScreen.fxml"));
			Scene scene = new Scene(root);
			PRIMARY_STAGE = primaryStage;
			// PRIMARY_STAGE.initStyle(StageStyle.TRANSPARENT);
			PRIMARY_STAGE.getIcons().add(new Image("file:logo.jpg"));
			PRIMARY_STAGE.centerOnScreen();
			PRIMARY_STAGE.setScene(scene);
			initMovablePlayer();
			primaryStage.show();
			scene.getStylesheets().add(getClass().getResource(Paths.viewpath + "lookfeel.css").toExternalForm());
			primaryStage.setTitle("Tournament APP");
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		launch(args);
	}
}
