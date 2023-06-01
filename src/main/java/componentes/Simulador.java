
package componentes;

import relatorio.BlocoTimeline;
import relatorio.Relatorio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulador {
    private Relatorio relatorio;
    private SistemaOperacional so;
    private List<Processo> processos;
    private Cpu[] cpus;
    private int tempoDecorrido;

    public Simulador(String arquivoEntrada) {
        this.relatorio = new Relatorio();
        this.so = new SistemaOperacional(relatorio);
        this.processos = lerEntradas(arquivoEntrada);
        this.cpus = new Cpu[4];
        for(int i = 0; i < cpus.length; i++) this.cpus[i] = new Cpu(so, i);
        this.tempoDecorrido = 0;
    }

    public void iniciar() {
        while(!verificarFimSimulacao()) {
            System.out.println("INSTANTE ATUAL: " + this.tempoDecorrido);
            this.relatorio.addBlocoTimeline(new BlocoTimeline(this.relatorio.getBlocoTimelineAtual()));

            verificarChegadaProcessos();
            for(Cpu cpu : cpus) {
                cpu.atualizarProcessos();
                if(this.tempoDecorrido > 0) {
                    cpu.executar();
                }
            }
            if(this.tempoDecorrido > 0) {
                this.so.getEscalonador().registrarFilasRelatorio();
            }
            this.tempoDecorrido++;
        }
        System.out.println("GERANDO RELATORIO");
        this.relatorio.gerarRelatorio();
    }

    private boolean verificarFimSimulacao() {
        for(Cpu cpu : cpus) {
            if(!cpu.ociosa()) {
                return false;
            }
        }
        if(this.so.getEscalonador().temProcesso()) {
            return false;
        }
        for(Processo processo : processos) {
            if(this.tempoDecorrido <= processo.getTempoChegada()) {
                return false;
            }
        }
        return true;
    }

    private void verificarChegadaProcessos() {
        for(Processo processo : processos) {
            if(processo.getTempoChegada() == tempoDecorrido) {
                so.getRelatorio().registrarEvento(processo.getNome() + ": NOVO - PRONTO");
                so.getEscalonador().adicionarProcesso(processo);
            }
        }
    }

    private List<Processo> lerEntradas(String arquivo) {
        List<Processo> processos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                String nomeProcesso = "P" + i++;
                this.relatorio.addProcesso(nomeProcesso);

                List<Integer> numbers = Arrays.stream(line.split(",\\s*")).map(Integer::parseInt).toList();
                if (numbers.size() == 7) {
                    Processo p = new Processo(
                        nomeProcesso,
                        numbers.get(0),
                        Prioridade.values()[numbers.get(1)],
                        numbers.get(2),
                        numbers.get(3),
                        numbers.get(4),
                        numbers.get(5),
                        numbers.get(6)
                    );

                    processos.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processos;
    }
}
