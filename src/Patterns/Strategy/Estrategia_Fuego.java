package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Fuego extends AtaqueElemento {

    public Estrategia_Fuego(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Hielo":
            case "Planta":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos de agua, electricos y de roca.
                return false;
        }
    }


}