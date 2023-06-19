package utils;

import componentes.Processo;

public class SegmentoMemoria {
    private int numQuadros;
    private Processo processo;

    public SegmentoMemoria(Processo processo, int numQuadros) {
        this.processo = processo;
        this.numQuadros = numQuadros;
    }

    public boolean livre() {
        return processo == null;
    }

    public int getNumQuadros() {
        return numQuadros;
    }

    public void setNumQuadros(int numQuadros) {
        this.numQuadros = numQuadros;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }
}
