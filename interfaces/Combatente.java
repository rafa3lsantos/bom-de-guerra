package interfaces;

import personagens.Personagem;

/**
 * Interface que define o contrato de combate para os seres do jogo.
 * Qualquer classe que assine esta interface se torna obrigatoriamente capaz
 * de participar das rodadas de duelo do loop de combate.
 * * @author Rafael
 */
public interface Combatente {

    /**
     * Desfere uma ação ofensiva contra um oponente do jogo.
     * Cada classe implementará este método aplicando seus próprios bônus de dano,
     * status de fúria ou multiplicadores de armas equipadas.
     * * @param alvo O herói ou monstro que sofrerá o impacto do ataque.
     */
    public void atacar(Personagem alvo);

    /**
     * Ativa a postura defensiva do ser para a rodada atual.
     * Pode ser implementada para recuperar pontos de escudo ou mitigar
     * o próximo dano recebido pela metade.
     */
    public void defender();
}