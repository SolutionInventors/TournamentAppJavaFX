/**
 * 
 */
package com.solutioninventors.tournament.GUI.utility;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author ChineduKnight
 */

public class GUITest extends Application {
	private static Stage PRIMARY_STAGE;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "WelcomeScreen.fxml"));
			Scene scene = new Scene(root);
			PRIMARY_STAGE = primaryStage;
			PRIMARY_STAGE.getIcons().add(new Image("file:logo.jpg"));
			PRIMARY_STAGE.centerOnScreen();
			PRIMARY_STAGE.setScene(scene);
			PRIMARY_STAGE.sizeToScene();
			PRIMARY_STAGE.setResizable(false);
			PRIMARY_STAGE.setOnCloseRequest(e -> {
				e.consume();
				closeprogram();
			});
			scene.getStylesheets().add(getClass().getResource(Paths.css + "welcomeScreen.css").toExternalForm());
			primaryStage.setTitle("Tournament APP");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeprogram() {
		Boolean answer = ConfirmBox.display("Close App", "Are you sure you want to exit");
		if (answer) {
			PRIMARY_STAGE.close();

		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
