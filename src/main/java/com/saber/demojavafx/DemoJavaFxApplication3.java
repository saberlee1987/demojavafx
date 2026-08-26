package com.saber.demojavafx;

import com.saber.demojavafx.utils.Utilities;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class DemoJavaFxApplication3 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");
        Button buttonSave = new Button("Save");
        Button buttonSayHello = new Button("SayHello");
        buttonSave.setOnAction(this::buttonAction);
        buttonCancel.setOnAction(this::buttonAction);
        buttonOK.setOnAction(this::buttonAction);
        buttonSayHello.setOnAction(this::sayHelloAction);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(buttonSayHello,buttonSave,buttonOK,buttonCancel);
        VBox box = new VBox(15);
        box.getChildren().addAll(buttonSave,buttonOK,buttonCancel);
//        StackPane pane = new StackPane();
//        FlowPane pane = new FlowPane(10,10);
//        for (int i = 1; i <=10 ; i++) {
//            Button button = new Button("Button "+i);
//            button.setOnAction(this::buttonAction);
//            pane.getChildren().add(button);
//        }
//        pane.getChildren().addAll(buttonSave,buttonOK,buttonCancel);

        GridPane pane = new GridPane(10,10);
        Label nameLabel = new Label("firstname : ");
        TextField nameField = new TextField();

        pane.add(nameLabel,0,0);
        pane.add(nameField,1,0);
        pane.add(hBox,1,1);
//        pane.add(buttonSave,0,0);
//        pane.add(buttonOK,0,1);
//        pane.add(buttonCancel,1,1);

        BorderPane borderPane=new BorderPane();
        borderPane.setTop(pane);
        borderPane.setCenter(box);
        borderPane.setBottom(new Label("bottom"));

        Scene scene = new Scene(borderPane,400,500);
        String css = Objects.requireNonNull(getClass()
                        .getResource("/css/sample-borderpane.css"))
                .toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("demo hbox ");
        stage.setScene(scene);
        stage.show();
    }

    private void sayHelloAction(ActionEvent event) {
        TextField nameField = (TextField) ((Button) event.getSource()).getParent().getParent().getChildrenUnmodifiable().get(1);
        String message = "Hello %s".formatted(nameField.getText());
//        System.out.println(nameField.getText());
//        System.out.println("Hello "+nameField.getText());
        Utilities.showDialog("SayHello",message, Alert.AlertType.INFORMATION);
    }

    private void buttonAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        String text = source.getText();
        System.out.println("text ==> "+text);
    }

    public static void main(String[] args) {
        Application.launch(DemoJavaFxApplication3.class,args);
    }
}
