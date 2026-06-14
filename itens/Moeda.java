package itens;

import enums.TipoRaridade;

public class Moeda extends Item {
    private int quantidade;

    public Moeda (int quantidadeInicial) {
        super("DRACMA", TipoRaridade.COMUM);
        this.quantidade =  quantidadeInicial;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarQuantidade (int quantidadeASerAdicionada) {
        this.quantidade += quantidadeASerAdicionada;
    }


}
