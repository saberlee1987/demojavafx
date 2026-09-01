package com.saber.demojavafx;

import com.saber.demojavafx.controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class DemoJavaFxApplication6 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(DemoJavaFxApplication6.class.getResource("/com/saber/demojavafx/scene1.fxml"));
        Parent root = loader.load();
        SceneController sceneController = loader.getController();
        sceneController.setStage(stage);
        Scene scene = new Scene(root,400,300);
        stage.setScene(scene);
        stage.setTitle("scene1");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(DemoJavaFxApplication6.class,args);
    }
}
