package com.saber.demojavafx;

import com.saber.demojavafx.controllers.PersonController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class DemoJavaFxApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(DemoJavaFxApplication.class.getResource("hello-view.fxml"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("saber66");
        FXMLLoader fxmlLoader = new FXMLLoader(DemoJavaFxApplication.class.getResource("person-list.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == PersonController.class) {
                return new PersonController(entityManagerFactory);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(fxmlLoader.load(), 1500, 500);
        String css = Objects.requireNonNull(getClass()
                        .getResource("/css/application.css"))
                .toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("MyApplication");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
