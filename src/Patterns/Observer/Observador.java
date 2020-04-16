package Patterns.Observer;

import java.io.IOException;
import java.io.Serializable;

public interface Observador {
    void update(String value) throws IOException;
}