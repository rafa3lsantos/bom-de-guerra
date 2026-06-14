package itens;

import enums.TipoRaridade;

/**
 * Representa a moeda corrente do jogo (Dracmas).
 * Esta classe é tratada como um item especial que pode ser guardado no inventário,
 * permitindo acumular, gastar e consultar a quantidade de dinheiro que o jogador carrega.
 * * @author Rafaela
 */
public class Moeda extends Item {
    private int quantidade;

    /**
     * Construtor para inicializar um monte de moedas com um valor inicial.
     * Toda moeda nasce automaticamente com o nome "DRACMA" e raridade COMUM.
     * * @param quantidadeInicial A quantidade de moedas que o monte vai conter.
     */
    public Moeda (int quantidadeInicial) {
        super("DRACMA", TipoRaridade.COMUM);
        this.quantidade =  quantidadeInicial;
    }

    /**
     * Adiciona uma quantia de moedas ao monte atual.
     * * @param quantidadeASerAdicionada O valor numérico que será somado.
     */
    public void adicionarQuantidade (int quantidadeASerAdicionada) {
        this.quantidade += quantidadeASerAdicionada;
    }

    /**
     * Remove uma quantia de moedas do monte atual.
     * * @param quantidadeASerRemovida O valor numérico que será subtraído.
     */
    public void removerQuantidade (int quantidadeASerRemovida) {
        this.quantidade -= quantidadeASerRemovida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}