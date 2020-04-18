package Patterns.Singleton;

import Interfaces.IGirable;

import static java.lang.Math.*;

public class DadoMovimiento extends IGirable {
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

    public int girarLimitado() {
        int valor = (int) floor(random()*3+1);
        return valor;
    }
}
