module com.saber.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.persistence;
    requires lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires persian.date.time;


    opens com.saber.demojavafx to javafx.fxml;
    opens com.saber.demojavafx.model;
    opens com.saber.demojavafx.services;
    opens com.saber.demojavafx.repositories;
    opens com.saber.demojavafx.dto to javafx.base;
    exports com.saber.demojavafx;
    exports com.saber.demojavafx.controllers;
    exports com.saber.demojavafx.services;
    exports com.saber.demojavafx.repositories;
    opens com.saber.demojavafx.controllers to javafx.fxml;
}