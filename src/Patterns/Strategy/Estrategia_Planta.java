package Patterns.Strategy;

import java.util.ArrayList;

public class Estrategia_Planta extends AtaqueElemento {

    public Estrategia_Planta(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Agua":
            case "Electrico":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos de fuego, hielo y roca.
                return false;
        }
    }


}
