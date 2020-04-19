package Patterns.Strategy;
import Entities.Elemento;
import Interfaces.IAtaqueElemento;

import javax.xml.bind.Element;
import java.util.ArrayList;

public abstract class AtaqueElemento implements IAtaqueElemento{
    private int ataque = 0;
    private String mejorElemento;

    private String nombreElemento;
    private ArrayList<Elemento> elementosContrincante;

    public AtaqueElemento(String nombreElemento, ArrayList elementosContrincante) {
        this.nombreElemento = nombreElemento;
        this.elementosContrincante = elementosContrincante;
    }

    public int getAtaque() {
        return this.ataque;
    }

    protected void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public String getNombreElemento() {
        return this.nombreElemento;
    }

    protected void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public ArrayList getElementosContrincante() {
        return this.elementosContrincante;
    }

    protected void setElementosContrincante(ArrayList elementosContrincante) {
        this.elementosContrincante = elementosContrincante;
    }
    public String getMejorElemento() {
        return this.mejorElemento;
    }

    protected void setMejorElemento(String mejorElemento) {
        this.mejorElemento = mejorElemento;
    }

    @Override
    public abstract void Evaluar_Ventaja();
}