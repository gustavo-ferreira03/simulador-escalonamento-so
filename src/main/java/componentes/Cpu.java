
package componentes;

import relatorio.Relatorio;

public class Cpu {
    private int id;
    int contador;
    private final SistemaOperacional so;
    private Processo processoAtual;

    Cpu(SistemaOperacional so, int id) {
        this.id = id;
        this.contador = 0;
        this.so = so;
    }

    public void executar() {
        if(ociosa()) {
            this.so.getRelatorio().getBlocoTimelineAtual().addProcessoCpu(id, "");
        }
        else {
            this.so.getRelatorio().getBlocoTimelineAtual().addProcessoCpu(id, this.processoAtual.getNome());
            this.processoAtual.executar();
            this.contador++;
            verificarInterrupcao();
        }
    }

    public void atualizarProcessos() {
        if(ociosa() && so.getEscalonador().temProcesso()) {
            obterProximoProcesso();
        }
    }

    private void verificarInterrupcao() {
        if(processoAtual.verificarConclusao()) {
            System.out.println("PROCESSO FINALIZADO: " + this.processoAtual.getNome());
            this.so.getRelatorio().registrarEvento(this.processoAtual.getNome() + ": EXECUÇÃO - FINALIZADO (CPU-" + (this.id + 1) + ")");
            this.processoAtual = null;
            this.contador = 0;
        }
        else if(verificarPreempcao()) {
            System.out.println("PROCESSO INTERROMPIDO: " + this.processoAtual.getNome());
            this.so.getRelatorio().registrarEvento(this.processoAtual.getNome() + ": EXECUÇÃO - PRONTO (CPU-" + (this.id + 1) + ")");
            this.so.getEscalonador().adicionarProcesso(this.processoAtual);
            this.processoAtual = null;
            this.contador = 0;
        }
    }

    public boolean ociosa() {
        return processoAtual == null;
    }

    private boolean verificarPreempcao() {
        if(this.processoAtual.getPrioridade() == Prioridade.TEMPO_REAL) {
            return false;
        }
        if(this.so.getEscalonador().temProcesso()) {
            return this.contador >= this.so.getEscalonador().getQuantum();
        }
        return false;
    }

    private void obterProximoProcesso() {
        if(so.getEscalonador().temProcesso()) {
            this.processoAtual = so.getEscalonador().obterProximoProcesso();
            this.so.getRelatorio().registrarEvento(this.processoAtual.getNome() + ": PRONTO - EXECUÇÃO (CPU-" + (this.id + 1) + ")");
        }
    }

    public int getId() {
        return id;
    }

    public int getContador() {
        return contador;
    }

    public SistemaOperacional getSo() {
        return so;
    }

    public Processo getProcessoAtual() {
        return processoAtual;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public void setProcessoAtual(Processo processoAtual) {
        this.processoAtual = processoAtual;
    }
}
