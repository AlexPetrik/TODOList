package ru.alexpetrik.todolist.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ru.alexpetrik.todolist.MainApp;
import ru.alexpetrik.todolist.model.TODOList;
import ru.alexpetrik.todolist.model.TypeNote;
import ru.alexpetrik.todolist.util.DateUtil;

import java.time.LocalDate;

public class TODOListOverviewController {

    private MainApp mainApp;

    @FXML
    private Label descriptionLabel;
    @FXML
    private Label captionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label typeTaskLabel;
    @FXML
    private Label dateDeadlineLabel;

    @FXML
    private TableView<TODOList> todoTable;
    @FXML
    private TableColumn<TODOList, LocalDate> dateColumn;
    @FXML
    private TableColumn<TODOList, String> captionColumn;
    @FXML
    private GridPane gpRoot;
    @FXML
    private GridPane gpDeadline;

    public TODOListOverviewController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        todoTable.setItems(mainApp.getTodoListsData());
    }

    @FXML
    private void initialize(){
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateListProperty());
        captionColumn.setCellValueFactory(cellData -> cellData.getValue().captionListProperty());

        showTODODetails(null);

        todoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTODODetails(newValue));
    }

    private void showTODODetails(TODOList todoList) {
        if (todoList != null){
            dateLabel.setText(DateUtil.formate(todoList.getDateList()));
            captionLabel.setText(todoList.getCaptionList());
            descriptionLabel.setText(todoList.getDescriptionList());
            typeTaskLabel.setText(todoList.getTypeNote().toString());
            if (todoList.dateDeadlineProperty().getValue() != null) {
                dateDeadlineLabel.setText(todoList.getDateDeadline().toString());
                if (todoList.getTypeNote() == TypeNote.TASKNOTE_TYPE) {
                    gpRoot.getRowConstraints().get(1).setMaxHeight(30);
                    gpDeadline.setVisible(true);
                } else {
                    gpRoot.getRowConstraints().get(1).setMaxHeight(0);
                    gpDeadline.setVisible(false);
//                    todoList.setDateDeadline(DateUtil.parse("0"));
                    dateDeadlineLabel.setText("");
                }
            }else {
                gpRoot.getRowConstraints().get(1).setMaxHeight(0);
                gpDeadline.setVisible(false);
//                todoList.setDateDeadline(DateUtil.parse("0"));
                dateDeadlineLabel.setText("");
            }
       } else {
            dateLabel.setText("");
            captionLabel.setText("");
            descriptionLabel.setText("");
            typeTaskLabel.setText("");
            dateDeadlineLabel.setText("");
        }
    }

    @FXML
    public void onActionNew() {

        TODOList tempTodoList = new TODOList();

        tempTodoList.setDateList(LocalDate.now());
        tempTodoList.setTypeNote(TypeNote.NOTE_TYPE);

        boolean okClicked = mainApp.showTODOEditDialog(tempTodoList);

        if (okClicked)
            mainApp.getTodoListsData().add(tempTodoList);
    }

    @FXML
    public void onActionDelete() {
        int selectedIndex = todoTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            todoTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Не выбрана задача");
            alert.setContentText("Пожалуйста, выберите задачу в таблице");
            alert.initOwner(mainApp.getPrimaryStage());

            alert.showAndWait();
        }
    }

    @FXML
    public void onActionEdit() {

        TODOList todoList = todoTable.getSelectionModel().getSelectedItem();

        if (todoList != null){

            boolean okClicked = mainApp.showTODOEditDialog(todoList);
            if (okClicked)
                showTODODetails(todoList);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Не выбрана задача");
            alert.setContentText("Пожалуйста, выберите задачу в таблице");
            alert.initOwner(mainApp.getPrimaryStage());

            alert.showAndWait();
        }
    }

    // TODO: 13.04.2017 добавить действие для кнопки "О программе".
}
