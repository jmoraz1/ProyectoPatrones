package Patterns.Prototype;

import Entities.Casilla;

public class CasillaQuerubin extends Casilla {

    public CasillaQuerubin(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Querubin");

    }


    @Override
    public CasillaQuerubin clone() {
        return new CasillaQuerubin(this.getNumero(), this.getFicha());

    }

    @Override
    public String accion() {
        return "Te voy a adelantar 11 espacios! :3";
    }
}
