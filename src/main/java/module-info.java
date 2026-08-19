module com.saber.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires lombok;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires persian.date.time;

    // JavaFX
    opens com.saber.demojavafx to javafx.fxml;
    opens com.saber.demojavafx.controllers  to javafx.fxml;
    // DTO - JavaFX + Hibernate Validator
    opens com.saber.demojavafx.dto to javafx.base, org.hibernate.validator;
    // Hibernate entities
    opens com.saber.demojavafx.model to org.hibernate.orm.core;
    opens com.saber.demojavafx.utils to org.hibernate.validator;


    exports com.saber.demojavafx;
    exports com.saber.demojavafx.controllers;
    exports com.saber.demojavafx.services;
    exports com.saber.demojavafx.repositories;
    exports com.saber.demojavafx.model;
    exports com.saber.demojavafx.dto;
}