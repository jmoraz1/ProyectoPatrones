package Logica.Prototipo.Prototipo;

import Logica.Prototipo.iPrototipo.Casilla;

public class CasillaZorvan extends Casilla {
    public CasillaZorvan(int numero, String ficha) {
        this.setNumero(numero);
        this.setFicha("aca va el objeto ficha");
        this.setTipo("Diablito");

    }


    @Override
    public CasillaDiablito clone() {
        return new CasillaDiablito(this.getNumero(), this.getFicha());

    }
}
