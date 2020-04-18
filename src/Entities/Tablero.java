package Entities;

import Interfaces.IGirable;
import Patterns.Prototype.Manager;
import Patterns.Singleton.DadoMovimiento;
import controllers.tableroPartidaController;

import java.util.ArrayList;



public class Tablero  {

    static Manager manager= new Manager();
    static int numTurno=0;

    public ArrayList<Casilla>casillas;
    public Jugador turno;
    public DadoMovimiento dadoMovimiento;
    public IGirable dadoAtaque;
    public Jugador[] jugadores;

    public Tablero(Jugador[] jugadores) {
        this.casillas =manager.generarCasillas() ;
        this.dadoMovimiento = DadoMovimiento.getInstance();
        this.dadoAtaque = DadoAtaque.getInstance();
        this.jugadores = jugadores;
        this.turno = jugadores[numTurno];

    }

    public Jugador siguienteTurno(){
        if (numTurno==jugadores.length-1){
            numTurno=0;
            turno= jugadores[numTurno];
        }else {
            numTurno++;
            turno = jugadores[numTurno];
        }
        return turno;
    }

    public int obtenerNumeroMovimiento(){ return dadoMovimiento.girar();}

    public int obtenerTipoAtaque(){
        return dadoAtaque.girar();
    }

    public void observarCasillas(tableroPartidaController o){
        //Subscribir el tablero a las casillas
        for (Casilla casilla :casillas){
            casilla.addObserver(o);
        }

    }

    public Jugador obtenerJugador(String j){
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].getNombre()==j){
                return this.jugadores[i];
            }
        }
        return null;
    }
}
