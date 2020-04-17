package Patterns.Decorator;

import Entities.DadoAtaque;
import Interfaces.IGirable;

public abstract class Decorador extends IGirable {
    IGirable dado;

    protected int res;

    public Decorador(IGirable dado) {
        this.dado = dado;
    }

    @Override
    public int girar() {
        return 0;
    }
}
