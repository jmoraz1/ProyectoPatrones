package Patterns.Prototype;

import Entities.Casilla;

public class CasillaNormal extends Casilla {
    public CasillaNormal(int numero) {
        this.setNumero(numero);

        this.setTipo("Normal");

    }

    @Override
    public CasillaNormal clone() {
        return new CasillaNormal(this.getNumero());

    }

    @Override
    public String accion() {
        return "Puede descansar aqui.";
    }
}
