package itens;

import enums.TipoRaridade;
import personagens.jogador.Jogador;

/**
 * Representa os equipamentos de ataque do jogo (como espadas, machados e cajados).
 * Carrega um bônus de dano que é somado à força do jogador na hora do combate.
 * * @author Rafaela
 */
public class ItemAtaque extends Item {
    private int danoBonus;

    /**
     * Construtor completo para armas que possuem um preço definido para venda no comércio.
     * * @param nome      O nome da arma.
     * @param preco     O valor dela em Dracmas na loja.
     * @param raridade  A classificação de raridade (Comum, Raro, etc).
     * @param danoBonus O valor de ataque extra que ela concede ao herói.
     */
    public ItemAtaque (String nome, int preco, TipoRaridade raridade, int danoBonus) {
        super(nome, preco, raridade);
        this.danoBonus = danoBonus;
    }

    /**
     * Construtor alternativo para armas sem preço inicial, ideal para drops de monstros
     * ou baús encontrados no mapa.
     * * @param nome      O nome da arma.
     * @param raridade  A classificação de raridade (Comum, Raro, etc).
     * @param danoBonus O valor de ataque extra que ela concede ao herói.
     */
    public ItemAtaque(String nome, TipoRaridade raridade, int danoBonus) {
        super(nome, raridade);
        this.danoBonus = danoBonus;
    }

    /**
     * Devolve o bônus de ataque da arma para somar na conta do dano do jogador.
     * * @return O valor numérico do dano bônus.
     */
    public int getDanoBonus() {
        return danoBonus;
    }
}