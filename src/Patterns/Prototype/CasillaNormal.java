package Patterns.Prototype;

import Entities.Casilla;

public class CasillaNormal extends Casilla {
    public CasillaNormal(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Normal");

    }

    @Override
    public CasillaNormal clone() {
        return new CasillaNormal(this.getNumero(), this.getFicha());

    }

    @Override
    public String accion() {
        return "Puede descansar aqui.";
    }
}
