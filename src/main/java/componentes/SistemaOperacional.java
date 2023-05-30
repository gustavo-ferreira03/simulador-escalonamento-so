package componentes;

public class SistemaOperacional {
    private Escalonador escalonador;

    public SistemaOperacional() {
        this.escalonador = new Escalonador(2);
    }

    public Escalonador getEscalonador() {
        return escalonador;
    }
}
