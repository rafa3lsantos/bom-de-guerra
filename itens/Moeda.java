package itens;

import enums.TipoRaridade;

public class Moeda extends Item {
    private int quantidade;

    public Moeda (String nome, TipoRaridade raridade, int quantidade) {
        super(nome, raridade);
        this.quantidade =  quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
