package componentes;

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

    public void incrementaRq() {
        if(this.rq < 2) {
            this.rq++;
        }
    }

    public void executar() {
//        System.out.println("Processo executando, tempo decorrido: " + this.tempoDecorrido);
        this.tempoDecorrido++;
    }

    public boolean verificarConclusao() {
        return this.tempoDecorrido >= this.tempoCPU;
    }
}