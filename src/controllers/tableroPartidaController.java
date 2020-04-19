package controllers;
import Entities.*;
import MainController.MainController;
import Patterns.Adapter.CasillaStoneAdapter;
import Patterns.FactoryMethod.FabricaElementos;
import Patterns.Observer.Observador;
import Patterns.Prototype.CasillaDiablillo;
import Patterns.Prototype.CasillaNormal;
import Patterns.Prototype.CasillaQuerubin;
import Patterns.Prototype.CasillaZorvan;
import Patterns.Proxy.MainControllerProxy;
import clasesUI.EjeXYCasilla;
import clasesUI.ElementoTabla;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;


public class tableroPartidaController implements Initializable,Observador {

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
    private Label lblJuega;

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
    public MainController mc;

    public ArrayList<ElementoTabla> arregloInfoElementos = new ArrayList<>();

    public ArrayList<EjeXYCasilla> coordenadasCasilla = new ArrayList<EjeXYCasilla>();

    public ArrayList<EjeXYCasilla> coordenadasCasillaFicha = new ArrayList<EjeXYCasilla>();

    public int valorDadoMovimeinto = 0;
    private int valorDadoAtaque = 1;

    public ControladorPrueba controladorTablero;
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

    //Checkbox para dado de ataque
    private CheckBox cb1Elemento;
    private CheckBox cb2Elemento;
    private CheckBox cb3Elemento;
    private boolean validacionCBElementos = false;
    //Checkbox para poder especial
    private CheckBox cb1Ataque;
    private CheckBox cb2Ataque;
    private CheckBox cb3Ataque;
    private boolean validacionCBPoderes = false;

    ArrayList<Elemento> elementosAtacan;
    ArrayList<Elemento> poderesAtacan;

    private String informacionResultadoDadoAtaque;

    public tableroPartidaController() throws IOException {
        MainControllerProxy proxy = new MainControllerProxy();
        mc = proxy.obtenerMainController("admin");
    }

    private FabricaElementos fabElementos = new FabricaElementos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgViewDado.setVisible(true);
        btnTirarDado.setVisible(true);
        btnTirarDado.setDisable(true);
        imgViewDadoAtaque.setVisible(false);
        btnTirarDadoAtaque.setVisible(false);
        flechaTirarDado.setVisible(false);
        iniciarValoresCoordenadas();


    }

    public void iniciarTodoTablero(ActionEvent event) throws IOException {
        //coloca nombres visualmente en la parte inferior de pantalla
        colocarNombres();
        //inactiva dado
        btnTirarDado.setDisable(false);
        //crea partida
        partida = mc.NuevaPartida(controladorTablero.arregloJugadores);
        //indica cuántos jugadores hay
        cantidadJugadores = controladorTablero.arregloJugadores.size();
        //añadiendo este controller como observador de las casillas
        partida.observarCasillas(this);
        //Coloca las casillas especiales en el tablero
        colocarCasillasEspeciales();
        //Coloca en el input el nombre del jugador en truno
        lblJuega.setText(controladorTablero.arregloJugadores.get(0).getNombre());
        //Establece el jugador en turno
        jugadorTurno = partida.turno;
        jugadoresPartida = partida.jugadores;
        //quita la flecha que indica que botón presionar al inicio
        flechaIniciarPartida.setVisible(false);
        // coloca la flecha que indica que se debe tirar dado
        flechaTirarDado.setVisible(true);
        //deshabilita el botón de iniciar tablero
        btnIniciarTodoTablero.setDisable(true);
        //prueba del dado de ataque
    }

    public void cambiarTurno() {
        //cambia el turno del jugador y coloca en el input el nombre del jugador en turno
        jugadorTurno = mc.obtenerTurno();
        lblJuega.setText(jugadorTurno.getNombre());
        if (numFicha == controladorTablero.arregloJugadores.size()) {
            numFicha = 1;
        } else {
            numFicha++;
        }
    }

    public void gestionarTurno(ActionEvent event) throws IOException {
        //VALIDAR QUE LA POSICIÓN ACTUAL DEL JUGADOR NO SEA STONE Y DE SERLO V

        // ER SI YA LE GANÓ AL STONE PARA PODER TIRAR DADO DE MOVIMIENTO

        int posicionActual = mc.obtenerPosicionJugador(jugadorTurno.ficha);
        //Pone el dado a "girar"
        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDado.setImage(imgDadoGirandoNumero);
        String urlImgDadoNumerico = "";
        //se genera el valor del dado de movimiento
        String jugadorActivo=jugadorTurno.getNombre();
        int resultadoDadoMovimiento = mc.obtenerMovimiento(jugadorActivo);
        //Según el resultado coloca la imagen del dado que coincide con su número
        switch (resultadoDadoMovimiento) {
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
        //permite que la imagen se muestre por un tiempo haciendo ver que está girando
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), e -> imgViewDado.setImage(imgDadoNum)));
        timeline.play();

        //indica al usuario que debe moverse
        dialogoMoverse(resultadoDadoMovimiento);

        jugadorNuevaPosicion = mc.obtenerNuevaPosicionFicha(resultadoDadoMovimiento);

        ImageView fichaJugadorIv = null;
        switch (numFicha) {
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

        mc.moverFicha(posicionActual, jugadorNuevaPosicion, jugadorTurno.ficha);
        //Debo setear nuevo jugador en turno

        cambiarTurno();
    }

    public void iniciarValoresCoordenadas() {
        double ejeX = 0;
        double ejeY = 0;
        double ejeYF = 0;
        for (int i = 1; i <= 100; i++) {
            if (i == 1) {
                EjeXYCasilla ejeCasilla = new EjeXYCasilla(i, 183, 165);
                EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, 183, 142);
                coordenadasCasilla.add(ejeCasilla);
                coordenadasCasillaFicha.add(ejeCasillaF);
            } else if (i == 100) {
                EjeXYCasilla ejeCasilla = new EjeXYCasilla(i, 1130, 555);
                coordenadasCasilla.add(ejeCasilla);
                EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, 1130, 530);
                coordenadasCasillaFicha.add(ejeCasillaF);
            } else {

                if (i >= 2 && i <= 15) {
                    ejeY = 165;
                    ejeYF = 142;
                } else if (i >= 16 && i <= 29) {
                    ejeY = 230;
                    ejeYF = 206;
                } else if (i >= 30 && i <= 43) {
                    ejeY = 295;
                    ejeYF = 274;
                } else if (i >= 44 && i <= 57) {
                    ejeY = 360;
                    ejeYF = 338;
                } else if (i >= 58 && i <= 71) {
                    ejeY = 425;
                    ejeYF = 401;
                } else if (i >= 72 && i <= 85) {
                    ejeY = 490;
                    ejeYF = 472;
                } else if (i >= 86 && i <= 99) {
                    ejeY = 555;
                    ejeYF = 530;
                }
                switch (i) {
                    case 2:
                    case 29:
                    case 30:
                    case 57:
                    case 58:
                    case 85:
                    case 86:
                        ejeX = 258;
                        break;
                    case 3:
                    case 28:
                    case 31:
                    case 56:
                    case 59:
                    case 84:
                    case 87:
                        ejeX = 318;
                        break;
                    case 4:
                    case 27:
                    case 32:
                    case 55:
                    case 60:
                    case 83:
                    case 88:
                        ejeX = 378;
                        break;
                    case 5:
                    case 26:
                    case 33:
                    case 54:
                    case 61:
                    case 82:
                    case 89:
                        ejeX = 436;
                        break;
                    case 6:
                    case 25:
                    case 34:
                    case 53:
                    case 62:
                    case 81:
                    case 90:
                        ejeX = 499;
                        break;
                    case 7:
                    case 24:
                    case 35:
                    case 52:
                    case 63:
                    case 80:
                    case 91:
                        ejeX = 557;
                        break;
                    case 8:
                    case 23:
                    case 36:
                    case 51:
                    case 64:
                    case 79:
                    case 92:
                        ejeX = 615;
                        break;
                    case 9:
                    case 22:
                    case 37:
                    case 50:
                    case 65:
                    case 78:
                    case 93:
                        ejeX = 678;
                        break;
                    case 10:
                    case 21:
                    case 38:
                    case 49:
                    case 66:
                    case 77:
                    case 94:
                        ejeX = 737;
                        break;
                    case 11:
                    case 20:
                    case 39:
                    case 48:
                    case 67:
                    case 76:
                    case 95:
                        ejeX = 796;
                        break;
                    case 12:
                    case 19:
                    case 40:
                    case 47:
                    case 68:
                    case 75:
                    case 96:
                        ejeX = 857;
                        break;
                    case 13:
                    case 18:
                    case 41:
                    case 46:
                    case 69:
                    case 74:
                    case 97:
                        ejeX = 918;
                        break;
                    case 14:
                    case 17:
                    case 42:
                    case 45:
                    case 70:
                    case 73:
                    case 98:
                        ejeX = 977;
                        break;
                    case 15:
                    case 16:
                    case 43:
                    case 44:
                    case 71:
                    case 72:
                    case 99:
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

    public void colocarCasillasEspeciales() {

        ArrayList<Integer> posicionesDiablillos = mc.obtenerDiablillos();
        ArrayList<Integer> posicionesStones = mc.obtenerStones();
        ArrayList<Integer> posicionesQuerubines = mc.obtenerQuerubines();

        for (int i = 0; i < posicionesDiablillos.size(); i++) {
            anchorPaneTablero.getChildren().add(circleDiablillo(posicionesDiablillos.get(i)));
        }

        for (int i = 0; i < posicionesStones.size(); i++) {
            anchorPaneTablero.getChildren().add(circleStone(posicionesStones.get(i)));
        }

        for (int i = 0; i < posicionesQuerubines.size(); i++) {
            anchorPaneTablero.getChildren().add(circleQuerubin(posicionesQuerubines.get(i)));
        }

    }

    public Circle circleQuerubin(int posicion) {
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(84, 197, 219));
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setRadius(9.0);
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public Circle circleDiablillo(int posicion) {
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(204, 39, 22));
        cQuerubin.setRadius(9.0);
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public Circle circleStone(int posicion) {
        Circle cQuerubin = new Circle();
        cQuerubin.setFill(Color.rgb(22, 204, 64));
        cQuerubin.setRadius(9.0);
        cQuerubin.setLayoutY(coordenadasCasilla.get(posicion).getLayoutY());
        cQuerubin.setLayoutX(coordenadasCasilla.get(posicion).getLayoutX());
        cQuerubin.setStroke(Color.BLACK);
        cQuerubin.setStrokeType(StrokeType.valueOf("INSIDE"));
        return cQuerubin;
    }

    public void turnoDadoAtaque() {
        imgViewDado.setVisible(false);
        btnTirarDado.setVisible(false);
        imgViewDadoAtaque.setVisible(true);
        imgViewDadoAtaque.setLayoutX(34);
        imgViewDadoAtaque.setLayoutY(211);
        btnTirarDadoAtaque.setVisible(true);
        btnTirarDadoAtaque.setLayoutX(26);
        btnTirarDadoAtaque.setLayoutY(296);
    }

    public void turnoDadoMovimiento() {
        imgViewDado.setVisible(true);
        btnTirarDado.setVisible(true);
        imgViewDadoAtaque.setVisible(false);
        btnTirarDadoAtaque.setVisible(false);
    }

    public void colocarNombres() {
        ArrayList<String> listaJugadores = controladorTablero.nombreJugadores;
        Image imgfP1 = new Image("/imgs/FICHAS-02.png");
        fichaP1.setImage(imgfP1);
        fichaP1.setLayoutY(145);
        fichaP1.setLayoutX(183);
        fichaP1.setFitWidth(45);
        fichaP1.setFitHeight(45);
        Image imgfP2 = new Image("/imgs/FICHAS-04.png");
        fichaP2.setImage(imgfP2);
        fichaP2.setLayoutY(145);
        fichaP2.setLayoutX(183);
        fichaP2.setFitWidth(45);
        fichaP2.setFitHeight(45);
        Image imgfP3 = new Image("/imgs/FICHAS-05.png");
        fichaP3.setImage(imgfP3);
        fichaP3.setLayoutY(145);
        fichaP3.setLayoutX(183);
        fichaP3.setFitWidth(45);
        fichaP3.setFitHeight(45);
        Image imgfP4 = new Image("/imgs/FICHAS-03.png");
        fichaP4.setImage(imgfP4);
        fichaP4.setLayoutY(145);
        fichaP4.setLayoutX(183);
        fichaP4.setFitWidth(45);
        fichaP4.setFitHeight(45);
        switch (listaJugadores.size()) {
            case 1:
                lblP1.setText(lblP1.getText() + " " + listaJugadores.get(0));
                lblP2.setVisible(true);
                lblP2.setText(lblP2.getText() + " Computadora");
                lblP3.setVisible(false);
                lblP4.setVisible(false);
                imgP2.setVisible(true);
                imgP3.setVisible(false);
                imgP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                break;
            case 2:
                lblP1.setText(lblP1.getText() + " " + listaJugadores.get(0));
                lblP2.setText(lblP2.getText() + " " + listaJugadores.get(1));
                lblP3.setVisible(false);
                lblP4.setVisible(false);
                imgP3.setVisible(false);
                imgP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                break;
            case 3:
                lblP1.setText(lblP1.getText() + " " + listaJugadores.get(0));
                lblP2.setText(lblP2.getText() + " " + listaJugadores.get(1));
                lblP3.setText(lblP3.getText() + " " + listaJugadores.get(2));
                lblP4.setVisible(false);
                anchorPaneTablero.getChildren().add(fichaP1);
                anchorPaneTablero.getChildren().add(fichaP2);
                anchorPaneTablero.getChildren().add(fichaP3);

                break;
            case 4:
                lblP1.setText(lblP1.getText() + " " + listaJugadores.get(0));
                lblP2.setText(lblP2.getText() + " " + listaJugadores.get(1));
                lblP3.setText(lblP3.getText() + " " + listaJugadores.get(2));
                lblP4.setText(lblP4.getText() + " " + listaJugadores.get(3));
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
        Optional<ButtonType> opcion = alert.showAndWait();
        if (opcion.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    public int tirarDado() throws IOException {
        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDado.setImage(imgDadoGirandoNumero);
        String urlImgDadoNumerico = "";
        String jugadorActivo=jugadorTurno.getNombre();
        int valorDadoMovimeinto = mc.obtenerMovimiento(jugadorActivo);
        switch (valorDadoMovimeinto) {
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
    //ActionEvent event
    public void tirarDadoAtaque() throws IOException {
        Image imgDadoGirandoNumero = new Image("/imgs/dadoNumericoGirando.gif");
        imgViewDadoAtaque.setImage(imgDadoGirandoNumero);

        String resultadoDadoAtaque = mc.obtenerAtaque();
        informacionResultadoDadoAtaque = resultadoDadoAtaque;

        switch (resultadoDadoAtaque) {
            case "Tu girada ha resultado en ataca un personaje de la triada":
                valorDadoAtaque = 1;
                break;
            case "Tu girada ha resultado en atacan dos personajes de la triada":
                valorDadoAtaque = 2;
                break;
            case "Tu girada ha resultado en ataca tres personaje de la triada":
                valorDadoAtaque = 3;
                break;
            case "Tu girada ha resultado en ataca solo un personaje y puede activar un poder especial de cualquiera":
                valorDadoAtaque = 4;
                break;
            case "Tu girada ha resultado en atacan dos personajes y se activa un poder especial":
                valorDadoAtaque = 5;
                break;
            case "Tu girada ha resultado en atacan todos los personajes y se activan dos poderes especiales":
                valorDadoAtaque = 6;
                break;
        }

        String urlImgDadoNumerico = "";
        switch (valorDadoAtaque) {
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
                new Timeline(new KeyFrame(Duration.millis(1000), e -> imgViewDadoAtaque.setImage(imgDadoAta)));
        timeline.play();
    }

    public void iniciarArregloInfoElementos() {
        ElementoTabla eleFuego = new ElementoTabla("Le otorga 5 puntos extra a un personaje de la triada por 2 turnos",
                "Fuego", "Neutral", "Desventaja", "Ventaja", "Desventaja", "Desventaja",
                "Ventaja");

        ElementoTabla eleAgua = new ElementoTabla("Habilita dado de ataque para tiro extra",
                "Agua", "Ventaja", "Neutral", "Desventaja", "Desventaja", "Ventaja",
                "Desventaja");

        ElementoTabla elePlanta = new ElementoTabla("Por 2 turnos no deja que un jugador saque más de 3 en su dado de movimientos",
                "Planta", "Desventaja", "Ventaja", "Neutral", "Ventaja", "Desventaja",
                "Desventaja");

        ElementoTabla eleElectrico = new ElementoTabla("Causa una parálisis que puede evitar que 1 jugador tire el dado de movimiento, este efecto dura 3 turnos",
                "Eléctrico", "Ventaja", "Ventaja", "Desventaja", "Neutral", "Desventaja",
                "Ventaja");

        ElementoTabla eleRoca = new ElementoTabla("Crea 1 stone en casilla de algún contrincante con desventaja de su triada (Stone 60 pts vida)",
                "Roca", "Ventaja", "Desventaja", "Ventaja", "Ventaja", "Neutral",
                "Desventaja");

        ElementoTabla eleHielo = new ElementoTabla("Congela a un jugador por turno",
                "Hielo", "Desventaja", "Ventaja", "Ventaja", "Desventaja", "Ventaja",
                "Ventaja");

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

        if (arregloInfoElementos.size() == 0) {
            iniciarArregloInfoElementos();
        }

        for (ElementoTabla obj : arregloInfoElementos) {
            tbl.getItems().add(obj);
        }

        alert.getDialogPane().setContent(tbl);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(1100, 400);


        alert.showAndWait();
    }

    public void decisionJugadorStone() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Hora de atacar guerrero. "+informacionResultadoDadoAtaque);
        String nombreElemento1 = jugadorTurno.ficha.getPersonajes()[0].getElemento().getTipo();
        String nombreElemento2 = jugadorTurno.ficha.getPersonajes()[1].getElemento().getTipo();
        String nombreElemento3 = jugadorTurno.ficha.getPersonajes()[2].getElemento().getTipo();
        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        boolean opcionesPoderes = false;

        GridPane gP = new GridPane();

        Text txtAtaca = new Text("Ataque de: ");
        txtAtaca.setFont(Font.font("Matura MT Script Capitals", 30));
        txtAtaca.setFill(Color.rgb(58, 54, 21));
        gP.add(txtAtaca, 0, 0);
        //Columna 0
        cb1Elemento = new CheckBox(nombreElemento1);
        cb1Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        gP.add(cb1Elemento, 0, 1);

        cb2Elemento = new CheckBox(nombreElemento2);
        cb2Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        gP.add(cb2Elemento, 0, 2);

        cb3Elemento = new CheckBox(nombreElemento3);
        cb3Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        gP.add(cb3Elemento, 0, 3);

        //Columna 1
        ImageView img1Elemento = new ImageView();
        Image img1 = new Image("/imgs/" + nombreElemento1 + ".png");
        img1Elemento.setImage(img1);
        img1Elemento.setFitHeight(50);
        img1Elemento.setFitWidth(50);
        gP.add(img1Elemento, 1, 1);

        ImageView img2Elemento = new ImageView();
        Image img2 = new Image("/imgs/" + nombreElemento2 + ".png");
        img2Elemento.setImage(img2);
        img2Elemento.setFitHeight(50);
        img2Elemento.setFitWidth(50);
        gP.add(img2Elemento, 1, 2);

        ImageView img3Elemento = new ImageView();
        Image img3 = new Image("/imgs/" + nombreElemento3 + ".png");
        img3Elemento.setImage(img3);
        img3Elemento.setFitHeight(50);
        img3Elemento.setFitWidth(50);
        gP.add(img3Elemento, 1, 3);

        switch (valorDadoAtaque) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                cb1Elemento.setSelected(true);
                cb1Elemento.setDisable(true);
                cb2Elemento.setSelected(true);
                cb2Elemento.setDisable(true);
                cb3Elemento.setSelected(true);
                cb3Elemento.setDisable(true);
                alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                break;
            case 4:
                opcionesPoderes = true;
                break;
            case 5:
                opcionesPoderes = true;
                break;
            case 6:
                cb1Elemento.setSelected(true);
                cb1Elemento.setDisable(true);
                cb2Elemento.setSelected(true);
                cb2Elemento.setDisable(true);
                cb3Elemento.setSelected(true);
                cb3Elemento.setDisable(true);
                opcionesPoderes = true;
                break;
        }

        //Columna 2
        if (opcionesPoderes) {
            Text txtPoder = new Text("Poder especial de: ");
            txtPoder.setFont(Font.font("Matura MT Script Capitals", 30));
            txtPoder.setFill(Color.rgb(58, 54, 21));
            gP.add(txtPoder, 2, 0);
            cb1Ataque = new CheckBox(nombreElemento1);
            cb1Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            gP.add(cb1Ataque, 2, 1);
            cb2Ataque = new CheckBox(nombreElemento2);
            cb2Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            gP.add(cb2Ataque, 2, 2);
            cb3Ataque = new CheckBox(nombreElemento3);
            cb3Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            gP.add(cb3Ataque, 2, 3);
        }

        //validación uno, una opción seleccionada para elementos
        EventHandler ePrimerValidacionElementoC1C4 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox chk = (CheckBox) event.getSource();
//
                    if (nombreElemento1.equals(chk.getText())) {
                        cb2Elemento.setSelected(!cb1Elemento.isSelected());
                        cb3Elemento.setSelected(!cb1Elemento.isSelected());

                    } else if (nombreElemento2.equals(chk.getText())) {
                        cb1Elemento.setSelected(!cb2Elemento.isSelected());
                        cb3Elemento.setSelected(!cb2Elemento.isSelected());

                    } else if (nombreElemento3.equals(chk.getText())) {
                        cb1Elemento.setSelected(!cb3Elemento.isSelected());
                        cb2Elemento.setSelected(!cb3Elemento.isSelected());

                    }

                    if ((cb1Elemento.isSelected() ||
                            cb2Elemento.isSelected() ||
                            cb3Elemento.isSelected())
                            &&
                            (
                                    (cb1Elemento.isSelected() != cb2Elemento.isSelected() &&
                                            cb1Elemento.isSelected() != cb3Elemento.isSelected())
                                            ||
                                            (cb2Elemento.isSelected() != cb1Elemento.isSelected() &&
                                                    cb2Elemento.isSelected() != cb3Elemento.isSelected())
                                            ||
                                            (cb3Elemento.isSelected() != cb1Elemento.isSelected() &&
                                                    cb3Elemento.isSelected() != cb2Elemento.isSelected())
                            )
                    ) {
                        validacionCBElementos = true;
                        if (validacionCBPoderes && valorDadoAtaque == 4 || valorDadoAtaque == 1) {
                            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                        }

                    } else {
                        validacionCBElementos = false;
                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                    }

                }

            }

        };

        //validación dos segundaValidación, dos opciones seleccionadas para elementos
        EventHandler eSegundaValidacionElementosC2C5 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox chk = (CheckBox) event.getSource();
//                    System.out.println("Action performed on checkbox " + chk.getText());
                    if (nombreElemento1.equals(chk.getText())) {
                        if (cb2Elemento.isSelected() && cb3Elemento.isSelected()) {
                            cb2Elemento.setSelected(false);
                            cb3Elemento.setSelected(false);
                        }

                    } else if (nombreElemento2.equals(chk.getText())) {
                        if (cb1Elemento.isSelected() && cb3Elemento.isSelected()) {
                            cb1Elemento.setSelected(false);
                            cb3Elemento.setSelected(false);
                        }

                    } else if (nombreElemento3.equals(chk.getText())) {
                        if (cb1Elemento.isSelected() && cb2Elemento.isSelected()) {
                            cb1Elemento.setSelected(false);
                            cb2Elemento.setSelected(false);
                        }
                    }

                    if (((cb1Elemento.isSelected() && cb2Elemento.isSelected()) ||
                            (cb1Elemento.isSelected() && cb3Elemento.isSelected()) ||
                            (cb2Elemento.isSelected() && cb3Elemento.isSelected()))
                            &&
                            !(cb1Elemento.isSelected() &&
                                    cb2Elemento.isSelected() &&
                                    cb3Elemento.isSelected())) {
                        validacionCBElementos = true;
                        if (validacionCBPoderes && valorDadoAtaque == 5 || valorDadoAtaque == 2) {
                            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                        }

                    } else {
                        validacionCBElementos = false;
                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                    }


                }
            }
        };

        //valición un poder seleccionado
        //validación uno, una opción seleccionada para elementos
        EventHandler ePrimerValidacionPoderC4C5 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox chk = (CheckBox) event.getSource();
//
                    if (nombreElemento1.equals(chk.getText())) {
                        cb2Ataque.setSelected(!cb1Ataque.isSelected());
                        cb3Ataque.setSelected(!cb1Ataque.isSelected());

                    } else if (nombreElemento2.equals(chk.getText())) {
                        cb1Ataque.setSelected(!cb2Ataque.isSelected());
                        cb3Ataque.setSelected(!cb2Ataque.isSelected());

                    } else if (nombreElemento3.equals(chk.getText())) {
                        cb1Ataque.setSelected(!cb3Ataque.isSelected());
                        cb2Ataque.setSelected(!cb3Ataque.isSelected());

                    }

                    if ((cb1Ataque.isSelected() ||
                            cb2Ataque.isSelected() ||
                            cb3Ataque.isSelected())
                            &&
                            (
                                    (cb1Ataque.isSelected() != cb2Ataque.isSelected() &&
                                            cb1Ataque.isSelected() != cb3Ataque.isSelected())
                                            ||
                                            (cb2Ataque.isSelected() != cb1Ataque.isSelected() &&
                                                    cb2Ataque.isSelected() != cb3Ataque.isSelected())
                                            ||
                                            (cb3Ataque.isSelected() != cb1Ataque.isSelected() &&
                                                    cb3Ataque.isSelected() != cb2Ataque.isSelected())
                            )
                    ) {
                        validacionCBPoderes = true;
                        if (validacionCBElementos && (valorDadoAtaque == 4 || valorDadoAtaque == 5)) {
                            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                        }

                    } else {
                        validacionCBPoderes = false;
                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                    }

                }

            }

        };


        //validación dos poderes seleccionados
        EventHandler eSegundaValidacionPoderC6 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (event.getSource() instanceof CheckBox) {
                    CheckBox chk = (CheckBox) event.getSource();
//                    System.out.println("Action performed on checkbox " + chk.getText());
                    if (nombreElemento1.equals(chk.getText())) {
                        if (cb2Ataque.isSelected() && cb3Ataque.isSelected()) {
                            cb2Ataque.setSelected(false);
                            cb3Ataque.setSelected(false);
                        }

                    } else if (nombreElemento2.equals(chk.getText())) {
                        if (cb1Ataque.isSelected() && cb3Ataque.isSelected()) {
                            cb1Ataque.setSelected(false);
                            cb3Ataque.setSelected(false);
                        }

                    } else if (nombreElemento3.equals(chk.getText())) {
                        if (cb1Ataque.isSelected() && cb2Ataque.isSelected()) {
                            cb1Ataque.setSelected(false);
                            cb2Ataque.setSelected(false);
                        }
                    }

                    if (((cb1Ataque.isSelected() && cb2Ataque.isSelected()) ||
                            (cb1Ataque.isSelected() && cb3Ataque.isSelected()) ||
                            (cb2Ataque.isSelected() && cb3Ataque.isSelected()))
                            &&
                            !(cb1Ataque.isSelected() &&
                                    cb2Ataque.isSelected() &&
                                    cb3Ataque.isSelected())) {
                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                    } else {
                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                    }


                }
            }
        };


        if (valorDadoAtaque == 1 || valorDadoAtaque == 4) {
            cb1Elemento.setOnAction(ePrimerValidacionElementoC1C4);
            cb2Elemento.setOnAction(ePrimerValidacionElementoC1C4);
            cb3Elemento.setOnAction(ePrimerValidacionElementoC1C4);
        }
        if (valorDadoAtaque == 2 || valorDadoAtaque == 5) {
            cb1Elemento.setOnAction(eSegundaValidacionElementosC2C5);
            cb2Elemento.setOnAction(eSegundaValidacionElementosC2C5);
            cb3Elemento.setOnAction(eSegundaValidacionElementosC2C5);
        }
        if (valorDadoAtaque == 4 || valorDadoAtaque == 5) {
            cb1Ataque.setOnAction(ePrimerValidacionPoderC4C5);
            cb2Ataque.setOnAction(ePrimerValidacionPoderC4C5);
            cb3Ataque.setOnAction(ePrimerValidacionPoderC4C5);
        }
        if (valorDadoAtaque == 6) {
            cb1Ataque.setOnAction(eSegundaValidacionPoderC6);
            cb2Ataque.setOnAction(eSegundaValidacionPoderC6);
            cb3Ataque.setOnAction(eSegundaValidacionPoderC6);
        }

        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(800, 400);
        alert.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);


        alert.showAndWait();
        //Para validar botón
        Optional<ButtonType> result = alert.showAndWait();
       if
        (!result.isPresent()) {
        }
        else if (result.get() == ButtonType.OK) {
            elementosAtacan  = new   ArrayList<Elemento>();
                if(cb1Elemento.isSelected()){
                    elementosAtacan.add(fabElementos.obtenerElemento(cb1Elemento.getText()));
                }
                if(cb2Elemento.isSelected()){
                    elementosAtacan.add(fabElementos.obtenerElemento(cb2Elemento.getText()));
                }
                if(cb3Elemento.isSelected()){
                    elementosAtacan.add(fabElementos.obtenerElemento(cb3Elemento.getText()));
                }

                if(valorDadoAtaque ==4 || valorDadoAtaque == 5 || valorDadoAtaque ==6){
                    poderesAtacan = new ArrayList<Elemento>();
                    if(cb1Ataque.isSelected()){
                        poderesAtacan.add(fabElementos.obtenerElemento(cb1Ataque.getText()));
                    }
                    if(cb2Ataque.isSelected()){
                        poderesAtacan.add(fabElementos.obtenerElemento(cb2Ataque.getText()));
                    }
                    if(cb3Ataque.isSelected()){
                        poderesAtacan.add(fabElementos.obtenerElemento(cb3Ataque.getText()));
                    }
                }

        }

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
                "\nHas de avanzar 11 casillas.");
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        GridPane gP = new GridPane();
        Text txt = new Text("Avanza: " + resultadoDado +" casillas.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(200, 150);
        alert.showAndWait();
    }

    @Override
    public void update(String value) throws IOException {
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
                int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                nuevaPosicion = mc.movimientoDiablito();

                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);

            }else if(value.equals("Querubin")){
                dialogoQuerubin();
                int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                nuevaPosicion = mc.movimientoQuerubin();
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);

            }else if(value.equals("Stone")){
                dialogoStone();
                //no se puede usar obtener turno porque mueve al jugador
                 mc.partida.turno.setInabilitado(true);
                //para validar si el jugador no puede moverse hasta matar al stone
            }else if(value.equals("Zorvan")){
                dialogoZorvan();
                int casillasExtras = jugadorNuevaPosicion - 100;
                int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                nuevaPosicion = mc.movimientoZorvan(casillasExtras);
                mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());

            }

        }
    }
}
