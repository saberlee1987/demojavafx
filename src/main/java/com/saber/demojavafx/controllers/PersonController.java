package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import com.saber.demojavafx.services.impl.PersonServiceImpl;
import com.saber.demojavafx.utils.Utilities;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @FXML
    private Pagination pagination;
    @FXML
    private Button buttonSearch;
    @FXML
    private TextField searchField;
    private static final int PAGE_SIZE = 10;

    @FXML
    private Button buttonNewPerson;
    private final PersonService personService;
    public PersonController(EntityManagerFactory entityManagerFactory) {
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
        buttonNewPerson.setOnAction(this::showNewPersonPage);
        buttonSearch.setOnAction(this::searchField);
        searchField.setOnAction(this::searchField);

        Long countPersons = personService.countPersons();
        int pageCount = (int) Math.ceil((double) countPersons / PAGE_SIZE);
        if ( pageCount == 0) pageCount = 1;
        pagination.setPageCount(pageCount);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int page = newValue.intValue();
                    //System.out.println("page newValue ===> "+page);
                    loadPersonData(page);
                }
        );
        loadPersonData(pagination.getCurrentPageIndex());
    }

    private void searchField(ActionEvent event) {
        int pageIndex = pagination.getCurrentPageIndex();
        loadPersonData(pageIndex);
    }

    private void showNewPersonPage(ActionEvent event) {
        openNewPage();
    }

    private void openNewPage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/saber/demojavafx/person-new.fxml")
            );
            PersonNewActionController personNewController = new PersonNewActionController(personService);
            loader.setController(personNewController);
            Parent load = loader.load();
            personNewController.initialize();
            Stage stage = new Stage();
            String css = Objects.requireNonNull(getClass()
                            .getResource("/css/application.css"))
                    .toExternalForm();
            stage.setTitle("اطلاعات شخص جدید");
            Scene scene = new Scene(load,700,600);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setOnHidden(event -> refreshPagination());
            stage.show();
        }catch (Exception e) {
            System.err.println(e.getMessage());
            Utilities.showDialog("error in load person new page", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadPersonData(int page) {
        String searchFieldText = searchField.getText();
        List<PersonDto> persons = personService.getAllPersons(searchFieldText,page,PAGE_SIZE);
        personTable.getItems().setAll(persons);
    }
    private void refreshPagination() {
        long totalPersons = personService.countPersons();
        int pageCount = totalPersons == 0 ? 1: (int) Math.ceil((double) totalPersons / PAGE_SIZE);
        System.out.println("pageCount ===> "+pageCount);
        pagination.setPageCount(pageCount);
        int currentPage =pagination.getCurrentPageIndex();
        System.out.println("currentPage ===> "+pageCount);
        if (currentPage >= pageCount) {
            pagination.setCurrentPageIndex(pageCount - 1);
        } else {
            loadPersonData(currentPage);
        }
    }

    private void createActionButtons() {
        actionColumn.setCellFactory(column-> new TableCell<>() {
            private final Button detailButton = new Button("جزئیات");
            private final Button editButton = new Button("ویرایش");
            private final Button deleteButton = new Button("حذف");
            private final HBox container = new HBox(10,detailButton,editButton,deleteButton);
            {
                detailButton.setOnAction(event-> {
                    PersonDto personDto = getTableView().getItems().get(getIndex());
                    openViewOrEditPage(personDto.getId(),true);
                });

                editButton.getStyleClass().add("edit-button");
                editButton.setOnAction(event -> {
                    PersonDto personDto = getTableView().getItems().get(getIndex());
                    openViewOrEditPage(personDto.getId(),false);
                });
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setOnAction(event -> {
                    Optional<ButtonType> optionalDelete = Utilities.showDialog("حذف شخص",
                            "آیا از حذف شخص مورد نظر مطئمن هستید؟",
                            Alert.AlertType.CONFIRMATION);
                    if (optionalDelete.isPresent() && optionalDelete.get().equals(ButtonType.OK)) {
                        PersonDto personDto = getTableView().getItems().get(getIndex());
                        try {
                            personService.getPersonById(personDto.getId());
                            personService.deletePersonById(personDto.getId());
                            Utilities.showDialog("عملیات حذف موفق","عملیات حذف شخص با موفقیت انجام شد", Alert.AlertType.INFORMATION);
                            refreshPagination();
                        } catch (Exception e) {
                            Utilities.showDialog("خطا در حذف شخص", e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
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

    private void openViewOrEditPage(Integer personId,boolean isView) {
        try {
            String page="";
            if (isView) {
                page = "/com/saber/demojavafx/person-view.fxml";
            } else {
                page = "/com/saber/demojavafx/person-edit.fxml";
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent load=null;
            if (isView) {
                PersonViewActionController personViewController = new PersonViewActionController(personService);
                loader.setController(personViewController);
                load = loader.load();
                personViewController.loadData(personId);
            } else {
                PersonEditActionController personEditController = new PersonEditActionController(personService);
                loader.setController(personEditController);
                load = loader.load();
                personEditController.loadData(personId);
            }

            Stage stage = new Stage();
            String css = Objects.requireNonNull(getClass()
                            .getResource("/css/application.css"))
                    .toExternalForm();
            stage.setTitle("جزئیات مشتری");
            double sceneWidth = Utilities.getScreenWidthByPercent();
            double sceneHeight = Utilities.getScreenHeightByPercent();
            if (!isView) {
                sceneWidth = sceneWidth * .55;
                sceneHeight = sceneHeight * .90;
            }

            System.out.println("sceneWidth ===> "+sceneWidth);
            System.out.println("sceneHeight ===> "+sceneHeight);
            Scene scene = new Scene(load, sceneWidth, sceneHeight);
            scene.getStylesheets().add(css);
            stage.setScene(scene);
            //stage.setResizable(false);
            stage.setOnHidden(event -> refreshPagination());
            stage.show();
        } catch (Exception e) {
            Utilities.showDialog("error in load view page", e.getMessage(), Alert.AlertType.ERROR);
            System.err.println(e.getMessage());
        }
    }
}
