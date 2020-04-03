package Patterns.Prototype;

import Entities.Casilla;

public class CasillaZorvan extends Casilla {

    public CasillaZorvan(int numero) {
        this.setNumero(numero);
        this.setTipo("Zorvan");

    }

    @Override
    public CasillaZorvan clone() {
        return new CasillaZorvan(this.getNumero());

    }

    @Override
    public String accion() {
        return "Has ganado el juego! :v";
    }
}
