package ru.alexpetrik.todolist.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.alexpetrik.todolist.MainApp;
import ru.alexpetrik.todolist.model.TODOList;
import ru.alexpetrik.todolist.model.TypeNote;
import ru.alexpetrik.todolist.util.DateUtil;

public class TODOListEditorController {

    private MainApp mainApp;

    @FXML
    private TextField dateField;
    @FXML
    private DatePicker dateDeadlineField;
    @FXML
    private TextField captionField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private GridPane gpRoot;
    @FXML
    private GridPane gpDeadline;

    private Stage dialogStage;
    private TODOList todoList;
    private boolean okClicked = false;

    @FXML
    private void initialize() {

        choiceBox.setItems(TypeNote.getArrayListTypeNote());
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue == TypeNote.TASKNOTE_TYPE){
                    gpRoot.getRowConstraints().get(2).setMaxHeight(30);
                    gpDeadline.setVisible(true);
                } else {
                    gpRoot.getRowConstraints().get(2).setMaxHeight(0);
                    gpDeadline.setVisible(false);
                    dateDeadlineField.setValue(DateUtil.parse("0"));
                }
            }
        });

    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTODOList(TODOList tempTodoList) {

        this.todoList = tempTodoList;
        captionField.setText(todoList.getCaptionList());
        descriptionArea.setText(todoList.getDescriptionList());
        dateField.setText(DateUtil.formate(todoList.getDateList()));
        dateField.setPromptText("dd.MM.yyyy");
        choiceBox.setValue(tempTodoList.getTypeNote());
        if (todoList.dateDeadlineProperty() != null)
            dateDeadlineField.setValue(todoList.getDateDeadline());

     }

    public void onActionCancel() {
        dialogStage.close();
    }

    public void onActionSave() {

        if (isInputValid()){
             todoList.setCaptionList(captionField.getText());
             todoList.setDescriptionList(descriptionArea.getText());
             todoList.setDateList(DateUtil.parse(dateField.getText()));
             todoList.setTypeNote((TypeNote) choiceBox.getValue());
             todoList.setDateDeadline(dateDeadlineField.getValue());

             okClicked = true;
             dialogStage.close();
        }

    }

    private boolean isInputValid(){
        String msgError = "";

        if (dateField.getText() == null || dateField.getText().length() == 0)
            msgError += "Не введена дата!";
        else {
            if (!DateUtil.validDate(dateField.getText()))
                msgError += "Не правильная дата";
        }

        if (captionField.getText() == null || captionField.getText().length() == 0)
            msgError += "Не введен заголовок!";

        if (choiceBox.getValue() == TypeNote.TASKNOTE_TYPE && dateDeadlineField.getValue() == null)
            msgError += "Для задачи введите срок выполнения!";

        if (msgError.length() == 0)
            return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, msgError);
            alert.initOwner(dialogStage);
            alert.setHeaderText("Пожалуйста исправьте ошибки!");
            alert.setTitle("Ошибка!");

            alert.showAndWait();
            return false;
        }
    }

}
