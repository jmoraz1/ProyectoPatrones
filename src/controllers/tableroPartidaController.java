package controllers;
import Entities.Casilla;
import Entities.Ficha;
import Entities.Jugador;
import Entities.Tablero;
import MainController.MainController;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.Observer.Observador;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaNormal;
import Patterns.Prototype.CasillaQuerubin;
import Patterns.Prototype.CasillaZorvan;
import clasesUI.EjeXYCasilla;
import clasesUI.ElementoTabla;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.ControladorPrueba;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class tableroPartidaController implements Initializable,Observador  {

    public MainController mc = new MainController();

    public ArrayList<ElementoTabla> arregloInfoElementos = new ArrayList<>();

    public ArrayList<EjeXYCasilla> coordenadasCasilla = new ArrayList<EjeXYCasilla>();

    public ArrayList<EjeXYCasilla> coordenadasCasillaFicha = new ArrayList<EjeXYCasilla>();

    public int valorDadoMovimeinto = 0;

    public int valorDadoAtaque= 0;

    public ControladorPrueba controladorTablero;



    @FXML
    private Image imgDelImageViewDado;
    @FXML
    private Label lblDado;
    @FXML
    private ImageView imgViewDado;
    @FXML
    private ImageView imgViewDadoAtaque;
    @FXML
    private Label lblP1;
    @FXML
    private Label lblP2;
    @FXML
    private Label lblP3;
    @FXML
    private Label lblP4;
    @FXML
    private TextField txtJugadorEnTurno;
    @FXML
    private Button btnTirarDadoAtaque;
    @FXML
    private Button btnTirarDado;
    @FXML
    private ImageView imgP1;
    @FXML
    private ImageView imgP2;
    @FXML
    private ImageView imgP3;
    @FXML
    private ImageView imgP4;
    @FXML
    private AnchorPane anchorPaneTablero;
    @FXML
    private ImageView flechaTirarDado;
    @FXML
    private ImageView flechaIniciarPartida;
    @FXML
    private Button btnIniciarTodoTablero;

    private static Tablero partida;
    private static Jugador jugadorTurno;
    private static Jugador[] jugadoresPartida;
    private ImageView fichaP1 = new ImageView();
    private ImageView fichaP2 = new ImageView();
    private ImageView fichaP3 = new ImageView();
    private ImageView fichaP4 = new ImageView();
    private int numFicha = 1;
    private int cantidadJugadores = 0;
    private int jugadorNuevaPosicion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgViewDado.setVisible(true);
        btnTirarDado.setVisible(true);
        btnTirarDado.setDisable(true);
        imgViewDadoAtaque.setVisible(false);
        btnTirarDadoAtaque.setVisible(false);
        flechaTirarDado.setVisible(false);
        iniciarValoresCoordenadas();
        txtJugadorEnTurno.setDisable(true);
    }

    public void iniciarTodoTablero(ActionEvent event) throws IOException{
        colocarNombres();
        //controladorTablero.admin
        btnTirarDado.setDisable(false);
        partida = mc.NuevaPartida(controladorTablero.arregloJugadores);
        cantidadJugadores = controladorTablero.arregloJugadores.size();
        //añadiendo este controller como observador de las casillas
        partida.observarCasillas(this);
        colocarCasillasEspeciales();
        txtJugadorEnTurno.setText(controladorTablero.arregloJugadores.get(0).getNombre());
        jugadorTurno  = partida.turno;
        jugadoresPartida = partida.jugadores;
        flechaIniciarPartida.setVisible(false);
        flechaTirarDado.setVisible(true);
        btnIniciarTodoTablero.setDisable(true);

    }

    public void cambiarTurno(){
        jugadorTurno  = mc.obtenerTurno();
        txtJugadorEnTurno.setText(jugadorTurno.getNombre());
        switch (numFicha){
            case 1:
                numFicha = 2;
                break;
            case 2:
                if(cantidadJugadores == 2){
                    numFicha = 1;
                }else{
                    numFicha = 3;
                }
                break;
            case 3:
                if(cantidadJugadores == 3){
                    numFicha = 1;
                }else{
                    numFicha = 4;
                }
                break;
            case 4:
                numFicha = 1;
                break;
            default:
                break;
        }

    }

    public void gestionarTurno(ActionEvent event) throws IOException{
        //VALIDAR QUE LA POSICIÓN ACTUAL DEL JUGADOR NO SEA STONE Y DE SERLO V
        // ER SI YA LE GANÓ AL STONE PARA PODER TIRAR DADO DE MOVIMIENTO
        int posicionActual = mc.obtenerPosicionJugador(jugadorTurno.ficha);

        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDado.setImage(imgDadoGirandoNumero);
        String urlImgDadoNumerico= "";
        int resultadoDadoMovimiento = mc.obtenerMovimiento();
        switch (resultadoDadoMovimiento){
            case 1:
                urlImgDadoNumerico = "/imgs/dadoUno.jpg";
                break;
            case 2:
                urlImgDadoNumerico = "/imgs/dadoDos.jpg";
                break;
            case 3:
                urlImgDadoNumerico = "/imgs/dadoTres.jpg";
                break;
            case 4:
                urlImgDadoNumerico = "/imgs/dadoCuatro.jpg";
                break;
            case 5:
                urlImgDadoNumerico = "/imgs/dadoCinco.jpg";
                break;
            case 6:
                urlImgDadoNumerico = "/imgs/dadoSeis.jpg";
                break;
        }
        Image imgDadoNum = new Image(urlImgDadoNumerico);
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), e -> imgViewDado.setImage(imgDadoNum)));
        timeline.play();
        dialogoMoverse(resultadoDadoMovimiento);

        jugadorNuevaPosicion = mc.obtenerNuevaPosicionFicha(resultadoDadoMovimiento);
        ImageView fichaJugadorIv = null;
        switch (numFicha){
            case 1:
                fichaJugadorIv = fichaP1;
                break;
            case 2:
                fichaJugadorIv = fichaP2;
                break;
            case 3:
                fichaJugadorIv = fichaP3;
                break;
            case 4:
                fichaJugadorIv = fichaP4;
                break;
        }
        fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(jugadorNuevaPosicion).getLayoutY());
        fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(jugadorNuevaPosicion).getLayoutX());

        Ficha fichaActiva=mc.obtenerFicha();
        posicionActual=mc.obtenerPosicionJugador(fichaActiva);
        mc.moverFicha(posicionActual,jugadorNuevaPosicion,fichaActiva);
         //Debo setear nuevo jugador en turno
        
        cambiarTurno();
    }

    public void iniciarValoresCoordenadas(){
        double ejeX = 0;
        double ejeY = 0;
        double ejeYF = 0;
        for(int i = 1; i <= 100; i++){
         if(i == 1){
             EjeXYCasilla ejeCasilla = new EjeXYCasilla(i, 183, 165);
             EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, 183, 142);
             coordenadasCasilla.add(ejeCasilla);
             coordenadasCasillaFicha.add(ejeCasillaF);
         }else if( i == 100){
             EjeXYCasilla ejeCasilla = new EjeXYCasilla(i, 1130, 555);
             coordenadasCasilla.add(ejeCasilla);
             EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, 1130, 530);
             coordenadasCasillaFicha.add(ejeCasillaF);
         }else{

             if( i>= 2 && i<= 15){
                 ejeY = 165;
                 ejeYF = 142;
             }else if(i >= 16 && i <= 29){
                 ejeY = 230;
                 ejeYF = 206;
             }else if(i >= 30 && i <= 43){
                 ejeY = 295;
                 ejeYF = 274;
             }else if(i >= 44 && i <= 57){
                 ejeY = 360;
                 ejeYF = 338;
             }else if(i >= 58 && i <= 71){
                 ejeY = 425;
                 ejeYF = 401;
             }else if(i >= 72 && i <=85){
                 ejeY = 490;
                 ejeYF = 472;
             }else if(i >= 86 && i <= 99){
                 ejeY = 555;
                 ejeYF = 530;
             }
                 switch (i){
                     case 2: case 29: case 30: case 57: case 58: case 72: case 86:
                         ejeX = 258;
                         break;
                     case 3: case 28: case 31: case 56: case 59: case 73: case 87:
                         ejeX = 318;
                         break;
                     case 4: case 27: case 32: case 55: case 60: case 74: case 88:
                         ejeX = 378;
                         break;
                     case 5: case 26: case 33: case 54: case 61: case 75: case 89:
                         ejeX = 436;
                         break;
                     case 6: case 25: case 34: case 53: case 62: case 76: case 90:
                         ejeX = 499;
                         break;
                     case 7: case 24: case 35: case 52: case 63: case 77: case 91:
                         ejeX = 557;
                         break;
                     case 8: case 23: case 36: case 51: case 64: case 78: case 92:
                         ejeX = 615;
                         break;
                     case 9: case 22: case 37: case 50: case 65: case 79: case 93:
                         ejeX = 678;
                        break;
                     case 10: case 21: case 38: case 49: case 66: case 80: case 94:
                         ejeX = 737;
                         break;
                     case 11: case 20: case 39: case 48: case 67: case 81: case 95:
                         ejeX = 796;
                         break;
                     case 12: case 19: case 40: case 47: case 68: case 82: case 96:
                         ejeX = 857;
                         break;
                     case 13: case 18: case 41: case 46: case 69: case 83: case 97:
                         ejeX = 918;
                         break;
                     case 14: case 17: case 42: case 45: case 70: case 84: case 98:
                         ejeX = 977;
                         break;
                     case 15: case 16: case 43: case 44: case 71: case 85: case 99:
                         ejeX = 1035;
                         break;
                 }

             EjeXYCasilla ejeCasilla = new EjeXYCasilla(i, ejeX, ejeY);
             EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, ejeX, ejeYF);
             coordenadasCasilla.add(ejeCasilla);
             coordenadasCasillaFicha.add(ejeCasillaF);

         }
        }
    }

    public void colocarCasillasEspeciales(){

        ArrayList<Integer> posicionesDiablillos = mc.obtenerDiablillos();
        ArrayList<Integer> posicionesStones = mc.obtenerStones();
        ArrayList<Integer> posicionesQuerubines = mc.obtenerQuerubines();

        for (int i = 0; i < posicionesDiablillos.size(); i++){
            anchorPaneTablero.getChildren().add(circleDiablillo(posicionesDiablillos.get(i)));
        }

        for (int i = 0; i < posicionesStones.size(); i++){
            anchorPaneTablero.getChildren().add(circleStone(posicionesStones.get(i)));
        }

        for (int i = 0; i < posicionesQuerubines.size(); i++){
            anchorPaneTablero.getChildren().add(circleQuerubin(posicionesQuerubines.get(i)));
        }

    }

    public Circle circleQuerubin(int posicion){
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(84, 197, 219));
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setRadius(9.0);
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public Circle circleDiablillo(int posicion){
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(204, 39, 22));
        cQuerubin.setRadius(9.0);
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public Circle circleStone(int posicion){
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(22, 204, 64));
        cQuerubin.setRadius(9.0);
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public void turnoDadoAtaque(){
        imgViewDado.setVisible(false);
        btnTirarDado.setVisible(false);
        imgViewDadoAtaque.setVisible(true);
        imgViewDadoAtaque.setLayoutX(34);
        imgViewDadoAtaque.setLayoutY(211);
        btnTirarDadoAtaque.setVisible(true);
        btnTirarDadoAtaque.setLayoutX(26);
        btnTirarDadoAtaque.setLayoutY(296);
    }

    public void turnoDadoMovimiento(){
        imgViewDado.setVisible(true);
        btnTirarDado.setVisible(true);
        imgViewDadoAtaque.setVisible(false);
        btnTirarDadoAtaque.setVisible(false);
    }

    public void colocarNombres(){
        ArrayList<String> listaJugadores = controladorTablero.nombreJugadores;
        Image imgfP1 = new Image("/imgs/FICHAS-02.png");
        fichaP1.setImage(imgfP1);
        fichaP1.setLayoutY(coordenadasCasillaFicha.get(0).getLayoutY());
        fichaP1.setLayoutX(coordenadasCasillaFicha.get(0).getLayoutX());
        fichaP1.setFitWidth(45);
        fichaP1.setFitHeight(45);
        Image imgfP2 = new Image("/imgs/FICHAS-04.png");
        fichaP2.setImage(imgfP2);
        fichaP2.setLayoutY(coordenadasCasillaFicha.get(0).getLayoutY());
        fichaP2.setLayoutX(coordenadasCasillaFicha.get(0).getLayoutX());
        fichaP2.setFitWidth(45);
        fichaP2.setFitHeight(45);
        Image imgfP3 = new Image("/imgs/FICHAS-05.png");
        fichaP3.setImage(imgfP3);
        fichaP3.setLayoutY(coordenadasCasillaFicha.get(0).getLayoutY());
        fichaP3.setLayoutX(coordenadasCasillaFicha.get(0).getLayoutX());
        fichaP3.setFitWidth(45);
        fichaP3.setFitHeight(45);
        Image imgfP4 = new Image("/imgs/FICHAS-03.png");
        fichaP4.setImage(imgfP4);
        fichaP4.setLayoutY(coordenadasCasillaFicha.get(0).getLayoutY());
        fichaP4.setLayoutX(coordenadasCasillaFicha.get(0).getLayoutX());
        fichaP4.setFitWidth(45);
        fichaP4.setFitHeight(45);
        switch (listaJugadores.size()){
            case 1:
                lblP1.setText(lblP1.getText()+" "+ listaJugadores.get(0));
                lblP2.setVisible(true);
                lblP2.setText(lblP2.getText()+" Computadora");
                lblP3.setVisible(false);
                lblP4.setVisible(false);
                imgP2.setVisible(true);
                imgP3.setVisible(false);
                imgP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                break;
            case 2:
                lblP1.setText(lblP1.getText() + " "+listaJugadores.get(0));
                lblP2.setText(lblP2.getText() +" "+ listaJugadores.get(1));
                lblP3.setVisible(false);
                lblP4.setVisible(false);
                imgP3.setVisible(false);
                imgP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                break;
            case 3:
                lblP1.setText(lblP1.getText() + " "+listaJugadores.get(0));
                lblP2.setText(lblP2.getText() +" "+ listaJugadores.get(1));
                lblP3.setText(lblP3.getText() + " "+listaJugadores.get(2));
                lblP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                anchorPaneTablero.getChildren().add(fichaP3);

                break;
            case 4:
                lblP1.setText(lblP1.getText() +" "+ listaJugadores.get(0));
                lblP2.setText(lblP2.getText() +" "+ listaJugadores.get(1));
                lblP3.setText(lblP3.getText() +" "+ listaJugadores.get(2));
                lblP4.setText(lblP4.getText() + " "+listaJugadores.get(3));
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                anchorPaneTablero.getChildren().add(fichaP3);
                anchorPaneTablero.getChildren().add(fichaP4);
                break;
        }
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

    public int tirarDado() throws IOException {
        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDado.setImage(imgDadoGirandoNumero);
        String urlImgDadoNumerico= "";
        int valorDadoMovimeinto = mc.obtenerMovimiento();
        switch (valorDadoMovimeinto){
            case 1:
                urlImgDadoNumerico = "/imgs/dadoUno.jpg";
                break;
            case 2:
                urlImgDadoNumerico = "/imgs/dadoDos.jpg";
                break;
            case 3:
                urlImgDadoNumerico = "/imgs/dadoTres.jpg";
                break;
            case 4:
                urlImgDadoNumerico = "/imgs/dadoCuatro.jpg";
                break;
            case 5:
                urlImgDadoNumerico = "/imgs/dadoCinco.jpg";
                break;
            case 6:
                urlImgDadoNumerico = "/imgs/dadoSeis.jpg";
                break;
        }
        Image imgDadoNum = new Image(urlImgDadoNumerico);
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(2000), e -> imgViewDado.setImage(imgDadoNum)));
        timeline.play();

        return valorDadoMovimeinto;

    }

    public void tirarDadoAtaque(ActionEvent event) throws IOException {
        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDadoAtaque.setImage(imgDadoGirandoNumero);

        String urlImgDadoNumerico= "";
        int valorDadoAtaque = (int) Math.floor(Math.random()*6+1);
        switch (valorDadoAtaque){
            case 1:
                urlImgDadoNumerico = "/imgs/atacaUnPersonajeTriada.jpg";
                break;
            case 2:
                urlImgDadoNumerico = "/imgs/atacaUnPersonaActivaPoderEspecial.jpg";
                break;
            case 3:
                urlImgDadoNumerico = "/imgs/atacanDosPersonajesTriada.jpg";
                break;
            case 4:
                urlImgDadoNumerico = "/imgs/atacanDosPersonajesPoderEspecial.jpg";
                break;
            case 5:
                urlImgDadoNumerico = "/imgs/atacanTresPersonajesTriada.jpg";
                break;
            case 6:
                urlImgDadoNumerico = "/imgs/dadoAtacanTodosDosPoderEspeciales.jpg";
                break;
        }
        Image imgDadoAta = new Image(urlImgDadoNumerico);
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(2000), e -> imgViewDadoAtaque.setImage(imgDadoAta)));
        timeline.play();
    }

    public void iniciarArregloInfoElementos(){
        ElementoTabla eleFuego = new ElementoTabla("Le otorga 5 puntos extra a un personaje de la triada por 2 turnos",
                "Fuego", "Neutral", "Desventaja", "Ventaja", "Desventaja", "Desventaja",
                "Ventaja" );

        ElementoTabla eleAgua = new ElementoTabla("Habilita dado de ataque para tiro extra",
                "Agua", "Ventaja", "Neutral", "Desventaja", "Desventaja", "Ventaja",
                "Desventaja" );

        ElementoTabla elePlanta = new ElementoTabla("Por 2 turnos no deja que un jugador saque más de 3 en su dado de movimientos",
                "Planta", "Desventaja", "Ventaja", "Neutral", "Ventaja", "Desventaja",
                "Desventaja" );

        ElementoTabla eleElectrico = new ElementoTabla("Causa una parálisis que puede evitar que 1 jugador tire el dado de movimiento, este efecto dura 3 turnos",
                "Eléctrico", "Ventaja", "Ventaja", "Desventaja", "Neutral", "Desventaja",
                "Ventaja" );

        ElementoTabla eleRoca = new ElementoTabla("Crea 1 stone en casilla de algún contrincante con desventaja de su triada (Stone 60 pts vida)",
                "Roca", "Ventaja", "Desventaja", "Ventaja", "Ventaja", "Neutral",
                "Desventaja" );

        ElementoTabla eleHielo = new ElementoTabla("Congela a un jugador por turno",
                "Hielo", "Desventaja", "Ventaja", "Ventaja", "Desventaja", "Ventaja",
                "Ventaja" );

        arregloInfoElementos.add(eleFuego);
        arregloInfoElementos.add(eleAgua);
        arregloInfoElementos.add(elePlanta);
        arregloInfoElementos.add(eleElectrico);
        arregloInfoElementos.add(eleRoca);
        arregloInfoElementos.add(eleHielo);
    }

    public void infoElementos(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Elementos");
        alert.setContentText("Información de los elementos/personajes");

        TableView tbl = new TableView();
        TableColumn<String, ElementoTabla> column1 = new TableColumn<>("Poder especial");
        column1.setCellValueFactory(new PropertyValueFactory<>("PoderEspecial"));

        TableColumn<String, ElementoTabla> column2 = new TableColumn<>("Elemento");
        column2.setCellValueFactory(new PropertyValueFactory<>("Elemento"));

        TableColumn<String, ElementoTabla> column3 = new TableColumn<>("Fuego");
        column3.setCellValueFactory(new PropertyValueFactory<>("Fuego"));

        TableColumn<String, ElementoTabla> column4 = new TableColumn<>("Agua");
        column4.setCellValueFactory(new PropertyValueFactory<>("Agua"));

        TableColumn<String, ElementoTabla> column5 = new TableColumn<>("Planta");
        column5.setCellValueFactory(new PropertyValueFactory<>("Planta"));

        TableColumn<String, ElementoTabla> column6 = new TableColumn<>("Eléctrico");
        column6.setCellValueFactory(new PropertyValueFactory<>("Eléctrico"));

        TableColumn<String, ElementoTabla> column7 = new TableColumn<>("Roca");
        column7.setCellValueFactory(new PropertyValueFactory<>("Roca"));

        TableColumn<String, ElementoTabla> column8 = new TableColumn<>("Hielo");
        column8.setCellValueFactory(new PropertyValueFactory<>("Hielo"));

        tbl.getColumns().add(column1);
        tbl.getColumns().add(column2);
        tbl.getColumns().add(column3);
        tbl.getColumns().add(column4);
        tbl.getColumns().add(column5);
        tbl.getColumns().add(column6);
        tbl.getColumns().add(column7);
        tbl.getColumns().add(column8);

        if(arregloInfoElementos.size() == 0){
            iniciarArregloInfoElementos();
        }

        for (ElementoTabla obj : arregloInfoElementos ) {
            tbl.getItems().add(obj);
        }

        alert.getDialogPane().setContent(tbl);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(1100, 400);


        alert.showAndWait();
    }

    public void dialogoZorvan() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Zorvan: " +
                "\n-¡Alto Ahí!" +
                "\nNo hay cabida para los tramposos, dévuelvete "+4+" espacios.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));

        gP.add(txt, 0,0);

        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/zorvan.jpeg");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(400);
        imgZorvan.setFitWidth(300);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 380);

        alert.showAndWait();
    }

    public void dialogoDiablillo() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Diablillo: " +
                "\n-¡Te estaba esperando guerrero!" +
                "\nMala suerte." +
                "\nTe has de devolver 10 casillas.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));

        gP.add(txt, 0,0);

        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/diablillo.jpg");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(400);
        imgZorvan.setFitWidth(300);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 380);

        alert.showAndWait();
    }

    public void dialogoQuerubin() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Querubin: " +
                "\n-¡Bienvenido guerrero!" +
                "\nVeo que eres digno." +
                "\nHas de avanzar 10 casillas.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);

        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/querubina.jpg");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(300);
        imgZorvan.setFitWidth(300);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 380);

        alert.showAndWait();
    }

    public void dialogoStone() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Stone: " +
                "\n-¡Osáis desafiarme!" +
                "\nNo tienes escapatoria." +
                "\nPara avanzar debes vencerme.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);

        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/stone.jpeg");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(300);
        imgZorvan.setFitWidth(300);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(500, 380);

        alert.showAndWait();
    }

    public void dialogoMoverse(int resultadoDado) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Información");
        GridPane gP = new GridPane();
        Text txt = new Text("Avanza: " + resultadoDado +" casillas.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(100, 100);
        alert.showAndWait();
    }

    @Override
    public String update(String value) throws IOException {
        value=value;

        if(!value.equals("Normal")){
            ImageView fichaJugadorIv = null;
            int nuevaPosicion=0;
            switch (numFicha){
                case 1:
                    fichaJugadorIv = fichaP1;
                    break;
                case 2:
                    fichaJugadorIv = fichaP2;
                    break;
                case 3:
                    fichaJugadorIv = fichaP3;
                    break;
                case 4:
                    fichaJugadorIv = fichaP4;
                    break;
            }
            if(value.equals("Diablito")){
                dialogoDiablillo();
                nuevaPosicion = mc.movimientoDiablito();
                Ficha ficha=mc.obtenerFicha();
                int posicionActual=mc.obtenerPosicionJugador(ficha);
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,ficha);

            }else if(value.equals("Querubin")){
                dialogoQuerubin();
                nuevaPosicion = mc.movimientoQuerubin();
                Ficha ficha=mc.obtenerFicha();
                int posicionActual=mc.obtenerPosicionJugador(ficha);
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,ficha);

            }else if(value.equals("Stone")){
                dialogoStone();

            }else if(value.equals("Zorvan")){
                dialogoZorvan();
                int casillasExtras = jugadorNuevaPosicion - 100;
                nuevaPosicion = mc.movimientoZorvan(casillasExtras);
                Ficha ficha=mc.obtenerFicha();
                int posicionActual=mc.obtenerPosicionJugador(ficha);
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,ficha);
            }

        }


        return null;
    }
}
