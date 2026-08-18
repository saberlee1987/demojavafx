package com.saber.demojavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.Optional;

public class DemoController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;

    @FXML
    protected void onHelloButtonClick() {
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String message = String.format("Hello %s %s",firstname,lastname);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("sayHello message");
        alert.setHeaderText("information sayHello");
        alert.setContentText(message);
        Optional<ButtonType> resultOptional = alert.showAndWait();
        if (resultOptional.isPresent()) {
            ButtonType result = resultOptional.get();
            System.out.println(result.getText());
        } else {
            System.out.println("result is null");
        }


        welcomeText.setText(message);
//        System.out.println("button hello clicked .............");
    }
}
