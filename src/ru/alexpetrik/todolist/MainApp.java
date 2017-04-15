package ru.alexpetrik.todolist;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.alexpetrik.todolist.model.TODOList;
import ru.alexpetrik.todolist.model.TODOListWrapper;
import ru.alexpetrik.todolist.view.RootLayoutController;
import ru.alexpetrik.todolist.view.TODOListEditorController;
import ru.alexpetrik.todolist.view.TODOListOverviewController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<TODOList> todoListsData = FXCollections.observableArrayList();

    public MainApp() {
        todoListsData.add(new TODOList("Проверить решение"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TODO List");

        this.primaryStage.getIcons().add(new Image("file:res/images/TODOList.png"));
        initRootLayout();

        showTODOListOverview();

    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        File file = getTODOListFilePath();
//        if (file != null)
//            loadTODOListFromFile(file);
    }

    private void showTODOListOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TODOListOverview.fxml"));
            AnchorPane todoListOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(todoListOverview);

            TODOListOverviewController todoListOverviewController = loader.getController();
            todoListOverviewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showTODOEditDialog(TODOList tempTodoList) {

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TODOListEditor.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.getIcons().add(new Image("file:res/images/TODOListEdit.png"));

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TODOListEditorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTODOList(tempTodoList);

            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public File getTODOListFilePath(){
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null)
            return new File(filePath);
        else
            return null;
    }

    public void setTODOListFilePath(File file){
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null){
            prefs.put("filePath" , file.getPath());
            primaryStage.setTitle("TODO List " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("TODO List");
        }

    }

    public void loadTODOListFromFile(File file){

        try {
            JAXBContext context = JAXBContext.newInstance(TODOListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            TODOListWrapper wrapper = (TODOListWrapper) um.unmarshal(file);

            todoListsData.clear();
            todoListsData.addAll(wrapper.getTodoLists());

            setTODOListFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Ошибка загрузки");
            alert.setContentText("Ошибка загрузки файла:\n" + file.getName());
            alert.initOwner(primaryStage);

            alert.showAndWait();
        }

    }

    public void saveTODODataToFile(File file){

        try {
            JAXBContext context = JAXBContext.newInstance(TODOListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            TODOListWrapper todoListWrapper = new TODOListWrapper();
            todoListWrapper.setTodoLists(todoListsData);

            m.marshal(todoListWrapper, file);

            setTODOListFilePath(file);


        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Ошибка сохранения");
            alert.setContentText("Ошибка сохранения файла:\n" + file.getName());
            alert.initOwner(primaryStage);

            alert.showAndWait();

        }
    }

    public ObservableList<TODOList> getTodoListsData() {
        return todoListsData;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
