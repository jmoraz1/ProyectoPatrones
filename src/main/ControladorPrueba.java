package main;

import Entities.Jugador;

import java.util.ArrayList;

public class ControladorPrueba {

    public ArrayList<String> nombreJugadores = new ArrayList<String>();
    public ArrayList<Jugador> arregloJugadores = new ArrayList<>();
    public String adminClave = "admin";

    public ControladorPrueba(){
        adminClave = "admin";
    }

    public void agregarJugadores(String nombre){
        nombreJugadores.add(nombre);
        Jugador jug = new Jugador();
        jug.setNombre(nombre);
        arregloJugadores.add(jug);
    }

    public ArrayList<String> retornoNombreJugadores(){
        return nombreJugadores;
    }

}
