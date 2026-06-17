package application;

import excecoes.InventarioCheioException;
import jogo.GerenciadorJogo;

/**
 * Classe de entrada (Entry Point) do aplicativo Bom De Guerra.
 * Contém o método executável principal que inicializa os motores do jogo.
 * * @author Rafaela
 */
public class Main {

    /**
     * Método mestre que o Java invoca para dar partida no programa.
     * Instancia o motor do jogo e inicia a jornada mitológica.
     * * @param args Argumentos de linha de comando passados na execução (não utilizados).
     * @throws InventarioCheioException Repassada caso o fluxo de inicialização enfrente estouro de capacidade na bolsa.
     */
    public static void main(String[] args) throws InventarioCheioException {
        GerenciadorJogo engine = new GerenciadorJogo();
        engine.iniciarJogo();
    }
}