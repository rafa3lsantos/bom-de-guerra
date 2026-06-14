package itens;

import enums.TipoConsumivel;
import enums.TipoRaridade;

public class ItemConsumivel extends Item {
    private int valorRecuperacao;
    private TipoConsumivel tipoRecuperacao;

    public ItemConsumivel(String nome, int preco, TipoRaridade raridade, int valorRecuperacao, TipoConsumivel tipoRecuperacao) {
        super(nome, preco, raridade);
        this.valorRecuperacao = valorRecuperacao;
        this.tipoRecuperacao = tipoRecuperacao; // Agora o Enum é inicializado corretamente!
    }

    public ItemConsumivel(String nome, TipoRaridade raridade, int valorRecuperacao, TipoConsumivel tipoRecuperacao) {
        super(nome, raridade);
        this.valorRecuperacao = valorRecuperacao;
        this.tipoRecuperacao = tipoRecuperacao;
    }

    public int getValorRecuperacao() {
        return valorRecuperacao;
    }

    public TipoConsumivel getTipoRecuperacao() {
        return tipoRecuperacao;
    }
}