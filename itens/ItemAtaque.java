package itens;

import enums.TipoRaridade;
import personagens.jogador.Jogador;

public class ItemAtaque extends Item {
    private int danoBonus;
    public ItemAtaque (String nome, int preco, TipoRaridade raridade, int danoBonus) {
        super(nome, preco, raridade);
        this.danoBonus = danoBonus;
    }

    public ItemAtaque(String nome, TipoRaridade raridade, int danoBonus) {
        super(nome, raridade);
        this.danoBonus = danoBonus;
    }

    public int getDanoBonus() {
        return danoBonus;
    }



}
