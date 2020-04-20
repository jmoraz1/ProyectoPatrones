package Patterns.Strategy;

import Entities.Elemento;
import Interfaces.IAtaqueElemento;

import java.util.ArrayList;

public class Estrategia_Agua extends AtaqueElemento {

    public Estrategia_Agua(String nombreElemento, ArrayList ElementosContrincante) {
        super(nombreElemento, ElementosContrincante);
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Roca":
                return true;
            default:
                //en este caso de que no tenga ventaja como los elementos electrico, hielo y planta.
                return false;
        }
    }


}
