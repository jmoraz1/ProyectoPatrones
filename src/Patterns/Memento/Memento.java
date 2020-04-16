package Patterns.Memento;

import Patterns.Strategy.AtaqueElemento;

public class Memento {
    int turnos;
    AtaqueElemento ataqueRecibido;

    public Memento(int turnos, AtaqueElemento ataqueRecibido) {
        this.turnos = turnos;
        this.ataqueRecibido = ataqueRecibido;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public AtaqueElemento getAtaqueRecibido() {
        return ataqueRecibido;
    }

    public void setAtaqueRecibido(AtaqueElemento ataqueRecibido) {
        this.ataqueRecibido = ataqueRecibido;
    }
}
