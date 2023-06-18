package componentes;

import relatorio.Relatorio;
import utils.ProcessoDisco;

import java.sql.Array;
import java.util.*;

public class SistemaOperacional {
    private Escalonador escalonador;
    private Relatorio relatorio;
    private ArrayList<Processo> filaIo;
    private int contadorIo;

    private Discos discos;


    public SistemaOperacional(Discos discos, Relatorio relatorio) {
        this.escalonador = new Escalonador(this, 2);
        this.relatorio = relatorio;
        this.filaIo = new ArrayList<>();
        this.contadorIo = 0;
        this.discos = discos;
    }

    public void requisitarIo(Processo processo) {
        this.filaIo.add(processo);
    }

    public void tratarIo() {
        if (!filaIo.isEmpty()) {
            Iterator<ProcessoDisco> iterator = discos.getProcessosUtilizando().iterator();
            while(iterator.hasNext()) {
                ProcessoDisco processoDisco = iterator.next();
                processoDisco.incrementarTempoDecorrido();
                if(processoDisco.verificarConclusao()) {
                    desbloquearProcesso(processoDisco.getProcesso());
                    iterator.remove();
                }
            }
        }
    }

    public void alocarDiscos() {
        for (Processo processo : filaIo) {
            if (discos.podeUtilizar(processo)) {
                discos.utilizar(processo);
            }
        }
    }

    private void desbloquearProcesso(Processo processo) {
        desbloquearProcessoIo(processo);
        discos.liberar(processo);
        filaIo.remove(processo);
    }

    private void desbloquearProcessoIo(Processo processo) {
        System.out.println("PROCESSO DESBLOQUEADO: " + processo.getNome());
        relatorio.registrarEvento(processo.getNome() + ": BLOQUEADO - PRONTO");
        processo.resetRq();
        escalonador.adicionarProcesso(processo);
    }

    public void inicializarProcesso(Processo processo) {
        relatorio.registrarEvento(processo.getNome() + ": NOVO - PRONTO");
        escalonador.adicionarProcesso(processo);
    }

    public Escalonador getEscalonador() {
        return escalonador;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public ArrayList<Processo> getFilaIo() {
        return filaIo;
    }

    public int getContadorIo() {
        return contadorIo;
    }
}