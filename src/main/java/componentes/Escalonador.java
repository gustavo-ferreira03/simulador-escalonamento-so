package componentes;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    private int quantum;
    private Queue<Processo> filaIo;
    private Queue<Processo> filaReal;
    private List<Queue<Processo>> filasUsuario;

    Escalonador(int quantum) {
        this.quantum = quantum;
        this.filaIo = new ArrayDeque<>();
        this.filaReal = new ArrayDeque<>();
        this.filasUsuario = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            this.filasUsuario.add(new ArrayDeque<>());
        }
    }

    public int getQuantum() {
        return this.quantum;
    }

    public boolean temProcesso() {
        if(!filaReal.isEmpty()) {
            return true;
        }
        for(Queue<Processo> filaUsuario : filasUsuario) {
            if(!filaUsuario.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void adicionarProcesso(Processo processo) {
        if(processo.getPrioridade() == Prioridade.TEMPO_REAL) {
            this.filaReal.offer(processo);
        }
        else {
            processo.incrementaRq();
            this.filasUsuario.get(processo.getRq()).offer(processo);
        }
    }

    public Processo obterProximoProcesso() {
        if(!filaReal.isEmpty()) {
            return filaReal.poll();
        }
        for(Queue<Processo> filaUsuario : filasUsuario) {
            if(!filaUsuario.isEmpty()) {
                return filaUsuario.poll();
            }
        }
        return null;
    }
}
