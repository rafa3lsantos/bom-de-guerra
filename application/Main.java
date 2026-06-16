package application;

import excecoes.InventarioCheioException;
import jogo.GerenciadorJogo;

public class Main {
    public static void main(String[] args) throws InventarioCheioException {
        GerenciadorJogo engine = new GerenciadorJogo();
        engine.iniciarJogo();
    }
}

