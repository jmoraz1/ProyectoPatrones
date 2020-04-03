package Entities;

import Interfaces.IGirable;

import static java.lang.Math.*;

public class DadoMovimiento implements IGirable {
    private static DadoMovimiento instance;


    private DadoMovimiento(){}

    public static DadoMovimiento getInstance(){
        if( instance == null){
             instance = new DadoMovimiento();
        }
        return instance;
    }

    @Override
    public int girar() {
        int valor = (int) floor(random()*6+1);
        return valor;
    }
}
