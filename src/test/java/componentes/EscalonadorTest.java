package componentes;

import org.junit.jupiter.api.*;
import relatorio.BlocoTimeline;
import relatorio.Relatorio;
import utils.Prioridade;

public class EscalonadorTest {
    Escalonador escalonador;
    SistemaOperacional so;
    Relatorio relatorio;
    BlocoTimeline bloco;

    @BeforeEach
    void inicializarCpu() {
        relatorio = new Relatorio();
        relatorio.criarBlocoTimeline();
        so = new SistemaOperacional(new Discos(4), new MemoriaPrincipal(32), relatorio);
        escalonador = so.getEscalonador();
    }

    @DisplayName("obterProximoProcesso()")
    @Nested
    class GetProximoProcesso {
        @DisplayName("Retorna o processo mais prioritário da fila")
        @Nested
        class RetornaProcessoMaisPrioritario {
            @DisplayName("ordem de chegada")
            @Nested
            class OrdemDeChegada {
                @Test
                void usuario() {
                    Processo processoPrioritario = new Processo("P1", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0);
                    so.inicializarProcesso(processoPrioritario);
                    so.inicializarProcesso(new Processo("P2", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0));
                    Assertions.assertEquals(processoPrioritario, so.getEscalonador().obterProximoProcesso());
                }
                @Test
                void tempoReal() {
                    Processo processoPrioritario = new Processo("P1", 0, Prioridade.TEMPO_REAL, 4, 512, 0, 0, 0);
                    so.inicializarProcesso(processoPrioritario);
                    so.inicializarProcesso(new Processo("P2", 0, Prioridade.TEMPO_REAL, 4, 512, 0, 0, 0));
                    Assertions.assertEquals(processoPrioritario, so.getEscalonador().obterProximoProcesso());
                }
            }
            @DisplayName("tempo real é prioritário em relação a usuário")
            @Test
            void tempoRealPrioritario() {
                Processo processoPrioritario = new Processo("P1", 0, Prioridade.TEMPO_REAL, 4, 512, 0, 0, 0);
                so.inicializarProcesso(new Processo("P2", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0));
                so.inicializarProcesso(processoPrioritario);
                Assertions.assertEquals(processoPrioritario, so.getEscalonador().obterProximoProcesso());
            }
            @DisplayName("RQ diferentes para usuário")
            @Test
            void rqDiferentes() {
                Processo processoPrioritario = new Processo("P1", 0, Prioridade.TEMPO_REAL, 4, 512, 0, 0, 0);
                Processo processoRq2 = new Processo("P2", 0, Prioridade.USUARIO, 4, 512, 0, 0, 0);
                processoRq2.incrementaRq();
                so.inicializarProcesso(processoRq2);
                so.inicializarProcesso(processoPrioritario);
                Assertions.assertEquals(processoPrioritario, so.getEscalonador().obterProximoProcesso());
            }
        }
    }
}
