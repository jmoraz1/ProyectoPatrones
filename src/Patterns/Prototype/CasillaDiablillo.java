package Patterns.Prototype;

import Entities.Casilla;
import Patterns.Observer.Observador;

import java.util.ArrayList;
import java.util.List;

public class CasillaDiablillo extends Casilla {
    public String accion() {
        return "Te voy a atrasar 10 espacios! >:)";
    }
    private List<Observador> observers = new ArrayList<Observador>();
    private Integer value;

    public CasillaDiablillo(int numero) {
        this.setNumero(numero);
        this.setTipo("Diablito");
    }

    @Override
    public Casilla clone() {
        return new CasillaDiablillo(this.getNumero());
    }



}
