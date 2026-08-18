package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import com.saber.demojavafx.services.impl.PersonServiceImpl;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class PersonController {
    @FXML
    private TableView<PersonDto> personTable;
    @FXML
    private TableColumn<PersonDto,Integer> idColumn;
    @FXML
    private TableColumn<PersonDto,String> firstnameColumn;
    @FXML
    private TableColumn<PersonDto,String> lastnameColumn;
    @FXML
    private TableColumn<PersonDto,Integer> ageColumn;
    @FXML
    private TableColumn<PersonDto,String> mobileColumn;
    @FXML
    private TableColumn<PersonDto,String> nationalCodeColumn;
    @FXML
    private TableColumn<PersonDto,String> emailColumn;
    @FXML
    private TableColumn<PersonDto,Void> actionColumn;

    private final PersonService personService;

    private final EntityManagerFactory entityManagerFactory;
    public PersonController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        personService = new PersonServiceImpl(entityManagerFactory);
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nationalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("nationalCode"));

        personTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        createActionButtons();

        loadPersonData();
    }

    private void loadPersonData() {
        List<PersonDto> persons = personService.getAllPersons();
        personTable.getItems().setAll(persons);
    }

    private void createActionButtons() {
        actionColumn.setCellFactory(column-> new TableCell<>() {
            private final Button detailButton = new Button("جزئیات");
            private final Button editButton = new Button("ویرایش");
            private final HBox container = new HBox(10,detailButton,editButton);

            {
                detailButton.setOnAction(event-> {
                    PersonDto personDto = getTableView().getItems().get(getIndex());
                    System.out.println("detail button ===> "+personDto);
                    openViewPage(personDto.getId());
                });

                editButton.setOnAction(event -> {
                    PersonDto personDto = getTableView().getItems().get(getIndex());
                    System.out.println("edit button ===> "+personDto);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
    }

    private void openViewPage(Integer personId) {
       try {
           FXMLLoader loader = new FXMLLoader(
                   getClass().getResource("/com/saber/demojavafx/person-view.fxml")
           );
           PersonViewController personViewController = new PersonViewController(personService);
           loader.setController(personViewController);
           Parent load = loader.load();
           personViewController.loadData(personId);
           Stage stage = new Stage();
           String css = Objects.requireNonNull(getClass()
                           .getResource("/css/application.css"))
                   .toExternalForm();
           stage.setTitle("جزئیات مشتری");
           Scene scene = new Scene(load,1500,700);
           scene.getStylesheets().add(css);
           stage.setScene(scene);
           stage.setResizable(false);
           stage.show();
       }catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getMessage());
       }
    }
}
