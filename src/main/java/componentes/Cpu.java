
package componentes;

import relatorio.Relatorio;

public class Cpu {
    private static int instanceCounter = 0;
    private int id;
    int contador;
    private final SistemaOperacional so;
    private Processo processoAtual;

    Cpu(SistemaOperacional so) {
        this.id = instanceCounter++;
        this.contador = 0;
        this.so = so;
    }

    public void executar() {
        if(this.processoAtual == null) {
            this.so.getRelatorio().getBlocoTimelineAtual().addProcessoCpu(id, "");
            if(this.so.getEscalonador().temProcesso()){
                obterProximoProcesso();
            }
            else {
                return;
            }
        }
        this.so.getRelatorio().getBlocoTimelineAtual().addProcessoCpu(id, this.processoAtual.getNome());
        this.processoAtual.executar();
        this.contador++;

        if(this.processoAtual.verificarConclusao()) {
            System.out.println("PROCESSO FINALIZADO: " + this.processoAtual.getNome());
            obterProximoProcesso();
            this.contador = 0;
            return;
        }
        if(verificarPreempcao()) {
            this.so.getEscalonador().adicionarProcesso(this.processoAtual);
            obterProximoProcesso();
            this.contador = 0;

        }
    }

    public boolean ociosa() {
        return this.processoAtual == null;
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

    private boolean obterProximoProcesso() {
        this.processoAtual = this.so.getEscalonador().obterProximoProcesso();
        if(this.processoAtual != null) {
            return true;
        }
        return false;
    }
}
