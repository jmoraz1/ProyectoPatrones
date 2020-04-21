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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import main.ControladorPrueba;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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

    //checkbox para jugadores
    private CheckBox cbJ1;
    private CheckBox cbJ2;
    private CheckBox cbJ3;

    private String jugadorAAfectarPorPoder = "";
    private Elemento elementoAAfectarPorPoder = null;
    //Determina si el juego sigue o ya alguien ganó
    private boolean terminaJuego = false;

    ArrayList<Elemento> elementosAtacan = null;
    ArrayList<Elemento> poderesAtacan = null;

    private String informacionResultadoDadoAtaque;

    private String nombreGanadorJuego = "";

    private int resulNuevaPosicion;
    private Boolean ganador=false;

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

    public void cambiarTurno() throws IOException {
        //cambia el turno del jugador y coloca en el input el nombre del jugador en turno
        jugadorTurno = mc.obtenerTurno();
        lblJuega.setText(jugadorTurno.getNombre());
        if (numFicha == controladorTablero.arregloJugadores.size()) {
            numFicha = 1;
        } else {
            numFicha++;
        }

        if(jugadorTurno.getNombre().equals("Computadora") && !terminaJuego){
           turnoPC();
        }
    }

    private void turnoPC() throws IOException {
        ActionEvent event=new ActionEvent();
        int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
        dialogoTurnoComputadora();
        gestionarTurno(event);
    }

    private void dialogoTurnoComputadora() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        GridPane gP = new GridPane();
        Text txt = new Text("Turno de la computadora.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(200, 150);
        alert.showAndWait();
    }

    public void gestionarTurno(ActionEvent event) throws IOException {
        int posicionActual = mc.obtenerPosicionJugador(jugadorTurno.ficha);
        //VALIDAR SI POSICIÓN ACTUAL DE JUGADOR ES STONE Y SI NO LO HA VENCIDO

        //ELSE LLAMAR DADO MOVIMIENTO
        if(!terminaJuego){
            if(mc.casillaStone(jugadorTurno.getNombre()) && !mc.stoneVencido(jugadorTurno.getNombre())){
                turnoDadoAtaque();
                tirarDadoAtaque();
                if(jugadorTurno.getNombre().equals("Computadora")){
                    decisionComputadoraStone();
                    gestionarElementosPoderesContraStoneContrincantes();
                }else{
                    //habilita dado ataque y deshabilita dado movimiento
                    decisionJugadorStone();
                    gestionarElementosPoderesContraStoneContrincantes();
                }

            }else{
                turnoDadoMovimiento();
                gestionarTurnoDadoMovimiento(posicionActual);
            }
            //Cambiar turnonuevo jugador en turno
            cambiarTurno();
        }

    }


    public void decisionComputadoraStone() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Hora de atacar guerrero. "+informacionResultadoDadoAtaque);
        String nombreElemento1 = jugadorTurno.ficha.getPersonajes()[0].getElemento().getTipo();
        String nombreElemento2 = jugadorTurno.ficha.getPersonajes()[1].getElemento().getTipo();
        String nombreElemento3 = jugadorTurno.ficha.getPersonajes()[2].getElemento().getTipo();
        boolean opcionesPoderes = false;

        GridPane gP = new GridPane();

        Text txtAtaca = new Text("Ataque de: ");
        txtAtaca.setFont(Font.font("Matura MT Script Capitals", 30));
        txtAtaca.setFill(Color.rgb(58, 54, 21));
        gP.add(txtAtaca, 0, 0);
        //Columna 0
        cb1Elemento = new CheckBox(nombreElemento1);
        cb1Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        cb1Elemento.setDisable(true);
        gP.add(cb1Elemento, 0, 1);

        cb2Elemento = new CheckBox(nombreElemento2);
        cb2Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        cb2Elemento.setDisable(true);
        gP.add(cb2Elemento, 0, 2);

        cb3Elemento = new CheckBox(nombreElemento3);
        cb3Elemento.setFont(Font.font("Matura MT Script Capitals", 30));
        cb3Elemento.setDisable(true);
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

        int seleccionElemento=0;
        switch (valorDadoAtaque) {
            case 1:
            case 4:
                seleccionElemento = (int) Math.floor(Math.random()*3+1);
                switch (seleccionElemento){
                    case 1:
                        cb1Elemento.setSelected(true);

                        break;
                    case 2:
                        cb2Elemento.setSelected(true);

                        break;
                    case 3:
                        cb3Elemento.setSelected(true);

                        break;
                }
                break;
            case 2:
            case 5:
                int seleccionElemento2=0;
                seleccionElemento = (int) Math.floor(Math.random()*3+1);
                do{
                    seleccionElemento2=(int) Math.floor(Math.random()*3+1);
                }while(seleccionElemento==seleccionElemento2);

                if(seleccionElemento==1 || seleccionElemento2==1){
                    cb1Elemento.setSelected(true);
                }
                if(seleccionElemento==2 || seleccionElemento2==2){
                    cb2Elemento.setSelected(true);
                }
                if(seleccionElemento==3 || seleccionElemento2==3){
                    cb3Elemento.setSelected(true);
                }
               break;
            case 3:
            case 6:
                cb1Elemento.setSelected(true);
                cb2Elemento.setSelected(true);
                cb3Elemento.setSelected(true);
                break;


        }


        //Columna 2
        if (valorDadoAtaque==4||valorDadoAtaque==5||valorDadoAtaque==6) {
            Text txtPoder = new Text("Poder especial de: ");
            txtPoder.setFont(Font.font("Matura MT Script Capitals", 30));
            txtPoder.setFill(Color.rgb(58, 54, 21));
            gP.add(txtPoder, 2, 0);
            cb1Ataque = new CheckBox(nombreElemento1);
            cb1Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            cb1Ataque.setDisable(true);
            gP.add(cb1Ataque, 2, 1);
            cb2Ataque = new CheckBox(nombreElemento2);
            cb2Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            cb2Ataque.setDisable(true);
            gP.add(cb2Ataque, 2, 2);
            cb3Ataque = new CheckBox(nombreElemento3);
            cb3Ataque.setFont(Font.font("Matura MT Script Capitals", 30));
            cb3Ataque.setDisable(true);
            gP.add(cb3Ataque, 2, 3);
            int seleccionPoderEspecial=0;
            switch (valorDadoAtaque){
                case 4:
                case 5:
                    seleccionPoderEspecial=(int) Math.floor(Math.random()*3+1);
                    switch (seleccionPoderEspecial){
                        case 1:
                            cb1Ataque.setSelected(true);

                            break;
                        case 2:
                            cb2Ataque.setSelected(true);

                            break;
                        case 3:
                            cb3Ataque.setSelected(true);

                            break;
                    }
                    break;
                case 6:
                    int seleccionPoder2=0;
                    seleccionPoderEspecial = (int) Math.floor(Math.random()*3+1);
                    do{
                        seleccionPoder2=(int) Math.floor(Math.random()*3+1);
                    }while(seleccionPoderEspecial==seleccionPoder2);

                    if(seleccionPoderEspecial==1 || seleccionPoder2==1){
                        cb1Ataque.setSelected(true);
                    }
                    if(seleccionPoderEspecial==2 || seleccionPoder2==2){
                        cb2Ataque.setSelected(true);
                    }
                    if(seleccionPoderEspecial==3 || seleccionPoder2==3){
                        cb3Ataque.setSelected(true);
                    }
                    break;




            }
        }

        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(800, 400);
        alert.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

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
    //Se usa cuándo el jugador en turno está habilitado para jugar por movimiento
    public void gestionarTurnoDadoMovimiento(int posicionActual)throws IOException {
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
            default:
                urlImgDadoNumerico = "/imgs/dadoAtaqueLimpio.jpg";
                break;
        }
        Image imgDadoNum = new Image(urlImgDadoNumerico);
        //permite que la imagen se muestre por un tiempo haciendo ver que está girando
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), e -> imgViewDado.setImage(imgDadoNum)));
        timeline.play();

        //indica al usuario que debe moverse

        dialogoMoverse(resultadoDadoMovimiento);
        resulNuevaPosicion=posicionActual+resultadoDadoMovimiento;
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
                EjeXYCasilla ejeCasillaF = new EjeXYCasilla(i, 1077, 525);
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
        btnTirarDado.setVisible(true);
        imgViewDadoAtaque.setVisible(true);
        imgViewDadoAtaque.setLayoutX(34);
        imgViewDadoAtaque.setLayoutY(211);

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
        String urlImgDadoNumerico = "";
        switch (resultadoDadoAtaque) {
            case "Tu girada ha resultado en ataca un personaje de la triada":
                urlImgDadoNumerico = "/imgs/atacaUnPersonajeTriada.jpg";
                valorDadoAtaque = 1;
                break;
            case "Tu girada ha resultado en atacan dos personajes de la triada":
                urlImgDadoNumerico = "/imgs/atacanDosPersonajesTriada.jpg";
                valorDadoAtaque = 2;
                break;
            case "Tu girada ha resultado en ataca tres personaje de la triada":
                urlImgDadoNumerico = "/imgs/atacanTresPersonajesTriada.jpg";
                valorDadoAtaque = 3;
                break;
            case "Tu girada ha resultado en ataca solo un personaje y puede activar un poder especial de cualquiera":
                urlImgDadoNumerico = "/imgs/atacaUnPersonaActivaPoderEspecial.jpg";
                valorDadoAtaque = 4;
                break;
            case "Tu girada ha resultado en atacan dos personajes y se activa un poder especial":
                urlImgDadoNumerico = "/imgs/atacanDosPersonajesPoderEspecial.jpg";
                valorDadoAtaque = 5;
                break;
            case "Tu girada ha resultado en atacan todos los personajes y se activan dos poderes especiales":
                urlImgDadoNumerico = "/imgs/dadoAtacanTodosDosPoderEspeciales.jpg";
                valorDadoAtaque = 6;
                break;
        }
        Image imgDadoAta = new Image(urlImgDadoNumerico);
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(1000), e -> imgViewDadoAtaque.setImage(imgDadoAta)));
        timeline.play();
    }

    //Inicia información sobre ventajas, desventajas y poderes de los elementos de tríada
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

    //Muestra la información general de los elementos al jugador
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

    //El jugador decide que hacer dado el dado de ataque
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

    public void gestionarElementosPoderesContraStoneContrincantes() throws IOException {

        //falta ataque stone
        if(elementosAtacan != null){
            dialogoAtaqueAStone(mc.Ataque(jugadorTurno.getNombre(),elementosAtacan));
            elementosAtacan = null;
        }

        if(poderesAtacan != null){

            boolean existeAgua = false;
            for (Elemento e : poderesAtacan){
                switch (e.getTipo()){
                    case "Planta":
                        //darle a elegir a cuál jugador aplicar poder
                        String infoPoderPlanta = "Selecciona a un jugador para que por dos turnos " +
                                "no dejes que saque mas de tres en su dado de movimientos";
                        decisionJugadorPoderAplicar(infoPoderPlanta, "Planta");
                        dialogoPoder(mc.poderPlanta(jugadorAAfectarPorPoder),"Planta");
                        break;
                    case "Electrico":
                        //darle a elegir a cuál jugador aplicar poder
                        String infoPoderElectrico = "Selecciona a un jugador para que no tire dado movimiento en tres turnos";
                        decisionJugadorPoderAplicar(infoPoderElectrico, "Electrico");
                        dialogoPoder(mc.poderElectrico(jugadorAAfectarPorPoder), "Electrico");
                        break;
                    case "Hielo":
                        //darle a elegir a cuál jugador aplicar poder
                        String infoPoderHielo = "Selecciona el jugador al que deseas congelar por un turno";
                        decisionJugadorPoderAplicar(infoPoderHielo, "Hielo");
                        dialogoPoder(mc.poderHielo(jugadorAAfectarPorPoder), "Hielo");
                        break;
                    case "Fuego":
                        //decidir a cual elemento se le dan 5 pts extra de la triada propia
                        String infoPoderFuego = "Selecciona el elemento de tu triada al que le otorgas 5 puntos extra por dos turnos";
                        decisionJugadorPoderAplicar(infoPoderFuego, "Fuego");
                        dialogoPoder(mc.poderFuego(jugadorTurno.getNombre(), elementoAAfectarPorPoder),"Fuego");
                        break;
                    case "Agua":
                        existeAgua = true;
                        break;
                    default:
                        String infoPoderRoca = "Selecciona el jugador al que deseas colocarle un stone";
                        decisionJugadorPoderAplicar(infoPoderRoca, "Roca");
                        int posicionRoca = mc.obtenerIndice(jugadorAAfectarPorPoder);
                        anchorPaneTablero.getChildren().add(circleStone(posicionRoca));
                        dialogoPoder(mc.poderRoca(jugadorAAfectarPorPoder),"Roca");
                        break;
                }
            }

            if(existeAgua){
                String infoPoderAgua =  mc.poderAgua();

                if(!mc.stoneVencidoParaAgua(jugadorTurno.getNombre())){
                    dialogoPoderAgua(infoPoderAgua);
                    poderesAtacan = null;
                    elementosAtacan = null;
                    turnoDadoAtaque();
                    tirarDadoAtaque();
                    if(jugadorTurno.equals("Computadora")){
                        decisionComputadoraStone();
                    }else{
                        decisionJugadorStone();
                    }
                    gestionarElementosPoderesContraStoneContrincantes();
                }else{
                    dialogoPoderAguaStoneVencido();
                }
            }

            poderesAtacan = null;
        }



    }

    public void dialogoAtaqueAStone(String resultadoAtaque){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado ataque");

        GridPane gP = new GridPane();
        Text txt = new Text(resultadoAtaque);
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));

        gP.add(txt, 0,0);
        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/stone.jpeg");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(100);
        imgZorvan.setFitWidth(100);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 200);
        alert.showAndWait();
    }

    public void decisionJugadorPoderAplicar(String infoPoder, String tipoPoder) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Hora de aplicar un poder especial guerrero.");
        String nombreElemento1 = jugadorTurno.ficha.getPersonajes()[0].getElemento().getTipo();
        String nombreElemento2 = jugadorTurno.ficha.getPersonajes()[1].getElemento().getTipo();
        String nombreElemento3 = jugadorTurno.ficha.getPersonajes()[2].getElemento().getTipo();
        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);


        Text txtInfo = new Text(infoPoder);
        txtInfo.setFont(Font.font("Matura MT Script Capitals", 30));
        txtInfo.setFill(Color.rgb(58, 54, 21));
        alert.getDialogPane().setContent(txtInfo);
        GridPane gP = new GridPane();

        switch (tipoPoder) {
            case "Planta":
            case "Electrico":
            case "Hielo":
            case "Roca":
                Text txtAtaca = new Text("Ataque a jugador: ");
                txtAtaca.setFont(Font.font("Matura MT Script Capitals", 30));
                txtAtaca.setFill(Color.rgb(58, 54, 21));
                gP.add(txtAtaca, 0, 0);
                //Columna 0

                int indexArray = 0;
                String[] arrayNombreJugadores = new String[cantidadJugadores];
                for (Jugador j : partida.jugadores) {
                    if (j.getNombre() != jugadorTurno.getNombre()) {
                        arrayNombreJugadores[indexArray] = j.getNombre();
                        indexArray++;
                    }
                }
                cbJ1 = new CheckBox(arrayNombreJugadores[0]);
                cbJ1.setFont(Font.font("Matura MT Script Capitals", 30));
                gP.add(cbJ1, 0, 1);

                if (cantidadJugadores == 3 || cantidadJugadores == 4) {
                    cbJ2 = new CheckBox(arrayNombreJugadores[1]);
                    cbJ2.setFont(Font.font("Matura MT Script Capitals", 30));
                    gP.add(cbJ2, 0, 2);

                    if (cantidadJugadores == 3 && !jugadorTurno.getNombre().equals("Computadora")) {
                        EventHandler eValidacionJugador3 = new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (event.getSource() instanceof CheckBox) {
                                    CheckBox chk = (CheckBox) event.getSource();//
                                    if (cbJ1.getText().equals(chk.getText())) {
                                        cbJ2.setSelected(!cbJ1.isSelected());
                                    } else if (cbJ2.getText().equals(chk.getText())) {
                                        cbJ1.setSelected(!cbJ2.isSelected());
                                    }
                                    if (!cbJ1.isSelected() && !cbJ2.isSelected()) {
                                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                                    } else {
                                        alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                                    }
                                }
                            }
                        };

                        cbJ1.setOnAction(eValidacionJugador3);
                        cbJ2.setOnAction(eValidacionJugador3);
                    }

                    if (cantidadJugadores == 4) {
                        cbJ3 = new CheckBox(arrayNombreJugadores[2]);
                        cbJ3.setFont(Font.font("Matura MT Script Capitals", 30));
                        gP.add(cbJ3, 0, 3);
                        if(!jugadorTurno.getNombre().equals("Computadora")){
                            EventHandler eValidacionJugador4 = new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (event.getSource() instanceof CheckBox) {
                                        CheckBox chk = (CheckBox) event.getSource();
//
                                        if (cbJ1.getText().equals(chk.getText())) {
                                            cbJ2.setSelected(!cbJ1.isSelected());
                                            cbJ3.setSelected(!cbJ1.isSelected());

                                        } else if (cbJ2.getText().equals(chk.getText())) {
                                            cbJ1.setSelected(!cbJ2.isSelected());
                                            cbJ3.setSelected(!cbJ2.isSelected());

                                        } else if (cbJ3.getText().equals(chk.getText())) {
                                            cbJ1.setSelected(!cbJ3.isSelected());
                                            cbJ2.setSelected(!cbJ3.isSelected());

                                        }

                                        if ((cbJ1.isSelected() ||
                                                cbJ2.isSelected() ||
                                                cbJ3.isSelected())
                                                &&
                                                (
                                                        (cbJ1.isSelected() != cbJ2.isSelected() &&
                                                                cbJ1.isSelected() != cbJ3.isSelected())
                                                                ||
                                                                (cbJ2.isSelected() != cbJ1.isSelected() &&
                                                                        cbJ2.isSelected() != cbJ3.isSelected())
                                                                ||
                                                                (cbJ3.isSelected() != cbJ1.isSelected() &&
                                                                        cbJ3.isSelected() != cbJ2.isSelected())
                                                )
                                        ) {

                                            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);


                                        } else {
                                            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                                        }

                                    }

                                }

                            };
                            cbJ1.setOnAction(eValidacionJugador4);
                            cbJ2.setOnAction(eValidacionJugador4);
                            cbJ3.setOnAction(eValidacionJugador4);
                        }

                    }
                }
                if(jugadorTurno.getNombre().equals("Computadora")){
                    alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                    cbJ1.setDisable(true);
                    int seleccionJugador=0;
                    if(cantidadJugadores==3){
                        cbJ2.setDisable(true);
                        seleccionJugador=(int) Math.floor(Math.random()*2+1);
                     }else if(cantidadJugadores==4){
                        cbJ2.setDisable(true);
                        cbJ3.setDisable(true);
                        seleccionJugador=(int) Math.floor(Math.random()*2+1);
                    }

                    switch (seleccionJugador){
                        case 1:
                            cbJ1.setSelected(true);

                            break;
                        case 2:
                            cbJ2.setSelected(true);

                            break;
                        case 3:
                            cbJ3.setSelected(true);

                            break;
                    }

                }
                break;
            //selecciona elemento triada
            case "Fuego":
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
                if(!jugadorTurno.getNombre().equals("Computadora")){
                    EventHandler ePrimerValidacionElemento = new EventHandler<ActionEvent>() {
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
                                    alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);

                                } else {
                                    alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                                }
                            }

                        }

                    };
                    cb1Elemento.setOnAction(ePrimerValidacionElemento);
                    cb2Elemento.setOnAction(ePrimerValidacionElemento);
                    cb3Elemento.setOnAction(ePrimerValidacionElemento);
                }

                if(jugadorTurno.getNombre().equals("Computadora")){
                    alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                    cb1Elemento.setDisable(true);
                    cb2Elemento.setDisable(true);
                    cb3Elemento.setDisable(true);
                    int seleccionElemento=0;
                    seleccionElemento=(int) Math.floor(Math.random()*3+1);


                    switch (seleccionElemento){
                        case 1:
                            cb1Elemento.setSelected(true);
                            break;
                        case 2:
                            cb2Elemento.setSelected(true);
                            break;
                        case 3:
                            cb3Elemento.setSelected(true);
                            break;
                    }

                }
                break;
            //vuelve a tirar dado
            case "Agua":
                break;
        }


        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(800, 400);
        alert.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);

        if(cantidadJugadores ==2 && tipoPoder !="Fuego"){
            cbJ1.setSelected(true);
            cbJ1.setDisable(true);
            alert.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        }

        //Para validar botón
        Optional<ButtonType> result = alert.showAndWait();
        if
        (!result.isPresent()) {
        } else if (result.get() == ButtonType.OK) {
            switch (tipoPoder) {
                case "Planta":
                case "Electrico":
                case "Hielo":
                case "Roca":
                    if (cantidadJugadores == 3 || cantidadJugadores == 4) {
                        if (cbJ1.isSelected()) {
                            jugadorAAfectarPorPoder = cbJ1.getText();
                        }
                        if (cbJ2.isSelected()) {
                            jugadorAAfectarPorPoder = cbJ2.getText();
                        }
                        if (cantidadJugadores == 4) {
                            if (cbJ3.isSelected()) {
                                jugadorAAfectarPorPoder = cbJ3.getText();
                            }
                        }
                    } else {
                        if (cbJ1.isSelected()) {
                            jugadorAAfectarPorPoder = cbJ1.getText();
                        }
                    }
                    break;
                //selecciona elemento triada
                case "Fuego":
                    if (cb1Elemento.isSelected()) {
                        elementoAAfectarPorPoder = fabElementos.obtenerElemento(cb1Elemento.getText());
                    }
                    if (cb2Elemento.isSelected()) {
                        elementoAAfectarPorPoder = fabElementos.obtenerElemento(cb2Elemento.getText());
                    }
                    if (cb3Elemento.isSelected()) {
                        elementoAAfectarPorPoder = fabElementos.obtenerElemento(cb3Elemento.getText());
                    }
            }
        }
    }

    public void dialogoPoderAgua(String infoAgua){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Poder agua");

        GridPane gP = new GridPane();
        Text txt = new Text(infoAgua);
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));

        gP.add(txt, 0,0);
        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/Agua.png");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(100);
        imgZorvan.setFitWidth(100);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 200);
        alert.showAndWait();
    }

    public void dialogoPoderAguaStoneVencido(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Poder agua");

        GridPane gP = new GridPane();
        Text txt = new Text("El stone ya se venció. Poder agua no disponible.");
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));

        gP.add(txt, 0,0);
        ImageView imgZorvan = new ImageView();
        Image imgZ = new Image("/imgs/Agua.png");
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(100);
        imgZorvan.setFitWidth(100);
        gP.add(imgZorvan, 1, 0);
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 200);
        alert.showAndWait();
    }

    public void dialogoPoder(String infoPoder, String tipoPoder){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Poder");

        GridPane gP = new GridPane();
        Text txt = new Text(infoPoder);
        txt.setFont(Font.font("Matura MT Script Capitals", 20));
        txt.setFill(Color.rgb(58,54,21));
        gP.add(txt, 0,0);


        ImageView imgZorvan = new ImageView();
        String url = "";
        switch (tipoPoder){
            case "Fuego":
                url = "/imgs/Fuego.png";
                break;
            case "Roca":
                url = "/imgs/Roca.png";
                break;
            case "Hielo":
                url = "/imgs/Hielo.png";
                break;
            case "Electrico":
                url = "/imgs/Electrico.png";
                break;
            case "Planta":
                url = "/imgs/Planta.png";
                break;
        }

        Image imgZ = new Image(url);
        imgZorvan.setImage(imgZ);
        imgZorvan.setFitHeight(100);
        imgZorvan.setFitWidth(100);
        gP.add(imgZorvan, 0, 1);

        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 200);
        alert.showAndWait();
    }

    //Levanta diálogo Zorvan cuándo el jugador hace trampa
    public void dialogoZorvan(int cantCasillas) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Zorvan: " +
                "\n-¡Alto Ahí!" +
                "\nNo hay cabida para los tramposos, dévuelvete "+cantCasillas+" espacios.");
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

    //Levanta diálogo diablillo cuándo el jugador cae en casilla diablillo
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

    //Levanta diálogo querubin cuándo el jugador cae en casilla querubin
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

    //Levanta diálogo stone cuándo el jugador cae en casilla stone
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

    public void dialogoStoneYaMuerto() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");

        GridPane gP = new GridPane();
        Text txt = new Text("Stone: " +
                "\n-¡Es tu día de suerte!" +
                "\nYa me han vencido." +
                "\nPuedes avanzar. Suerte contra tus adversarios");
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

    //Levanta diálogo para indicar cuántas casillas el jugador debe avanzar según resultado dado
    public void dialogoMoverse(int resultadoDado) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        GridPane gP = new GridPane();
        if(resultadoDado == 0){
            Text txt0 = new Text("Estás bajo el efecto de un poder especial, no te puedes mover");
            txt0.setFont(Font.font("Matura MT Script Capitals", 20));
            txt0.setFill(Color.rgb(58,54,21));
            gP.add(txt0, 0,0);
        }else{
            Text txt = new Text("Avanza: " + resultadoDado +" casillas.");
            txt.setFont(Font.font("Matura MT Script Capitals", 20));
            txt.setFill(Color.rgb(58,54,21));
            gP.add(txt, 0,0);
        }
        alert.getDialogPane().setContent(gP);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(200, 150);
        alert.showAndWait();
    }

    //Observa si la casilla dónde cae jugador no es una casilla normal para disparar un evento
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
                if(nuevaPosicion>99){
                    resulNuevaPosicion=nuevaPosicion;
                    nuevaPosicion=99;
                }
                fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);

            }else if(value.equals("Stone")){
                int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                CasillaStoneAdapter casillaStone=(CasillaStoneAdapter) mc.partida.casillas.get(posicionActual);
                if(casillaStone.getVidaStone()>0){
                    dialogoStone();
                    //no se puede usar obtener turno porque mueve al jugador
                    mc.partida.turno.setInabilitado(true);
                }else{
                    dialogoStoneYaMuerto();
                }

                //para validar si el jugador no puede moverse hasta matar al stone
            }else if(value.equals("Zorvan")){
                int casillasExtras = resulNuevaPosicion - 99;
                if(ganador==false){
                    if(casillasExtras==0){
                        nombreGanadorJuego=jugadorTurno.getNombre();
                        terminaJuego = true;
                        ganador=true;
                        int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                        nuevaPosicion = 99;
                        mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);
                        fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                        fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                        dialogoZorvanGanador();
                    }else{
                        dialogoZorvan(casillasExtras);
                        int posicionActual=mc.obtenerPosicionJugador(jugadorTurno.ficha);
                        nuevaPosicion = mc.movimientoZorvan(casillasExtras);

                        fichaJugadorIv.setLayoutY(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutY());
                        fichaJugadorIv.setLayoutX(coordenadasCasillaFicha.get(nuevaPosicion).getLayoutX());
                        mc.moverFicha(posicionActual,nuevaPosicion,jugadorTurno.ficha);

                    }

                }

            }

        }
    }

    //Método que se muestra cuando un jugador ganó, indica lo acontecido y lo lleva a la vista final
    public void dialogoZorvanGanador() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");

        GridPane gP = new GridPane();
        Text txt = new Text("Zorvan: " +
                "\n-¡Excelente guerrero@ "+ nombreGanadorJuego+"!" +
                "\nHas vencido las artimañas del juego y lo has logrado dignamente. "+
                "\nEres el GANADOR del juego.");
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

        Stage stagePrevia = (Stage) btnTirarDado.getScene().getWindow();
        Optional<ButtonType> result = alert.showAndWait();
           if
            (!result.isPresent()) {
            }
            else if (result.get() == ButtonType.OK) {
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("/fxml/resultadoPartida.fxml"));
               loader.load();
               resultadoPartidaController controller = loader.<resultadoPartidaController>getController();
               controller.txtFieldGanador.setText(nombreGanadorJuego);
               Parent root = loader.getRoot();
               Platform.runLater(() -> root.requestFocus());
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.setTitle("Laberinto de Zorvan");
               stage.setResizable(false);
               stage.show();
               stagePrevia.hide();

            }
    }

}
