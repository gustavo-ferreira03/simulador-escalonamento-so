package componentes;

import relatorio.Relatorio;

import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SistemaOperacional {
    private Escalonador escalonador;
    private Relatorio relatorio;
    private ArrayList<Processo> filaIo;
    private int contadorIo;

    private Discos discos;

    private ArrayList<String> processoUtilizandoDiscos = new ArrayList<>();
    private ArrayList<Integer> tempoUtilizandoDiscos = new ArrayList<>();


    public SistemaOperacional(Relatorio relatorio) {
        this.escalonador = new Escalonador(this, 2);
        this.relatorio = relatorio;
        this.filaIo = new ArrayList<>();
        this.contadorIo = 0;
        this.discos = new Discos();
    }

    public void requisitarIo(Processo processo) {
        this.filaIo.add(processo);
    }

    public void tratarIo() {
        if(!filaIo.isEmpty()) {
            this.contadorIo++;
            for(int i = 0;i<filaIo.size();i++){
                if(filaIo.stream().toList().get(i).getDisco() <= this.discos.getQuantidadeDisponivel() && !this.processoUtilizandoDiscos.contains(filaIo.stream().toList().get(i).getNome())){
                    this.discos.setQuantidadeDisponivel(this.discos.getQuantidadeDisponivel() - filaIo.stream().toList().get(i).getDisco());
                    System.out.println("Pode usar o disco processo :"+filaIo.stream().toList().get(i).getNome());
                    this.processoUtilizandoDiscos.add(filaIo.stream().toList().get(i).getNome());
                    this.tempoUtilizandoDiscos.add(filaIo.stream().toList().get(i).getDuracaoIO());
                }else{
                    System.out.println("Nao pode usar o disco processo"+ filaIo.stream().toList().get(i).getNome());
                }
            }
            for(int i = 0;i<this.processoUtilizandoDiscos.size();i++){
                this.tempoUtilizandoDiscos.set(i,this.tempoUtilizandoDiscos.get(i) -1);

                if(this.tempoUtilizandoDiscos.get(i) == 0){
                    this.tempoUtilizandoDiscos.remove(i);
                    String nomeProcessoDesbloqueado = this.processoUtilizandoDiscos.remove(i);
                    System.out.println("aqui : "+nomeProcessoDesbloqueado);
                    Processo processoDesbloqueado = null;
                    for(int p=0;p<filaIo.size();p++){

                        if(filaIo.get(p).getNome().equals(nomeProcessoDesbloqueado)){
                            processoDesbloqueado = filaIo.get(p);
                        }
                    }
                    if(processoDesbloqueado != null){
                        desbloquearProcessoIo(processoDesbloqueado);
                        filaIo.remove(processoDesbloqueado);
                        System.out.println("AAAAAqui :" +processoDesbloqueado.getDisco());
                        this.discos.setQuantidadeDisponivel(this.discos.getQuantidadeDisponivel() + processoDesbloqueado.getDisco());
                        System.out.println("Quantidade de discos disponiveis :"+this.discos.getQuantidadeDisponivel());
                    }

                }
            }



        }else{
            this.contadorIo =0;
        }
    }

    private boolean verificarConclusaoIo(Processo processo) {
        return contadorIo == processo.getDuracaoIO();
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
