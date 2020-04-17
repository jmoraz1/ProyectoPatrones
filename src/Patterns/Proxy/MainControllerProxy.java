package Patterns.Proxy;

import Entities.Jugador;
import Entities.Tablero;
import MainController.MainController;

import java.io.IOException;
import java.util.ArrayList;

public class MainControllerProxy implements IMainController{
    @Override
    public MainController obtenerMainController( String psw) throws IOException {
        if (psw== "admin"){
            MainController controller= new MainController();
            return controller.obtenerMainController(psw);
        }else{
            return null;
        }
    }
}
