package Patterns.Strategy;
import Interfaces.IAtaqueElemento;

import java.util.ArrayList;

public abstract class AtaqueElemento implements IAtaqueElemento{
    private int ataque = 0;
    private String nombreElemento;
    private String mejorElemento;
    private ArrayList elementosContrincante;

    public AtaqueElemento(String nombreElemento, ArrayList elementosContrincante) {
        this.nombreElemento = nombreElemento;
        this.elementosContrincante = elementosContrincante;
    }

    @Override
    public abstract void Evaluar_Ventaja();

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
}