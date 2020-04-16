package Entities;

import Patterns.Memento.Caretaker;
import Patterns.Memento.Memento;
import Patterns.Strategy.AtaqueElemento;

public class Jugador {
    public String nombre;
    public Ficha ficha;
    public Caretaker caretaker;
    public Boolean inabilitado=false;



    public Jugador() {
        this.nombre = "";
        this.ficha = null;

    }

    public Jugador(String nombre, Ficha ficha) {
        this.nombre = nombre;
        this.ficha = ficha;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(int turnos, AtaqueElemento ataque) {
        this.caretaker = new Caretaker();
        caretaker.setMemento(new Memento(turnos, ataque));
    }

    public Boolean getInabilitado() {
        return inabilitado;
    }

    public void setInabilitado(Boolean inabilitado) {
        this.inabilitado = inabilitado;
    }
}
