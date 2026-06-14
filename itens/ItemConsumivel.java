package itens;

import enums.TipoConsumivel;
import enums.TipoRaridade;

/**
 * Representa os itens descartáveis do jogo, como poções de cura e elixires.
 * Eles carregam um valor de restauração e um tipo específico para saber se vão
 * recuperar os pontos de vida ou os pontos de escudo do jogador.
 * * @author Rafaela
 */
public class ItemConsumivel extends Item {
    private int valorRecuperacao;
    private TipoConsumivel tipoRecuperacao;

    /**
     * Construtor completo para consumíveis que possuem um preço definido para o comércio.
     * * @param nome             O nome do consumível.
     * @param preco            O preço dele em Dracmas na loja.
     * @param raridade         A classificação de raridade (Comum, Raro, etc).
     * @param valorRecuperacao Quantos pontos o item recupera ao ser usado.
     * @param tipoRecuperacao  Diz se a poção cura vida (CURA_VIDA) ou escudo (CURA_ESCUDO).
     */
    public ItemConsumivel(String nome, int preco, TipoRaridade raridade, int valorRecuperacao, TipoConsumivel tipoRecuperacao) {
        super(nome, preco, raridade);
        this.valorRecuperacao = valorRecuperacao;
        this.tipoRecuperacao = tipoRecuperacao; // Agora o Enum é inicializado corretamente!
    }

    /**
     * Construtor alternativo para consumíveis sem preço inicial, ideal para drops de monstros
     * ou baús de recompensa encontrados pelo mapa.
     * * @param nome             O nome do consumível.
     * @param raridade         A classificação de raridade (Comum, Raro, etc).
     * @param valorRecuperacao Quantos pontos o item recupera ao ser usado.
     * @param tipoRecuperacao  Diz se a poção cura vida (CURA_VIDA) ou escudo (CURA_ESCUDO).
     */
    public ItemConsumivel(String nome, TipoRaridade raridade, int valorRecuperacao, TipoConsumivel tipoRecuperacao) {
        super(nome, raridade);
        this.valorRecuperacao = valorRecuperacao;
        this.tipoRecuperacao = tipoRecuperacao;
    }

    /**
     * Devolve a quantidade de pontos que este item vai restaurar nos status do herói.
     * * @return O valor numérico da cura ou restauração.
     */
    public int getValorRecuperacao() {
        return valorRecuperacao;
    }

    /**
     * Devolve o tipo de cura (se afeta a barra de vida ou a barra de escudo).
     * * @return O enum indicando a propriedade de recuperação do item.
     */
    public TipoConsumivel getTipoRecuperacao() {
        return tipoRecuperacao;
    }
}