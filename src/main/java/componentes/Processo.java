package componentes;

import utils.Prioridade;

public class Processo {
    private String nome;
    private int tempoChegada;
    private Prioridade prioridade;
    private int tempoCPU;
    private int tamanho;
    private int disco;
    private int duracaoIO;
    private int tempoDecorrido;
    private int inicioIO;
    private int rq;

    public Processo(String nome, int tempoChegada, Prioridade prioridade, int tempoCPU, int tamanho, int disco, int inicioIO, int duracaoIO) {
        this.nome = nome;
        this.tempoChegada = tempoChegada;
        this.prioridade = prioridade;
        this.tempoCPU = tempoCPU;
        this.tamanho = tamanho;
        this.disco = disco;
        this.inicioIO = inicioIO;
        this.duracaoIO = duracaoIO;
        this.rq = -1;
    }

    public double getProgresso() {
        return Math.round(10000 * (double) tempoDecorrido / tempoCPU) / 100.0;
    }

    public String getNome() {
        return nome;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public int getTempoCPU() {
        return tempoCPU;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getDisco() {
        return disco;
    }

    public int getDuracaoIO() {
        return duracaoIO;
    }

    public int getTempoDecorrido() {
        return tempoDecorrido;
    }

    public int getInicioIO() {
        return inicioIO;
    }

    public int getRq() {
        return rq;
    }

    public void resetRq() {
        this.rq = -1;
    }

    public void incrementaRq() {
        if(this.rq < 2) {
            this.rq++;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTempoChegada(int tempoChegada) {
        this.tempoChegada = tempoChegada;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setTempoCPU(int tempoCPU) {
        this.tempoCPU = tempoCPU;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setDisco(int disco) {
        this.disco = disco;
    }

    public void setDuracaoIO(int duracaoIO) {
        this.duracaoIO = duracaoIO;
    }

    public void setTempoDecorrido(int tempoDecorrido) {
        this.tempoDecorrido = tempoDecorrido;
    }

    public void setInicioIO(int inicioIO) {
        this.inicioIO = inicioIO;
    }

    public void setRq(int rq) {
        this.rq = rq;
    }

    public void executar() {
        this.tempoDecorrido++;
    }

    public boolean verificarConclusao() {
        return this.tempoDecorrido >= this.tempoCPU;
    }
}