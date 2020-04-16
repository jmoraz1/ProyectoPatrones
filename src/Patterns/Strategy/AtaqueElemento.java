package Patterns.Strategy;
import Interfaces.IAtaqueElemento;
public abstract class AtaqueElemento implements IAtaqueElemento{
    private String nombreElemento;

    public AtaqueElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    @Override
    public abstract boolean Evaluar_Ventaja();

    public String getNombreElemento() {
        return this.nombreElemento;
    }

    protected void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }
}