package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Roca extends AtaqueElemento {

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
            // Si es un elemento con ventaja entonces el ataque es de 15
            if(((validacionDeElemento(getElementosContrincante().get(j).toString()))) && (getAtaque() < 15)){
                setAtaque(15);
                setMejorElemento(getElementosContrincante().get(j).toString());
                // Si es el mismo elemento
            } else if((getNombreElemento().equals(getElementosContrincante().get(j).toString())) && (getAtaque() < 10)){
                setAtaque(10);
                setMejorElemento(getElementosContrincante().get(j).toString());
                // Si es un el elemento sin ventaja entonces el ataque es de 5
            } else {
                setAtaque(5);
                setMejorElemento(getElementosContrincante().get(j).toString());
            }
        }
    }
}