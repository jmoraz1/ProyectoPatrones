package Patterns.Adapter;

import Entities.Casilla;
import Entities.Stone;
import Interfaces.IPersonaje;
import Interfaces.IVisitor;
import Patterns.Visitor.RecibirAtaque;

public class CasillaStoneAdapter extends Casilla implements IPersonaje {
    public Stone stone;

    public CasillaStoneAdapter(int numero) {
        this.setNumero(numero);
        this.setTipo("Stone");
        this.setStone();
    }

    public CasillaStoneAdapter(Stone stone) {
        this.setNumero(0);
        this.setTipo("Stone");
        this.stone = stone;
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

    public void dobleStone(){
        this.getStone().dobleStone();
    }

    @Override
    public Casilla clone() {
        return new CasillaStoneAdapter(this.getNumero());
    }

    @Override
    public String accion() {
        return stone.mostrarElementosYVida();
    }

    @Override
    public void accept( IVisitor visitor, int amount){
        if( visitor.getClass().equals( RecibirAtaque.class ) )

            visitor.visit(this, amount);
    }


}
