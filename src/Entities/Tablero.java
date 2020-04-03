package Entities;

import Interfaces.Observador;
import Patterns.Prototype.Manager;

import java.io.Serializable;
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
        this.turno = jugadores[numTurno];
        this.dadoMovimiento = DadoMovimiento.getInstance();
        this.dadoAtaque = DadoAtaque.getInstance();
        this.jugadores = jugadores;
        observarCasillas();

    }

    public Jugador siguienteTurno(){
        if (numTurno==jugadores.length-1){
            numTurno=0;
            turno= jugadores[numTurno];
        }else {
            numTurno=numTurno++;
            turno = jugadores[numTurno];
        }
        return turno;
    }

    public int obtenerNumeroMovimiento(){ return dadoMovimiento.girar();}

    public int obtenerTipoAtaque(){
        return dadoMovimiento.girar();
    }

    @Override
    public void update(Serializable value) {
        System.out.println("Se asigno una ficha a una casilla tipo"+value);
    }

    private void observarCasillas(){
        //Subscribir el tablero a las casillas
        for (Casilla casilla :casillas){
            casilla.addObserver(this);
        }

    }
}
