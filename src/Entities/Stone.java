package Entities;

import Patterns.FactoryMethod.FabricaElementos;

import java.util.*;

public class Stone {
    int vida;
    ArrayList<Elemento> elementos= new ArrayList<Elemento>();

    public Stone() {
        this.vida = 100;
        this.elementos = generarElementos();
    }

    public Stone(int vida, ArrayList<Elemento> elementos){
        this.vida = vida;
        this.elementos=elementos;
    }

    private ArrayList<Elemento> generarElementos() {
        FabricaElementos fabrica=new FabricaElementos();

        Random rand = new Random();
        List<String> elementosDisponibles = new ArrayList<>();
        elementosDisponibles.add("Agua");
        elementosDisponibles.add("Fuego");
        elementosDisponibles.add("Planta");
        elementosDisponibles.add("Electrico");
        elementosDisponibles.add("Roca");
        elementosDisponibles.add("Hielo");
        ArrayList<Elemento>elementosStone=new ArrayList<Elemento>();
        int contadorElementos = 3;

        for (int i = 0; i < contadorElementos; i++) {
            int randomIndex = rand.nextInt(elementosDisponibles.size());
            elementosStone.add(fabrica.obtenerElemento(elementosDisponibles.get(randomIndex)));
        }
        return elementosStone;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public ArrayList<Elemento> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<Elemento> elementos) {
        this.elementos = elementos;
    }

    public String mostrarElementosYVida() {
        String estado="Vida: "+getVida();
        for (int i = 0; i < elementos.size(); i++){
            estado+=elementos.get(i).getTipo();
        }
        return estado;
    }

    public void dobleStone(){
        this.vida= getVida()+60;
    }
}
