package Logica.Adaptador.Adaptadores;

import Logica.Prototipo.iPrototipo.Casilla;
import Logica.Stone;

public class CasillaStoneAdapter extends Casilla{
        Stone stone;

        public CasillaStoneAdapter(int numero, String ficha) {
            this.setNumero(numero);
            this.setFicha("aca va el objeto ficha");
            this.setTipo("Stone");
            this.setStone();
        }

        public Stone getStone() {
            return stone;
        }

        private void setStone() {
            this.stone = generarStone();
        }

        private Stone generarStone(){
            return new Stone();

        }

    @Override
    public Casilla clone() {
        return new CasillaStoneAdapter(this.getNumero(), this.getFicha());
    }

    @Override
    public String accion() {
        return stone.mostrarElementosYVida();
    }
}
