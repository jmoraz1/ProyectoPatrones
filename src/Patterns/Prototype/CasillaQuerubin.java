package Patterns.Prototype;

import Entities.Casilla;

public class CasillaQuerubin extends Casilla {

    public CasillaQuerubin(int numero) {
        this.setNumero(numero);
        this.setTipo("Querubin");

    }


    @Override
    public CasillaQuerubin clone() {
        return new CasillaQuerubin(this.getNumero());

    }
    @Override
    public String accion() {
        return "Te voy a adelantar 11 espacios! :3";
    }


}
