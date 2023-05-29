
package componentes;

import java.util.ArrayList;
import java.util.List;

public class Fila<T> {
    private List<T> itens;

    public Fila() {
        this.itens = new ArrayList<>();
    }

    public void enqueue(T elemento) {
        this.itens.add(elemento);
    }

    public T dequeue() {
        return this.itens.remove(0); //Nao sei se era isso que o codigo em JS estava fazendo, favor checar
    }
}