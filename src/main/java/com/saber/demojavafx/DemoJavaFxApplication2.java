package com.saber.demojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class DemoJavaFxApplication2 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                DemoJavaFxApplication2.class.getResource("/com/saber/demojavafx/sample-borderpane.fxml")
        );
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,800,500);
        String css = Objects.requireNonNull(getClass()
                        .getResource("/css/sample-borderpane.css"))
                .toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("demo border pane sample");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(DemoJavaFxApplication2.class,args);
    }
}
