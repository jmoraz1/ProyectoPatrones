package Patterns.FactoryMethod;

import Entities.Elemento;
import Entities.Personaje;
import Interfaces.IMetodoFabrica;

public class FabricaPersonajes implements IMetodoFabrica {
    @Override
    public Personaje crearPersonaje(String Elemento) {
        Personaje tmpPersonaje;
        Elemento tmpElemento;
        switch (Elemento){
            case "Fuego":
                tmpElemento=new Elemento(Elemento);
                break;
            case "Agua":
                tmpElemento=new Elemento(Elemento);
                break;
            case "Planta":
                tmpElemento= new Elemento(Elemento);
                break;
            case "Electrico":
                tmpElemento=new Elemento(Elemento);
                break;
            case "Roca":
                tmpElemento=new Elemento(Elemento);
                break;
            default:
                //en este caso sería Hielo
                tmpElemento=new Elemento(Elemento);
                break;

        }
        return tmpPersonaje= new Personaje(tmpElemento);
    }
}
