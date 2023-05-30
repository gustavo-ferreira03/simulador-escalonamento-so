
package componentes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Simulador {
    private SistemaOperacional so;
    private List<Processo> processos;
    private Cpu[] cpus;
    int tempoDecorrido;

    public Simulador(String arquivoEntrada) {
        this.so = new SistemaOperacional();
        this.processos = lerEntradas(arquivoEntrada);
        this.cpus = new Cpu[4];
        for(int i = 0; i < cpus.length; i++) this.cpus[i] = new Cpu(so);
        this.tempoDecorrido = 0;
    }

    public void iniciar() {
        while(true) {
            System.out.println(this.tempoDecorrido);
            for(Processo processo : processos) {
                if(processo.getTempoChegada() == this.tempoDecorrido) {
                    so.getEscalonador().adicionarProcesso(processo);
                }
            }
            for(Cpu cpu : cpus) {
                cpu.executar();
            }
            if(verificarFimSimulacao()) {
                break;
            }
            this.tempoDecorrido++;
        }
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
            if(processo.getTempoChegada() > this.tempoDecorrido) {
                return false;
            }
        }
        return true;
    }

    private void verificarChegadaProcessos() {
        for(Processo processo : processos) {
            if(processo.getTempoChegada() == tempoDecorrido) {
                so.getEscalonador().adicionarProcesso(processo);
            }
        }
    }

    private List<Processo> lerEntradas(String arquivo) {
        List<Processo> processos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> numbers = Arrays.stream(line.split(",\\s*")).map(Integer::parseInt).toList();

                if (numbers.size() == 7) {
                    Processo p = new Processo(
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
