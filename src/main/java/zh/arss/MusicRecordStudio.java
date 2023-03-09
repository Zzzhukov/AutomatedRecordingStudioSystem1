package zh.arss;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import zh.arss.controller.Controller;

import java.io.IOException;

public class MusicRecordStudio extends Application {
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/musicStudioMain.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Controller.setStage(stage);
        Controller.setShowStage(fxmlLoader);
    }

    public static void main(String[] args) {
        launch();
    }
}
