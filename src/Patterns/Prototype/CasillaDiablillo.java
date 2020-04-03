package Patterns.Prototype;

import Entities.Casilla;

public class CasillaDiablillo extends Casilla {

    public CasillaDiablillo(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Diablito");
    }



    @Override
    public Casilla clone() {
        return new CasillaDiablillo(this.getNumero(), this.getFicha());
    }

    public String accion() {
        return "Te voy a atrasar 10 espacios! >:)";
    }
}
