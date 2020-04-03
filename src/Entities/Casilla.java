package Entities;

import Interfaces.Observador;
import Interfaces.Sujeto;

import java.util.ArrayList;
import java.util.List;

public abstract class Casilla implements Sujeto {
    int numero;
    List<Ficha> fichas = new ArrayList<Ficha>();
    String tipo;
    public abstract String accion();
    private List<Observador> observers = new ArrayList<Observador>();



    public abstract Casilla clone();

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFicha(Ficha ficha) {
        this.fichas.add(ficha);
        notifyObservers();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }



    @Override
    public void addObserver(Observador o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observador o) {

    }

    @Override
    public void notifyObservers() {
        for(Observador o : observers){
            o.update(this.tipo);
        }
    }

}
