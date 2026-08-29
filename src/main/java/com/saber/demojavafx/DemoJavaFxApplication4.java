package com.saber.demojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class DemoJavaFxApplication4 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(DemoJavaFxApplication4.class.getResource("/com/saber/demojavafx/sample-register-person.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,400,300);
        String css = Objects.requireNonNull(getClass()
                        .getResource("/css/application.css"))
                .toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("sample register person form");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(DemoJavaFxApplication4.class,args);
    }
}
