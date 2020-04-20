package Patterns.Prototype;

import Entities.Casilla;
import Entities.Elemento;
import Patterns.Adapter.CasillaStoneAdapter;

import java.util.ArrayList;
import java.util.Random;

public class Manager {
    private CasillaNormal normal = new CasillaNormal(0);
    private CasillaZorvan zorvan = new CasillaZorvan(0 );
    private CasillaDiablillo diablito = new CasillaDiablillo(0 );
    private CasillaQuerubin querubin = new CasillaQuerubin(0);
    private CasillaStoneAdapter stone = new CasillaStoneAdapter(0 );

    ArrayList<Casilla> casillas=new ArrayList<Casilla>();


    public  ArrayList<Casilla>  generarCasillas() {
        for (int i = 0; i < 100; i++) {
            casillas.add(normal.clone());
        }
        casillas=incluirDiablitos(casillas);
        casillas=incluirQuerubines(casillas);
        casillas=incluirStones(casillas);
        casillas.set(99,zorvan.clone());
        casillas.set(0,normal.clone());
        casillas.set(88, normal.clone());
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
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,stone.clone());
        }

        return casillas;
    }

    public CasillaStoneAdapter generarCasillaStone(){
        return ((CasillaStoneAdapter)stone.clone());
    }

    public void setElementosStone (ArrayList<Elemento >elementos, Casilla casilla){
        CasillaStoneAdapter casillaStoneAdapter=(CasillaStoneAdapter)casilla;
        casillaStoneAdapter.setElementosStone(elementos);
    }

    private ArrayList<Casilla> incluirQuerubines(ArrayList<Casilla> casillas) {
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,querubin.clone());
        }

        return casillas;
    }

    private ArrayList<Casilla> incluirDiablitos(ArrayList<Casilla>  casillas){
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            int randomIndex = rand.nextInt(casillas.size()-1);
            casillas.set(randomIndex,diablito.clone());
        }

        return casillas;
    }
}
