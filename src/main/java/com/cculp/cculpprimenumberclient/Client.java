package com.cculp.cculpprimenumberclient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Socket socket = new Socket("localhost", 1337);
        DataInputStream finalFromServer = new DataInputStream(socket.getInputStream());
        DataOutputStream finalToServer = new DataOutputStream(socket.getOutputStream());

        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: purple");
        paneForTextField.setLeft(new Label("Enter number to check if prime "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        stage.setTitle("Client");
        stage.setScene(scene);
        stage.show();



        tf.setOnAction(e -> {
            try {
                int numToCheck = Integer.parseInt(tf.getText().trim());
                finalToServer.writeInt(numToCheck);
                boolean res = finalFromServer.readBoolean();
                ta.appendText(numToCheck + " is prime: " + res + '\n');
                tf.clear();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        stage.setOnCloseRequest(e -> {
            try {
                finalToServer.writeInt(1337);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
