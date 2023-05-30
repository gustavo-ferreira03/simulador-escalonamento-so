package relatorio;

import componentes.Escalonador;

import java.util.ArrayList;

public class BlocoTimeline {
    private ArrayList<String>[] cpus;
    private FilasRelatorio filas;
    private ProgressoRelatorio progresso;

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
        this.progresso = new ProgressoRelatorio();
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

    public ProgressoRelatorio getProgresso() {
        return progresso;
    }
}
