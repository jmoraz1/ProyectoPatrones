package Patterns.Strategy;

public class Estrategia_Agua extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Agua(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Fuego":
            case "Roca":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos de agua, electrico, hielo y planta.
                this.validacion = false;
                break;
        }
        return validacion;
    }

    @Override
    public boolean Evaluar_Ventaja() {
        return validacionDeElemento(getNombreElemento());
    }
}