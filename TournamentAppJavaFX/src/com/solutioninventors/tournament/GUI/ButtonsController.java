/**
 * 
 */
package com.solutioninventors.tournament.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author ChineduKnight
 *
 */
public class ButtonsController {

	
	public void previous(ActionEvent event, String fxml, String Cssfile, String title) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource(Cssfile).toExternalForm());
		primaryStage.show();
		primaryStage.setTitle(title);}
}
