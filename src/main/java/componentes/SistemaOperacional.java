package componentes;

import relatorio.Relatorio;

import java.util.ArrayDeque;
import java.util.Queue;

public class SistemaOperacional {
    private Escalonador escalonador;
    private Relatorio relatorio;
    private Queue<Processo> filaIo;
    private int contadorIo;

    public SistemaOperacional(Relatorio relatorio) {
        this.escalonador = new Escalonador(this, 2);
        this.relatorio = relatorio;
        this.filaIo = new ArrayDeque<>();
    }

    public void tratarIo() {
        contadorIo++;
    }

    public void inicializarProcesso(Processo processo) {
        relatorio.registrarEvento(processo.getNome() + ": NOVO - PRONTO");
        escalonador.adicionarProcesso(processo);
    }

    public Escalonador getEscalonador() {
        return escalonador;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }
}
