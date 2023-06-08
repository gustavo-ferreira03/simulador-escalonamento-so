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
        this.contadorIo = 0;
    }

    public void requisitarIo(Processo processo) {
        this.filaIo.offer(processo);
    }

    public void registrarFilaIo() {
        relatorio.getBlocoTimelineAtual().getFilas().setIo(filaIo.stream().map(Processo::getNome).toList());
    }

    public void tratarIo() {
        if(!filaIo.isEmpty()) {
            this.contadorIo++;
            if(verificarConclusaoIo(filaIo.peek())) {
                desbloquearProcessoIo(filaIo.poll());
                this.contadorIo = 0;
            }
        }
    }

    private boolean verificarConclusaoIo(Processo processo) {
        return contadorIo == processo.getDuracaoIO();
    }

    private void desbloquearProcessoIo(Processo processo) {
        System.out.println("PROCESSO DESBLOQUEADO: " + processo.getNome());
        relatorio.registrarEvento(processo.getNome() + ": BLOQUEADO - PRONTO");
        processo.resetRq();
        escalonador.adicionarProcesso(processo);
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

    public Queue<Processo> getFilaIo() {
        return filaIo;
    }

    public int getContadorIo() {
        return contadorIo;
    }
}
