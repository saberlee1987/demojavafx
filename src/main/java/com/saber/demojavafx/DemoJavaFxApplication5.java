package com.saber.demojavafx;

import com.saber.demojavafx.controllers.SampleRegisterPersonController;
import com.saber.demojavafx.utils.Utilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class DemoJavaFxApplication5 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(DemoJavaFxApplication5.class.getResource("/com/saber/demojavafx/sample-calculator.fxml"));
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
        Application.launch(DemoJavaFxApplication5.class,args);
    }
}
