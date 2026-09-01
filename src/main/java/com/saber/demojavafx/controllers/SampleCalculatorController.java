package com.saber.demojavafx.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SampleCalculatorController {

    @FXML
    private TextField textFieldNumber1;
    @FXML
    private TextField textFieldNumber2;
    @FXML
    private Label result;
    @FXML
    private ComboBox<String> operators;

    @FXML
    public void initialize() {
        //result.textProperty().bind(textFieldNumber1.textProperty());

       // textFieldNumber1.textProperty().bindBidirectional(textFieldNumber2.textProperty());
        operators.getItems().addAll("+","-","*","/");
        operators.getSelectionModel().selectFirst();

        StringBinding stringBinding = Bindings.createStringBinding(
                () -> {
                    try {
                        double number1 = Double.parseDouble(textFieldNumber1.getText());
                        double number2 = Double.parseDouble(textFieldNumber2.getText());
                        String operand = operators.getValue();
                        Double res =  calculate(number1,number2,operand);
                        if (res == null)
                            throw new RuntimeException("لطفا عدد معتبر وارد کنید");
                        return String.format("%.2f %s %.2f = %.2f",number1,operand,number2,res);

                    } catch (Exception e) {
                        return "لطفا عدد معتبر وارد کنید";
                    }
                },
                textFieldNumber1.textProperty(),
                textFieldNumber2.textProperty(),
                operators.valueProperty()
        );
        result.textProperty().bind(stringBinding);
    }

    private Double calculate(double number1, double number2, String operand) {
        return switch (operand) {
            case "+" -> number1 + number2;
            case "-" -> number1 - number2;
            case "*" -> number1 * number2;
            case "/" -> number1 / number2;
            default -> null;
        };

//        return null;
    }
}
