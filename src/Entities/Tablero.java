package Entities;

import Patterns.Prototype.Manager;

import java.util.ArrayList;



public class Tablero {

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
}
