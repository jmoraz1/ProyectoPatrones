package Patterns.Observer.Interfaces;

import java.io.Serializable;

public interface Observador {
    void update(Serializable value);
}