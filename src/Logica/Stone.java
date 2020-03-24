package Logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stone {
    int vida;
    ArrayList<String> elementos= new ArrayList<String>();

    public Stone() {
        this.vida = 100;
        this.elementos = generarElementos();
    }

    private ArrayList<String> generarElementos() {
        Random rand = new Random();
        List<String> elementosDisponibles = List.of("Agua", "Fuego","Planta","Electrico","Roca","Hielo");
        ArrayList<String>elementosStone=new ArrayList<String>();
        int contadorElementos = 3;

        for (int i = 0; i < contadorElementos; i++) {
            int randomIndex = rand.nextInt(elementosDisponibles.size());
            elementosStone.add(elementosDisponibles.get(randomIndex));
        }
        return elementosStone;
    }







}
