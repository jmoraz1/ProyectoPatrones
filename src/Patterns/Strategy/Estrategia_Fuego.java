package Patterns.Strategy;

public class Estrategia_Fuego extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Fuego(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Hielo":
            case "Planta":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos de agua, electricos, fuego y de roca.
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