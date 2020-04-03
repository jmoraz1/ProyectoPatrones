package Entities;

import Interfaces.IGirable;

import static java.lang.Math.floor;
import static java.lang.Math.random;

public class DadoAtaque implements IGirable {

    private static DadoAtaque instance;
    int[] ataques;

    private DadoAtaque() {
        this.ataques = new int[]{1,2,3,4,5,6};
    }

    public static DadoAtaque getInstance(){
        if( instance == null){
            instance = new DadoAtaque();
        }
        return instance;
    }


    @Override
    public int girar() {
        int valor = (int) floor(random()*6+1);
        return ataques[valor];
    }
}