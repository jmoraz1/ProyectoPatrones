package Patterns.Adapter;

import Entities.Casilla;
import Entities.Elemento;
import Entities.Stone;
import Interfaces.IPersonaje;
import Interfaces.IVisitor;
import Patterns.Visitor.RecibirAtaque;
import sun.plugin.dom.core.Element;

import java.util.ArrayList;

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

    public int getVidaStone(){
        return stone.getVida();
    }

    public void setElementosStone(ArrayList<Elemento>elementosStone){
        stone.setElementos(elementosStone);
    }
    public void setVidaStone(int vida){
         stone.setVida(vida);
    }
}
