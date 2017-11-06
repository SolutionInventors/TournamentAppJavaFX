package com.solutioninventors.tournament.GUI.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    //Create variable
    static int answer = -111;

    public static int display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        Button cancelButton = new Button("Cancel");
        yesButton.setPrefSize(55, 25);
        noButton.setPrefSize(55, 25);
        cancelButton.setPrefSize(55, 25);
        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = 1;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = 0;
            window.close();
        });
        cancelButton.setOnAction(e -> {
            window.close();
        });
        HBox hLayout = new HBox(10);
        
        VBox layout = new VBox(10);
        hLayout.setPadding(new Insets(0,0,10,20));
        //Add buttons
        hLayout.getChildren().addAll(yesButton,noButton,cancelButton);
        layout.getChildren().addAll(label, hLayout);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}