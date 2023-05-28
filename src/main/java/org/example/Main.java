package org.example;

import relatorio.Relatorio;

public class Main {
    public static void main(String[] args) {
        Relatorio relatorio = Relatorio.getInstance();
        relatorio.gerarRelatorio();
    }
}