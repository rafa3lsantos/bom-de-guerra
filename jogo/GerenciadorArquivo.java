package jogo;

import personagens.jogador.Jogador;
import java.io.*;

/**
 * Classe responsável por salvar e carregar o progresso do jogo usando serialização.
 * Cumpre o requisito de Leitura e Escrita em Arquivos.
 * * @author Rafael
 */
public class GerenciadorArquivo {

    private static final String NOME_ARQUIVO = "save_bomdeguerra.dat";

    /**
     * Pega o objeto do Jogador atual e escreve ele no disco rígido.
     * * @param jogador O herói que será salvo.
     */
    public static void salvarJogo(Jogador jogador) {
        // Tenta abrir o arquivo para escrita (FileOutputStream) e mandar o objeto (ObjectOutputStream)
        try (FileOutputStream fos = new FileOutputStream(NOME_ARQUIVO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(jogador);
            System.out.println("\n💾 Jogo salvo com sucesso! Os deuses guardaram seu progresso.");

        } catch (IOException e) {
            System.out.println("\n❌ ERRO FATAL: Não foi possível salvar o jogo!");
            e.printStackTrace();
        }
    }

    /**
     * Lê o arquivo do disco rígido e reconstrói o Jogador exatamente como estava.
     * * @return O objeto Jogador carregado, ou null se não existir save.
     */
    public static Jogador carregarJogo() {
        File arquivo = new File(NOME_ARQUIVO);

        if (!arquivo.exists()) {
            return null; // Não tem save anterior
        }

        // Tenta abrir o arquivo para leitura (FileInputStream) e reconstruir o objeto (ObjectInputStream)
        try (FileInputStream fis = new FileInputStream(arquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Jogador jogadorCarregado = (Jogador) ois.readObject();
            System.out.println("\n📖 Progresso carregado com sucesso! Bem-vindo de volta, " + jogadorCarregado.getNome() + ".");
            return jogadorCarregado;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\n❌ ERRO: O arquivo de save está corrompido ou desatualizado.");
            return null;
        }
    }
}