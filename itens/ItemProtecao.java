package itens;

import enums.TipoRaridade;

/**
 * Representa os equipamentos de defesa do jogo, como armaduras, escudos e elmos.
 * Eles carregam um valor de defesa percentual que mitiga o dano recebido pelo jogador.
 * * @author Rafaela
 */
public class ItemProtecao extends Item {
    private int valorDefesa;

    /**
     * Construtor completo para armaduras que possuem um preço definido para venda no comércio.
     * * @param nome        O nome da armadura.
     * @param preco       O valor dela em Dracmas na loja do Mercador.
     * @param raridade    A classificação de raridade (Comum, Raro, etc).
     * @param valorDefesa O percentual de redução de dano que o item concede ao herói.
     */
    public ItemProtecao (String nome, int preco, TipoRaridade raridade, int valorDefesa) {
        super(nome, preco, raridade);
        this.valorDefesa = valorDefesa;
    }

    /**
     * Construtor alternativo para armaduras sem preço inicial, ideal para drops de monstros
     * ou baús encontrados pelo mapa.
     * * @param nome        O nome da armadura.
     * @param raridade    A classificação de raridade (Comum, Raro, etc).
     * @param valorDefesa O percentual de redução de dano que o item concede ao herói.
     */
    public ItemProtecao(String nome, TipoRaridade raridade, int valorDefesa) {
        super(nome, raridade);
        this.valorDefesa = valorDefesa;
    }

    /**
     * Devolve o valor de defesa da armadura para calcular a mitigação de dano do jogador.
     * * @return O valor numérico correspondente ao percentual de defesa.
     */
    public int getValorDefesa() {
        return valorDefesa;
    }
}