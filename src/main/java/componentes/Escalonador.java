package componentes;

import utils.Prioridade;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Escalonador {
    private SistemaOperacional so;
    private int quantum;
    private Queue<Processo> filaReal;
    private List<Queue<Processo>> filasUsuario;

    Escalonador(SistemaOperacional so, int quantum) {
        this.so = so;
        this.quantum = quantum;
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
        System.out.println("PROCESSO PRONTO: " + processo.getNome());
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
            System.out.println("PROCESSO ESCALONADO: " + filaReal.peek().getNome());
            return filaReal.poll();
        }
        for(Queue<Processo> filaUsuario : filasUsuario) {
            if(!filaUsuario.isEmpty()) {
                System.out.println("PROCESSO ESCALONADO: " + filaUsuario.peek().getNome());
                return filaUsuario.poll();
            }
        }
        return null;
    }

    public SistemaOperacional getSo() {
        return so;
    }

    public void setSo(SistemaOperacional so) {
        this.so = so;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public Queue<Processo> getFilaReal() {
        return filaReal;
    }

    public void setFilaReal(Queue<Processo> filaReal) {
        this.filaReal = filaReal;
    }

    public List<Queue<Processo>> getFilasUsuario() {
        return filasUsuario;
    }

    public void setFilasUsuario(List<Queue<Processo>> filasUsuario) {
        this.filasUsuario = filasUsuario;
    }
}
