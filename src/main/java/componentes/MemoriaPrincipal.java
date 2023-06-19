package componentes;

import utils.SegmentoMemoria;

import java.util.ArrayList;
import java.util.List;

public class MemoriaPrincipal {
    int tamanho;
    int espacoDisponivel;
    List<SegmentoMemoria> segmentos;

    public MemoriaPrincipal(int tamanho) {
        this.tamanho = tamanho;
        this.espacoDisponivel = tamanho * 1000;
        this.segmentos = new ArrayList<>();
        this.segmentos.add(new SegmentoMemoria(null, tamanho * 1000));
    }

    public boolean temEspacoDisponivel(Processo processo) {
        return espacoDisponivel >= processo.getTamanho();
    }

    public void alocar(Processo processo) {
        int quadrosNecessarios = processo.getTamanho();
        this.espacoDisponivel -= quadrosNecessarios;
        System.out.println(processo.getNome());
        for(int i = 0; i < segmentos.size(); i++) {
            SegmentoMemoria segmento = segmentos.get(i);
            int espacoSegmento = segmento.getNumQuadros();
            if(segmento.livre() && segmento.getNumQuadros() > 0) {
                if(quadrosNecessarios > segmento.getNumQuadros()) {
                    criarNovoSegmento(processo, segmento, espacoSegmento);
                    quadrosNecessarios -= espacoSegmento;
                    System.out.println(quadrosNecessarios + " " + segmento.getNumQuadros() + " " + segmentos.size());
                }
                else {
                    criarNovoSegmento(processo, segmento, quadrosNecessarios);
                    break;
                }
            }
        }
    }

    public void desalocar(Processo processo) {
        for(SegmentoMemoria segmento : segmentos) {
            if(segmento.getProcesso() == processo) {
                liberarSegmento(segmento);
            }
        }
    }

    public void organizarSegmentos() {
        for(int i = 0; i < segmentos.size(); i++) {
            SegmentoMemoria segmento = segmentos.get(i);
            if(segmento.getNumQuadros() == 0) {
                segmentos.remove(segmento);
                i--;
            }
            if(segmento.livre() && (i < segmentos.size() - 1)) {
                SegmentoMemoria segmentoSeguinte = segmentos.get(i + 1);
                if(segmentoSeguinte.livre()) {
                    juntarSegmentos(segmento, segmentoSeguinte);
                    i--;
                }
            }
        }
    }

    private void juntarSegmentos(SegmentoMemoria segmento, SegmentoMemoria segmentoSeguinte) {
        int novoNumQuadros = segmento.getNumQuadros() + segmentoSeguinte.getNumQuadros();
        segmento.setNumQuadros(novoNumQuadros);
        segmentos.remove(segmentoSeguinte);
    }

    private void criarNovoSegmento(Processo processo, SegmentoMemoria segmentoLivre, int numQuadros) {
        segmentos.add(segmentos.indexOf(segmentoLivre), new SegmentoMemoria(processo, numQuadros));
        segmentoLivre.setNumQuadros(segmentoLivre.getNumQuadros() - numQuadros);
    }

    private void liberarSegmento(SegmentoMemoria segmento){
        espacoDisponivel += segmento.getNumQuadros();
        segmento.setProcesso(null);
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getEspacoDisponivel() {
        return espacoDisponivel;
    }

    public void setEspacoDisponivel(int espacoDisponivel) {
        this.espacoDisponivel = espacoDisponivel;
    }

    public List<SegmentoMemoria> getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List<SegmentoMemoria> segmentos) {
        this.segmentos = segmentos;
    }
}
