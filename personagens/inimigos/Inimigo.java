package personagens.inimigos;

import personagens.Personagem;
import jogo.InterfaceUsuario;
import java.util.Random;

/**
 * Classe base para os monstros do jogo. Cuida de guardar o dano padrão,
 * as moedas dropadas e possui uma IA por sorteio para decidir as ações no combate.
 * * @author Rafael
 */
public class Inimigo extends Personagem {
    private int danoBase;
    private int dracmasDropadas;
    private Random random;

    /**
     * Construtor para criar um Inimigo com seus status de combate e de recompensa.
     * * @param nome            Nome do monstro.
     * @param vidaMaxima      Limite máximo de pontos de vida dele.
     * @param vidaAtual       Vida inicial com que ele entra na luta.
     * @param danoBase        Dano padrão que ele causa nos ataques.
     * @param dracmasDropadas Quantidade de moedas que ele deixa cair ao morrer.
     */
    public Inimigo(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        super(nome, vidaMaxima, vidaAtual);
        this.danoBase = danoBase;
        this.dracmasDropadas = dracmasDropadas;
        this.random = new Random();
    }

    /**
     * A IA do monstro. Sorteia uma ação para o turno dele usando porcentagens:
     * 70% de chance de atacar e 30% de se defender.
     * * @param jogador O herói que está sendo enfrentado.
     */
    public void executarTurno(Personagem jogador) {
        int escolha = random.nextInt(100);

        if (escolha < 70) {
            this.atacar(jogador);
        } else {
            this.defender();
        }
    }

    /**
     * Realiza um ataque físico básico contra o herói, causando o seu dano bruto.
     * * @param alvo O jogador ou personagem que vai tomar o golpe.
     */
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n⚔️ [TURNO DO INIMIGO] -> " + getNome() + " avança para atacar!");

        alvo.receberDano(this.danoBase);
    }

    /**
     * Faz o monstro rosnar e assumir uma postura de guarda para o turno.
     */
    @Override
    public void defender() {
        System.out.println("\n🛡️ [TURNO DO INIMIGO] -> " + getNome() + " rosnou e adotou uma postura defensiva!");
    }

    /**
     * Desconta os pontos de vida do monstro após ele ser atingido pelo ataque do jogador.
     * * @param danoBruto O valor total do dano enviado pelo herói.
     */
    @Override
    public void receberDano(int danoBruto) {
        int novaVida = getVidaAtual() - danoBruto;
        setVidaAtual(novaVida);

        System.out.println("💥 " + getNome() + " sofreu " + danoBruto + " de dano!");
    }

    public int getDanoBase() {
        return danoBase;
    }
    public int getDracmasDropadas() {
        return dracmasDropadas;
    }
}