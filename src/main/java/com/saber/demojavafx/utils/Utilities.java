package com.saber.demojavafx.utils;

import com.saber.demojavafx.dto.PersonDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;

import java.util.*;

public class Utilities {
    // درصد دلخواه خود را اینجا تنظیم کنید (مثلاً 0.8 یعنی 80 درصد)
    private static final double SCREEN_PERCENT = 0.85;
    public static Optional<ButtonType> showDialog(String messageTitle, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(messageTitle);
        alert.setContentText(message);
        alert.setResizable(false);
        if (alertType.equals(Alert.AlertType.CONFIRMATION)) {
            return alert.showAndWait();
        } else {
            alert.show();
            return Optional.empty();
        }
    }

    public static Map<String,String> validatePerson(PersonDto personDto) {
        Map<String,String> errors = new HashMap<>();
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<PersonDto>> violations = validator.validate(personDto);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<PersonDto> violation : violations) {
                    String key = violation.getPropertyPath().toString();
                    if (errors.containsKey(key)) {
                        errors.put(key, errors.get(key).concat("-").concat(violation.getMessage()));
                    } else {
                        errors.put(violation.getPropertyPath().toString(), violation.getMessage());
                    }
                }
            }
        }
        return errors;
    }
    public static double getScreenWidthByPercent() {
        // 1. دریافت ابعاد فضای قابل مشاهده صفحه (تسک بار و منوها حذف می‌شوند)
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        return visualBounds.getWidth() * SCREEN_PERCENT;
    }
    public static double getScreenHeightByPercent() {
        // 1. دریافت ابعاد فضای قابل مشاهده صفحه (تسک بار و منوها حذف می‌شوند)
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        return visualBounds.getHeight() * SCREEN_PERCENT;
    }
}