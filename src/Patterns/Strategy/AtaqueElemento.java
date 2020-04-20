package Patterns.Strategy;
import Entities.Elemento;
import Interfaces.IAtaqueElemento;

import javax.xml.bind.Element;
import java.util.ArrayList;

public abstract class AtaqueElemento implements IAtaqueElemento{
    private int ataque = 0;
    private String mejorElemento;

    private String nombreElemento;
    private ArrayList<Elemento> elementosContrincante = new ArrayList<Elemento>();

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

    public ArrayList<Elemento> getElementosContrincante() {
        return this.elementosContrincante;
    }

    protected void setElementosContrincante(ArrayList<Elemento> elementosContrincante) {
        this.elementosContrincante = elementosContrincante;
    }

    public abstract boolean validacionDeElemento(String nombreElemento);

    @Override
    public  void Evaluar_Ventaja(){

            int valorAtaque = 0;
            for (int j=0; j<getElementosContrincante().size();j++){
                // Si es un elemento con ventaja entonces el ataque es de 15
                if(validacionDeElemento(getElementosContrincante().get(j).getTipo())){
                    if(valorAtaque < 15){
                        valorAtaque = 15;
                    }
                    // Si es el mismo elemento
                } else if(getNombreElemento().equals(getElementosContrincante().get(j).getTipo())){
                    if(valorAtaque < 10){
                        valorAtaque = 10;
                    }
                    // Si es un el elemento sin ventaja entonces el ataque es de 5
                } else {
                    if(valorAtaque > 5){
                    }else{
                        valorAtaque = 5;
                    }
                }
            }
            setAtaque(valorAtaque);


    }
}