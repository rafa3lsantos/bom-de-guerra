package itens;

import enums.TipoRaridade;

public class ItemConsumivel extends Item {
    private int valorRecuperacao;
    private String tipoRecuperação; // usa o enum
    public ItemConsumivel (String nome, int preco, TipoRaridade raridade, int valorRecuperacao) {
        super(nome, preco, raridade);
        this.valorRecuperacao = valorRecuperacao;
    }

    public int getValorRecuperacao() {
        return valorRecuperacao;
    }

    public String getTipoRecuperação() {
        return tipoRecuperação;
    }

}
