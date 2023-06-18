package relatorio;

import componentes.Discos;
import componentes.Escalonador;
import componentes.Processo;
import componentes.SistemaOperacional;
import utils.ProcessoDisco;

import java.util.*;

public class BlocoTimeline {
    private ArrayList<String>[] cpus;
    private FilasRelatorio filas;
    Map<String, ProgressoRelatorio> progresso;
    private List<String> eventos;
    private List<DiscosRelatorio> discos;

    public BlocoTimeline(BlocoTimeline blocoAnterior) {
        this.cpus = new ArrayList[4];
        for(int i = 0; i < 4; i++) {
            if(blocoAnterior == null) {
                this.cpus[i] = new ArrayList<String>();
            }
            else {
                this.cpus[i] = (ArrayList<String>) blocoAnterior.getCpus()[i].clone();
            }
        }
        this.filas = new FilasRelatorio();
        this.progresso = new HashMap<>();
        this.eventos = new ArrayList<>();
        this.discos = new ArrayList<>();
    }

    void addEvento(String evento) {
        this.eventos.add(evento);
    }

    public ArrayList<String>[] getCpus() {
        return cpus;
    }

    public void addProcessoCpu(int cpu, String processo) {
        this.cpus[cpu].add(processo);
    }

    public FilasRelatorio getFilas() {
        return filas;
    }

    public void registrarFilas(SistemaOperacional so) {
        filas.setIo(so.getFilaIo().stream().map(Processo::getNome).toList());
        filas.setP0(so.getEscalonador().getFilaReal().stream().map(Processo::getNome).toList());
        List<List<String>> p1 = new ArrayList<List<String>>();
        for(Queue<Processo> filaUsuario : so.getEscalonador().getFilasUsuario()) {
            p1.add(filaUsuario.stream().map(Processo::getNome).toList());
        }
        filas.setP1(p1);
    }

    public void registrarDiscos(Discos discos) {
        for(ProcessoDisco processoDisco : discos.getProcessosUtilizando()) {
            Processo processo = processoDisco.getProcesso();
            this.discos.add(new DiscosRelatorio(processo.getNome(), processo.getDisco()));
        }
    }

    public void registrarProgresso(List<Processo> processos) {
        for(Processo processo : processos) {
            ProgressoRelatorio progresso = new ProgressoRelatorio(processo.getTempoDecorrido(), processo.getTempoCPU());
            this.progresso.put(processo.getNome(), progresso);
        }
    }

    public Map<String, ProgressoRelatorio> getProgresso() {
        return progresso;
    }
}
