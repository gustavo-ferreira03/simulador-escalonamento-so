package componentes;

import relatorio.Relatorio;

public class SistemaOperacional {
    private Escalonador escalonador;
    private Relatorio relatorio;

    public SistemaOperacional(Relatorio relatorio) {
        this.escalonador = new Escalonador(this, 2);
        this.relatorio = relatorio;
    }

    public Escalonador getEscalonador() {
        return escalonador;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }
}
