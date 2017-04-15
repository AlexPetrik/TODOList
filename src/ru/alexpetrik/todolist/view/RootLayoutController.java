package ru.alexpetrik.todolist.view ;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import ru.alexpetrik.todolist.MainApp;

import java.io.File;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void onActionNew() {
        mainApp.getTodoListsData().clear();
        mainApp.setTODOListFilePath(null);
    }

    @FXML
    public void onActionOpen() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "XML-файл (.xml)", "*.xml");

        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null)
            mainApp.loadTODOListFromFile(file);
    }

    @FXML
    public void onActionSave() {

        File file = mainApp.getTODOListFilePath();
        if (file != null)
            mainApp.saveTODODataToFile(file);
        else
            onActionSaveAs();

    }

    @FXML
    public void onActionSaveAs() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML-файл (.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null){
            if (!file.getPath().endsWith(".xml")){
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveTODODataToFile(file);
        }

    }

    @FXML
    public void onActionClose() {
        System.exit(0);
    }
}
