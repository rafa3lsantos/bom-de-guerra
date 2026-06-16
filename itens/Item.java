package itens;

import enums.TipoRaridade;
import personagens.jogador.Jogador;

/**
 * Classe abstrata que serve de molde para qualquer objeto, equipamento
 * ou consumível que exista no universo do jogo.
 * * @author Rafaela
 */
public abstract class Item implements java.io.Serializable {
    private String nome;
    private int preco;
    private TipoRaridade raridade;

    /**
     * Construtor completo para itens que possuem um valor de mercado definido.
     * Muito utilizado para mercadorias vendidas ou compradas na loja.
     * * @param nome     O nome do item.
     * @param preco    O preço do item em Dracmas.
     * @param raridade A classificação de raridade do item (Comum, Raro, etc).
     */
    public Item (String nome, int preco, TipoRaridade raridade) {
        this.nome = nome;
        this.preco = preco;
        this.raridade = raridade;
    }

    /**
     * Construtor alternativo para itens que não possuem preço inicial.
     * Útil para moedas ou itens especiais de missão.
     * * @param nome     O nome do item.
     * @param raridade A classificação de raridade do item.
     */
    public Item (String nome, TipoRaridade raridade) {
        this.nome = nome;
        this.raridade = raridade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public TipoRaridade getRaridade() {
        return raridade;
    }

    public void setRaridade(TipoRaridade raridade) {
        this.raridade = raridade;
    }
}