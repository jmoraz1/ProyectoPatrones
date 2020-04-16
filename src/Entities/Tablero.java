package Entities;

import Patterns.Observer.Observador;
import Patterns.Prototype.Manager;
import Patterns.Singleton.DadoMovimiento;

import java.util.ArrayList;



public class Tablero implements Observador {

    static Manager manager= new Manager();
    static int numTurno=0;

    public ArrayList<Casilla>casillas;
    public Jugador turno;
    public DadoMovimiento dadoMovimiento;
    public DadoAtaque dadoAtaque;
    public Jugador[] jugadores;

    public Tablero(Jugador[] jugadores) {
        this.casillas =manager.generarCasillas() ;
        this.dadoMovimiento = DadoMovimiento.getInstance();
        this.dadoAtaque = DadoAtaque.getInstance();
        this.jugadores = jugadores;
        this.turno = jugadores[numTurno];
        observarCasillas();

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
        return dadoMovimiento.girar();
    }

    @Override
    public String update(String value) {
        return value;
    }

    private void observarCasillas(){
        //Subscribir el tablero a las casillas
        for (Casilla casilla :casillas){
            casilla.addObserver(this);
        }

    }
}
