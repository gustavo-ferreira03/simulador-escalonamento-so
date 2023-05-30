
package componentes;

public class Cpu {
    int contador;
    private final SistemaOperacional so;
    private Processo processoAtual;

    Cpu(SistemaOperacional so) {
        this.contador = 0;
        this.so = so;
    }

    public void executar() {
        if(this.processoAtual == null) {
            if(this.so.getEscalonador().temProcesso()){
                obterProximoProcesso();
            }
            else {
                return;
            }
        }
        this.processoAtual.executar();
        this.contador++;

        if(this.processoAtual.verificarConclusao()) {
            System.out.println("PROCESSO ATUAL: " + this.processoAtual.getPrioridade());
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
