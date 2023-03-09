package zh.arss.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zh.arss.MusicRecordStudio;

import java.io.IOException;

public class Controller {
    private static Stage stage;
    private static final String PATH_TO_LAYOUT = "/zh/arss/layout/";

    public static void openOtherWindow(String layoutName, Button button) {
        button.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, ImageView imageView) {
        imageView.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, Label label) {
        label.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, TextField textField) {
        textField.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, ComboBox<?> comboBox) {
        comboBox.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openNewWindow(String layoutName) {
        String path = PATH_TO_LAYOUT + layoutName + ".fxml";
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(path));
        try {
            loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        setShowStage(loader);
    }

    public static void setShowStage(FXMLLoader loader) {
        stage.setTitle("Music Studio \"Valentine Skarab\"");
        stage.getIcons().add(new Image(String.valueOf(MusicRecordStudio.class.getResource("image/icon.png"))));
        Parent root = loader.getRoot();
        Scene scene = new Scene(root, 1000, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }
}
