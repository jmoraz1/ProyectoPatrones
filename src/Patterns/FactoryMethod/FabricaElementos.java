package Patterns.FactoryMethod;

import Entities.Elemento;
import Entities.Personaje;
import Interfaces.IMetodoFabrica;

public class FabricaElementos implements IMetodoFabrica {
    @Override
    public Personaje crearPersonaje(String Elemento) {
        return new Personaje(obtenerElemento(Elemento));
    }

    public Elemento obtenerElemento(String Elemento){
        Elemento tmpElemento;
        switch (Elemento){
            case "Fuego":
                tmpElemento=new Fuego();
                break;
            case "Agua":
                tmpElemento=new Agua();
                break;
            case "Planta":
                tmpElemento= new Planta();
                break;
            case "Electrico":
                tmpElemento=new Electrico();
                break;
            case "Roca":
                tmpElemento=new Roca();
                break;
            default:
                //en este caso sería Hielo
                tmpElemento=new Hielo();
                break;
        }
        return tmpElemento;
    }
}
