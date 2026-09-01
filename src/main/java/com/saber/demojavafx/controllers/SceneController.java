package com.saber.demojavafx.controllers;

import com.saber.demojavafx.DemoJavaFxApplication6;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void switchToScene2() throws Exception {
        showStage("/com/saber/demojavafx/scene2.fxml","scene2");
    }

    @FXML
    public void switchToScene1() throws Exception {
        showStage("/com/saber/demojavafx/scene1.fxml","scene1");
    }

    private void showStage(String fxmlPage,String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(DemoJavaFxApplication6.class.getResource(fxmlPage));
        Parent root = loader.load();
        SceneController sceneController = loader.getController();
        sceneController.setStage(stage);
        Scene scene = new Scene(root,400,300);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void close(ActionEvent event) {
        stage.close();
    }
}
