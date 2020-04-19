package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;




public class resultadoPartidaController implements Initializable {

    @FXML
    public TextField txtFieldGanador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void salir(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir");
        alert.setHeaderText("¿Desea salir del juego?");
        Optional<ButtonType> opcion =  alert.showAndWait();
        if(opcion.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    public void inicioLaberinto(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Regresar");
        alert.setHeaderText("¿Desea regresar al inicio?");
        Optional<ButtonType> opcion =  alert.showAndWait();
        if(opcion.get() == ButtonType.OK){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/inicioLaberintoZorvan.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Platform.runLater(() -> root.requestFocus());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Laberinto de Zorvan");
            stage.setResizable(false);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
