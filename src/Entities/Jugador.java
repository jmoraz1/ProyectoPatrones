package Entities;

import Patterns.Memento.Caretaker;
import Patterns.Memento.Memento;
import Patterns.Strategy.AtaqueElemento;

import java.util.Objects;

public class Jugador {
    public String nombre;
    public Ficha ficha;
    public Caretaker caretaker;
    public Boolean inabilitado=false;
    public int contadorHielo;
    public int contadorElectrico;
    public int contadorPlanta;
    public int contadorUpdates;




    public Jugador() {
        this.nombre = "";
        this.ficha = null;
        this.contadorElectrico=0;
        this.contadorHielo=0;
        this.contadorPlanta=0;
        this.contadorUpdates=0;
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

    public int getContadorHielo() {
        return contadorHielo;
    }

    public void setContadorHielo(int contadorHielo) {
        this.contadorHielo = contadorHielo;
    }

    public int getContadorPlanta() {
        return contadorPlanta;
    }

    public void setContadorPlanta(int contadorPlanta) {
        this.contadorPlanta = contadorPlanta;
    }

    public int getContadorElectrico() {
        return contadorElectrico;
    }

    public void setContadorElectrico(int contadorElectrico) {
        this.contadorElectrico = contadorElectrico;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(getNombre(), jugador.getNombre());
    }

}
