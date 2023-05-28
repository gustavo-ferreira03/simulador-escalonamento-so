package relatorio;

import java.util.ArrayList;

public class BlocoRelatorio {
    private ArrayList<String>[] cpus;
    private FilasRelatorio filas;
    private ProgressoRelatorio progresso;

    public BlocoRelatorio() {
        this.cpus = new ArrayList[4];
        for(int i = 0; i < 4; i++) this.cpus[i] = new ArrayList<String>();

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
