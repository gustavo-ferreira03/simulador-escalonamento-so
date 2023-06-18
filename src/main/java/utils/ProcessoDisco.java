package utils;

import componentes.Processo;

public class ProcessoDisco {
    private Processo processo;
    private int tempoDecorrido;

    public ProcessoDisco(Processo processo) {
        this.processo = processo;
        this.tempoDecorrido = 0;
    }

    public boolean verificarConclusao() {
        return this.tempoDecorrido == processo.getDuracaoIO();
    }

    public void incrementarTempoDecorrido() {
        this.tempoDecorrido++;
    }

    public Processo getProcesso() {
        return processo;
    }

    public int getTempoDecorrido() {
        return tempoDecorrido;
    }
}
