package componentes;

import org.junit.jupiter.api.*;
import relatorio.BlocoTimeline;
import relatorio.Relatorio;
import utils.Prioridade;

public class CpuTest {
    Cpu cpu;
    SistemaOperacional so;
    Processo processoAtual;
    Relatorio relatorio;
    BlocoTimeline bloco;
    int quantum;

    @BeforeEach
    void inicializarCpu() {
        relatorio = new Relatorio();
        relatorio.criarBlocoTimeline();
        so = new SistemaOperacional(new Discos(4), relatorio);
        cpu = new Cpu(so, 0);
        quantum = so.getEscalonador().getQuantum();
    }

    @DisplayName("atualizarProcessos()")
    @Nested
    class AtualizarProcessos {
        @BeforeEach
        void inicializarProcesso() {
            processoAtual = new Processo("P0", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0);
            so.inicializarProcesso(processoAtual);
        }
        @DisplayName("Atualiza CPU se estiver ociosa")
        @Test
        void atualizaCpuSeEstiverOciosa() {
            Assertions.assertTrue(cpu.ociosa());
            cpu.atualizarProcessos();
            Assertions.assertFalse(cpu.ociosa());
            Assertions.assertNotNull(cpu.getProcessoAtual());
        }
    }


    @DisplayName("executar()")
    @Nested
    class Executar {
        @BeforeEach
        void inicializarProcesso() {
            processoAtual = new Processo("P0", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0);
            so.inicializarProcesso(processoAtual);
            cpu.atualizarProcessos();
        }
        @DisplayName("Incrementa o tempo decorrido do processo atual")
        @Test
        void incrementaTempoDecorrido() {
            int tempoDecorridoAntigo = processoAtual.getTempoDecorrido();
            processoAtual.setTempoCPU(999);
            cpu.executar();
            Assertions.assertEquals(tempoDecorridoAntigo + 1, processoAtual.getTempoDecorrido());
        }
        @DisplayName("Retira o processo da CPU caso ele seja finalizado")
        @Test
        void retiraProcessoFinalizado() {
            processoAtual.setTempoDecorrido(processoAtual.getTempoCPU() - 1);
            cpu.executar();
            Assertions.assertNull(cpu.getProcessoAtual());
        }
        @DisplayName("Retira o processo de usuário ao atingir o quantum caso tenha outros processos no escalonador")
        @Test
        void retiraProcessoInterrompido() {
            so.inicializarProcesso(new Processo("P1", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0));
            cpu.setContador(quantum - 1);
            processoAtual.setPrioridade(Prioridade.USUARIO);
            cpu.executar();
            Assertions.assertNotEquals(processoAtual, cpu.getProcessoAtual());
        }
        @DisplayName("Não retira o processo (de tempo real) da CPU caso ele atinja o quantum")
        @Test
        void naoRetiraProcessoTempoReal() {
            cpu.setContador(quantum - 1);
            processoAtual.setTempoCPU(999);
            processoAtual.setPrioridade(Prioridade.TEMPO_REAL);
            cpu.executar();
            Assertions.assertEquals(processoAtual, cpu.getProcessoAtual());
        }
    }

}
