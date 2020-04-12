package main;

import java.util.ArrayList;

public class ControladorPrueba {

    public ArrayList<String> nombreJugadores = new ArrayList<String>();

    public ControladorPrueba(){

    }

    public void agregarJugadores(String nombre){
        nombreJugadores.add(nombre);
    }

    public ArrayList<String> retornoNombreJugadores(){
        return nombreJugadores;
    }

}
