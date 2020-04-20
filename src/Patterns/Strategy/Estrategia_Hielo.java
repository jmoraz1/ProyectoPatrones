package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Hielo extends AtaqueElemento {

    public Estrategia_Hielo(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Agua":
            case "Planta":
            case "Roca":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos electricos y de fuego.
                return false;
        }
    }


}