package Patterns.Observer;

import java.io.IOException;
import java.io.Serializable;

public interface Observador {
    String update(String value) throws IOException;
}