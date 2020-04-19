package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Roca extends AtaqueElemento{

    public Estrategia_Roca(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Electrico":
            case "Planta":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos de agua y hielo.
                return false;
        }
    }

    @Override
    public void Evaluar_Ventaja() {
        for (int j=0; j<getElementosContrincante().size();j++){
            if(((validacionDeElemento(getElementosContrincante().get(j).toString())))){
                setAtaque(15);
                setMejorElemento(getElementosContrincante().get(j));
            } else if((getNombreElemento() == getElementosContrincante().get(j).toString()) && (getAtaque() < 10)){
                setAtaque(10);
                setMejorElemento(getElementosContrincante().get(j));
            } else{
                setAtaque(5);
                setMejorElemento(getElementosContrincante().get(j) && (getAtaque() < 5));
            }
            getElementosContrincante().get(j).toString();
        }
    }
}