package Patterns.Prototype;

import Entities.Casilla;
import Patterns.Adapter.CasillaStoneAdapter;

import java.util.ArrayList;
import java.util.Random;

public class Manager {
    private CasillaNormal normal = new CasillaNormal(0,"ninguna" );
    private CasillaZorvan zorvan = new CasillaZorvan(0,"ninguna" );
    private CasillaDiablillo diablito = new CasillaDiablillo(0,"ninguna" );
    private CasillaQuerubin querubin = new CasillaQuerubin(0,"ninguna" );
    private CasillaStoneAdapter stone = new CasillaStoneAdapter(0,"ninguna" );

    ArrayList<Casilla> casillas=new ArrayList<Casilla>();


    public  ArrayList<Casilla>  generarCasillas() {
        for (int i = 0; i < 100; i++) {
            casillas.add(normal.clone());
        }
        casillas=incluirDiablitos(casillas);
        casillas=incluirQuerubines(casillas);
        casillas=incluirStones(casillas);
        casillas.set(99,zorvan.clone());
        return casillas;
    }

    public String imprimir() {
        String lista="";
        int index=casillas.size()-1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <20; j++) {
                lista+=casillas.get(index).getTipo();
                index--;
            }
            lista+="\n";
        }
        return lista;

    }

    private ArrayList<Casilla> incluirStones(ArrayList<Casilla> casillas) {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,stone.clone());
        }

        return casillas;
    }

    private ArrayList<Casilla> incluirQuerubines(ArrayList<Casilla> casillas) {
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,querubin.clone());
        }

        return casillas;
    }

    private ArrayList<Casilla> incluirDiablitos(ArrayList<Casilla>  casillas){
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,diablito.clone());
        }

        return casillas;
    }
}