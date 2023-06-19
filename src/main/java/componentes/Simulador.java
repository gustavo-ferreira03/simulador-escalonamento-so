
package componentes;

import relatorio.ProgressoRelatorio;
import relatorio.Relatorio;
import utils.Prioridade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulador {
    private Relatorio relatorio;
    private Discos discos;
    private MemoriaPrincipal memoriaPrincipal;
    private SistemaOperacional so;
    private List<Processo> processos;
    private Cpu[] cpus;
    private int tempoDecorrido;

    public Simulador(String arquivoEntrada) {
        this.relatorio = new Relatorio();
        this.discos = new Discos(4);
        this.memoriaPrincipal = new MemoriaPrincipal(32);
        this.so = new SistemaOperacional(discos, memoriaPrincipal, relatorio);
        this.processos = lerEntradas(arquivoEntrada);
        this.cpus = new Cpu[4];
        for(int i = 0; i < cpus.length; i++) this.cpus[i] = new Cpu(so, i);
        this.tempoDecorrido = 0;
    }

    public void iniciar() {
        while(!simulacaoAcabou()) {
            System.out.println("INSTANTE ATUAL: " + this.tempoDecorrido);
            relatorio.criarBlocoTimeline();

            inicializarNovosProcessos();
            so.tratarIo();
            if(this.tempoDecorrido > 0) {
                for(Cpu cpu : cpus) {
                    cpu.executar();
                    cpu.tratarInterrupcao();
                }
            }
            for(Cpu cpu: cpus) {
                cpu.atualizarProcessos();
            }
            so.alocarDiscos();
            relatorio.getBlocoTimelineAtual().registrarMemoriaPrincipal(memoriaPrincipal);
            relatorio.getBlocoTimelineAtual().registrarProgresso(processos);
            relatorio.getBlocoTimelineAtual().registrarDiscos(discos);
            relatorio.getBlocoTimelineAtual().registrarFilas(so);
            this.tempoDecorrido++;
        }
        System.out.println("GERANDO RELATORIO");
        relatorio.gerarRelatorio();
    }

    private boolean simulacaoAcabou() {
        for(Cpu cpu : cpus) {
            if(!cpu.ociosa()) {
                return false;
            }
        }
        if(this.so.getEscalonador().temProcesso()) {
            return false;
        }
        if(!this.so.getFilaIo().isEmpty()) {
            return false;
        }
        for(Processo processo : processos) {
            if(this.tempoDecorrido <= processo.getTempoChegada()) {
                return false;
            }
        }
        return true;
    }

    private void inicializarNovosProcessos() {
        for(Processo processo : processos) {
            if(processo.getTempoChegada() == tempoDecorrido) {
                so.inicializarProcesso(processo);
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
