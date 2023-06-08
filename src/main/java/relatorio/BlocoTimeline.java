package relatorio;

import componentes.Escalonador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlocoTimeline {
    private ArrayList<String>[] cpus;
    private FilasRelatorio filas;
    Map<String, ProgressoRelatorio> progresso;
    private ArrayList<String> eventos;

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
        this.eventos = new ArrayList<String>();
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

    public Map<String, ProgressoRelatorio> getProgresso() {
        return progresso;
    }
}
