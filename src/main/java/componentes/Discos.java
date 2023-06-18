package componentes;

import utils.ProcessoDisco;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Discos {
    private int quantidadeDisponivel;
    private List<ProcessoDisco> processosUtilizando;

    public Discos(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.processosUtilizando = new ArrayList<>();
    }

    public boolean podeUtilizar(Processo processo) {
        for(ProcessoDisco processoDisco : processosUtilizando) {
            if(processoDisco.getProcesso() == processo) return false;
        }
        return quantidadeDisponivel >= processo.getDisco();
    }

    public void utilizar(Processo processo) {
        this.processosUtilizando.add(new ProcessoDisco(processo));
        this.quantidadeDisponivel -= processo.getDisco();
    }

    public void liberar(Processo processo) {
        this.quantidadeDisponivel += processo.getDisco();
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public List<ProcessoDisco> getProcessosUtilizando() {
        return processosUtilizando;
    }

    public void setProcessosUtilizando(List<ProcessoDisco> processosUtilizando) {
        this.processosUtilizando = processosUtilizando;
    }
}
