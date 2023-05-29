
package componentes;

import java.util.List;

public class Simulador {
    public List<Cpu> cpus;
    public Fila<Processo> filaReal;
    public List<Fila<Processo>> filasUsuario;
    public List<Processo> processosPendentes;
}
