package Patterns.Strategy;

public class Estrategia_Hielo extends AtaqueElemento{

    private boolean validacion;

    public Estrategia_Hielo(String nombreElemento, boolean validacion) {
        super(nombreElemento);
        this.validacion = validacion;
    }

    public boolean validacionDeElemento(String tipo_elemento) {
        switch (tipo_elemento){
            case "Agua":
            case "Planta":
            case "Roca":
                this.validacion = true;
                break;
            default:
                //en este caso de que no tenga ventaja como los elementos electricos, fuego y hielo.
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