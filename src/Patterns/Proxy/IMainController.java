package Patterns.Proxy;

import Entities.Jugador;
import Entities.Tablero;
import MainController.MainController;

import java.io.IOException;
import java.util.ArrayList;

public interface IMainController {
    MainController obtenerMainController( String psw) throws IOException;
}
