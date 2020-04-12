package clasesUI;

public class ElementoTabla {

    public String PoderEspecial;
    public String Elemento;
    public String Fuego;
    public String Agua;
    public String Planta;
    public String Eléctrico;
    public String Roca;
    public String Hielo;

    public ElementoTabla(){

    }
    public ElementoTabla(String poderEspecial, String elemento, String fuego, String agua, String planta, String eléctrico, String roca, String hielo) {
        PoderEspecial = poderEspecial;
        Elemento = elemento;
        Fuego = fuego;
        Agua = agua;
        Planta = planta;
        Eléctrico = eléctrico;
        Roca = roca;
        Hielo = hielo;
    }

    public String getPoderEspecial() {
        return PoderEspecial;
    }

    public void setPoderEspecial(String poderEspecial) {
        PoderEspecial = poderEspecial;
    }

    public String getElemento() {
        return Elemento;
    }

    public void setElemento(String elemento) {
        Elemento = elemento;
    }

    public String getFuego() {
        return Fuego;
    }

    public void setFuego(String fuego) {
        Fuego = fuego;
    }

    public String getAgua() {
        return Agua;
    }

    public void setAgua(String agua) {
        Agua = agua;
    }

    public String getPlanta() {
        return Planta;
    }

    public void setPlanta(String planta) {
        Planta = planta;
    }

    public String getEléctrico() {
        return Eléctrico;
    }

    public void setEléctrico(String eléctrico) {
        Eléctrico = eléctrico;
    }

    public String getRoca() {
        return Roca;
    }

    public void setRoca(String roca) {
        Roca = roca;
    }

    public String getHielo() {
        return Hielo;
    }

    public void setHielo(String hielo) {
        Hielo = hielo;
    }
}
