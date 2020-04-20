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


}