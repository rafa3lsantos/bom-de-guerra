package itens;

import enums.TipoRaridade;

public class ItemProtecao extends Item {
    private int valorDefesa;
    public ItemProtecao (String nome, int preco, TipoRaridade raridade, int valorDefesa) {
        super(nome, preco, raridade);
        this.valorDefesa = valorDefesa;
    }

    public ItemProtecao(String nome, TipoRaridade raridade, int valorDefesa) {
        super(nome, raridade);
        this.valorDefesa = valorDefesa;
    }

    public int getValorDefesa() {
        return valorDefesa;
    }
}
