package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Electrico extends AtaqueElemento {

    public Estrategia_Electrico(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Agua":
            case "Hielo":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos de planta y de roca.
                return false;
        }
    }



}
