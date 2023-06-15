import componentes.Simulador;
import relatorio.Relatorio;

public class Main {
    public static void main(String[] args) {
        Simulador simulador = new Simulador("arquivoTeste.txt");
        simulador.iniciar();
    }
}