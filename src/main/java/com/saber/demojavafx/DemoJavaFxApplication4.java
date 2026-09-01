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

        scene.setOnMouseMoved(event ->{
            SampleRegisterPersonController sampleRegisterPersonController = loader.getController();
            System.out.println("label X : "+ event.getX());
            System.out.println("label Y : "+ event.getY());
            String location = "label X : " + event.getX()+"\n"+"label Y : "+event.getY();
            sampleRegisterPersonController.setLabelPointerText(location);
        });
        stage.setOnCloseRequest(event->{
            Optional<ButtonType> optionalResult = Utilities.showDialog("close program", "do you want to close", Alert.AlertType.CONFIRMATION);
            optionalResult.ifPresent(result -> System.out.println(result.getText()));
        });

        stage.setScene(scene);
        stage.setTitle("sample register person form");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(DemoJavaFxApplication4.class,args);
    }
}
