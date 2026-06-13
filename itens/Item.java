package itens;

import enums.TipoRaridade;
import personagens.jogador.Jogador;

public abstract class Item {
    private String nome;
    private int preco;
    private TipoRaridade raridade;

    public Item (String nome, int preco, TipoRaridade raridade) {
        this.nome = nome;
        this.preco = preco;
        this.raridade = raridade;
    }

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
