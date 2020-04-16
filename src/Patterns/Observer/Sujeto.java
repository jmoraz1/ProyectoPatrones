package Patterns.Observer;

import java.io.IOException;

public interface Sujeto {
    void addObserver(Observador o);
    void notifyObservers() throws IOException;
}
