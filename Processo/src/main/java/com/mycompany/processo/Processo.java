package com.mycompany.processo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Processo {
    
    private int tempoChegada;
    private int prioridade;
    private int tempoCPU;
    private int tamanho;
    private int disco;
    private int inicioIO;
    private int duracaoIO;
    private int tempoRestante;
    
    public static List<Processo> leArquivo(String arquivo) {
        List<Processo> processos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                Processo p = new Processo();
                String[] numbers = line.split(", ");

                if (numbers.length == 7) {
                    p.tempoChegada = Integer.parseInt(numbers[0]);
                    p.prioridade = Integer.parseInt(numbers[1]);
                    p.tempoCPU = Integer.parseInt(numbers[2]);
                    p.tamanho = Integer.parseInt(numbers[3]);
                    p.disco = Integer.parseInt(numbers[4]);
                    p.inicioIO = Integer.parseInt(numbers[5]);
                    p.duracaoIO = Integer.parseInt(numbers[6]);

                    processos.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processos;
    }
    public static void main(String[] args) {
        
        List<Processo> processos = leArquivo("arquivoTeste.txt");
        
        for (Processo processo : processos) {
            System.out.println("Número 1: " + processo.tempoChegada);
            System.out.println("Número 2: " + processo.prioridade);
            System.out.println("Número 3: " + processo.tempoCPU);
            System.out.println("Número 4: " + processo.tamanho);
            System.out.println("Número 5: " + processo.disco);
            System.out.println("Número 6: " + processo.inicioIO);
            System.out.println("Número 7: " + processo.duracaoIO);
            System.out.println();
            }
    }
}

    
    // Getters e Setters para os atributos
    
    
