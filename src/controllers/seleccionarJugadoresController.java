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
import main.ControladorPrueba;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class seleccionarJugadoresController implements Initializable {

    @FXML
    private TextField txtFieldP1;
    @FXML
    private TextField txtFieldP2;
    @FXML
    private TextField txtFieldP3;
    @FXML
    private TextField txtFieldP4;

    private ControladorPrueba control = new ControladorPrueba();

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
    public void regresar(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Regresar");
        alert.setHeaderText("¿Desea regresar a la vista anterior?");
        alert.setContentText("De ser así perderá los datos guardados");
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

    public void iniciarPartida(ActionEvent event) throws IOException{
        boolean validarNombreDiferente = true;
        if(validarCampos()){

            if(!txtFieldP4.isDisable()){
                if(!txtFieldP1.getText().equals(txtFieldP2.getText()) &&
                        !txtFieldP1.getText().equals(txtFieldP3.getText())&&
                        !txtFieldP1.getText().equals(txtFieldP4.getText())&&
                        !txtFieldP2.getText().equals(txtFieldP3.getText())&&
                        !txtFieldP2.getText().equals(txtFieldP4.getText())&&
                        !txtFieldP3.getText().equals(txtFieldP4.getText())){
                    String nombreP1 = txtFieldP1.getText();
                    control.agregarJugadores(nombreP1);
                    String nombreP2 = txtFieldP2.getText();
                    control.agregarJugadores(nombreP2);
                    String nombreP3 = txtFieldP3.getText();
                    control.agregarJugadores(nombreP3);
                    String nombreP4 = txtFieldP4.getText();
                    control.agregarJugadores(nombreP4);

                }else{
                    validarNombreDiferente = false;
                }

            }else if(txtFieldP4.isDisable() && !txtFieldP3.isDisable()){
                if(!txtFieldP1.getText().equals(txtFieldP2.getText())&&
                                !txtFieldP1.getText().equals(txtFieldP3.getText())&&
                                !txtFieldP2.getText().equals(txtFieldP3.getText())){
                    String nombreP1 = txtFieldP1.getText();
                    control.agregarJugadores(nombreP1);
                    String nombreP2 = txtFieldP2.getText();
                    control.agregarJugadores(nombreP2);
                    String nombreP3 = txtFieldP3.getText();
                    control.agregarJugadores(nombreP3);
                }else{
                    validarNombreDiferente = false;
                }

            }else if(txtFieldP3.isDisable() && !txtFieldP2.isDisable()){
                if(!txtFieldP1.getText().equals(txtFieldP2.getText())){
                    String nombreP1 = txtFieldP1.getText();
                    control.agregarJugadores(nombreP1);
                    String nombreP2 = txtFieldP2.getText();
                    control.agregarJugadores(nombreP2);
                }else{
                    validarNombreDiferente = false;
                }

            }else{
                String nombreP1 = txtFieldP1.getText();
                control.agregarJugadores(nombreP1);
                control.agregarJugadores("Computadora");
            }

            if(validarNombreDiferente){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/tableroPartida.fxml"));

                loader.load();
                tableroPartidaController controller = loader.<tableroPartidaController>getController();
                controller.controladorTablero = control;
                Parent root = loader.getRoot();
                Platform.runLater(() -> root.requestFocus());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Laberinto de Zorvan");
                stage.setResizable(false);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }else{
                nombresIguales();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/seleccionarJugadores.fxml"));
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

        }else{
            camposVacios();
        }
    }

    public void p1(ActionEvent event) throws IOException{
        txtFieldP1.setDisable(false);
        txtFieldP2.setDisable(true);
        txtFieldP3.setDisable(true);
        txtFieldP4.setDisable(true);

    }
    public void p2(ActionEvent event) throws IOException{
        txtFieldP1.setDisable(false);
        txtFieldP2.setDisable(false);
        txtFieldP3.setDisable(true);
        txtFieldP4.setDisable(true);
    }
    public void p3(ActionEvent event) throws IOException{
        txtFieldP1.setDisable(false);
        txtFieldP2.setDisable(false);
        txtFieldP3.setDisable(false);
        txtFieldP4.setDisable(true);
    }
    public void p4(ActionEvent event) throws IOException{
        txtFieldP1.setDisable(false);
        txtFieldP2.setDisable(false);
        txtFieldP3.setDisable(false);
        txtFieldP4.setDisable(false);
    }

    @FXML
    public void camposVacios() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Existen campos sin rellenar o la longitud de los nombres es mayor a 11.");
        alert.setContentText("Revise los campos que hacen falta.");
        alert.showAndWait();
    }

    @FXML
    public void nombresIguales() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Nombres iguales");
        alert.setContentText("Revise que los nombres de los jugadores se han distintos.");
        alert.showAndWait();
    }

    public boolean validarCampos(){

        if(!txtFieldP1.isDisable() && "".equals(txtFieldP1.getText()) || txtFieldP1.getText().length()>11){
            return false;
        }
        if(!txtFieldP2.isDisable() && "".equals(txtFieldP2.getText()) || txtFieldP2.getText().length()>11){
            return false;
        }
        if(!txtFieldP3.isDisable() && "".equals(txtFieldP3.getText()) || txtFieldP3.getText().length()>11){
            return false;
        }
        if(!txtFieldP3.isDisable() && "".equals(txtFieldP3.getText()) ||  txtFieldP4.getText().length()>11){
            return false;
        }
        return true;

    }

}
