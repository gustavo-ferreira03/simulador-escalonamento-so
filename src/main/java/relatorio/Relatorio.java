package relatorio;

import java.util.ArrayList;

public class Relatorio {
    private static Relatorio instance = null;
    private ArrayList<String> processos;
    private ArrayList<BlocoRelatorio> timeline;

    private Relatorio() {
        this.processos = new ArrayList<String>();
        this.timeline = new ArrayList<BlocoRelatorio>();
    }

    public Relatorio getInstance() {
        if(instance == null) {
            instance = new Relatorio();
        }
        return instance;
    }

    public void addProcesso(String processo) {
        this.processos.add(processo);
    }

    public ArrayList<BlocoRelatorio> getTimeline() {
        return timeline;
    }
}
