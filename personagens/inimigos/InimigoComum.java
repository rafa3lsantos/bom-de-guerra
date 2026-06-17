package personagens.inimigos;

import personagens.Personagem;
import jogo.InterfaceUsuario;
import java.util.Random;

/**
 * Representa os monstros normais e capangas padrão do jogo.
 * Eles possuem uma inteligência artificial básica por sorteio percentual,
 * focando na maior parte do tempo em ataques físicos diretos.
 * * @author Rafael
 */
public class InimigoComum extends Inimigo {
    private boolean defendendo = false;
    private Random random;

    /**
     * Construtor para criar um monstro comum com seus atributos de combate e recompensa.
     * Repassa as informações para a estrutura da classe mãe Inimigo.
     * * @param nome            Nome do monstro comum.
     * @param vidaMaxima      Total de vida máxima que ele pode ter.
     * @param vidaAtual       Vida inicial com que ele começa o combate.
     * @param danoBase        Dano físico padrão causado por ele.
     * @param dracmasDropadas Quantidade de moedas que ele derruba ao ser derrotado.
     */
    public InimigoComum(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        super(nome, vidaMaxima, vidaAtual, danoBase, dracmasDropadas);
        this.random = new Random();
    }

    /**
     * O cérebro do monstro. O GerenciadorJogo chama isso no turno dele.
     * Realiza uma escolha de ação baseada em sorteio: 75% de chance de atacar
     * o jogador e 25% de chance de adotar postura defensiva.
     * * @param jogador O herói que está sendo enfrentado no duelo.
     */
    public void executarTurno(Personagem jogador) {
        int escolha = random.nextInt(100);

        if (escolha < 75) {
            this.atacar(jogador);
        } else {
            this.defender();
        }
    }

    /**
     * Executa um ataque físico direto, desferindo o dano base do monstro
     * contra os status do herói.
     * * @param alvo O herói que sofrerá a investida.
     */
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n⚔️ [TURNO DO INIMIGO] -> " + getNome() + " avança furioso para atacar!");

        alvo.receberDano(getDanoBase());
    }

    /**
     * Faz o monstro assumir uma postura de guarda, ativando uma proteção
     * que cortará o próximo dano recebido pela metade.
     */
    @Override
    public void defender() {
        System.out.println("\n🛡️ [TURNO DO INIMIGO] -> " + getNome() + " adotou uma postura defensiva para mitigar o próximo impacto!");
        this.defendendo = true;
    }

    /**
     * Deduz os pontos de vida do monstro. Se ele tiver usado o comando defender
     * no turno anterior, o dano sofrido é reduzido pela metade.
     * * @param danoBruto O valor total do dano bruto enviado pelo ataque do herói.
     */
    @Override
    public void receberDano(int danoBruto) {
        int danoFinal = danoBruto;

        // Se o monstro se defendeu no turno passado, reduz o dano pela metade
        if (this.defendendo) {
            danoFinal = danoBruto / 2;
            System.out.println("🛡️ " + getNome() + " estava defendendo e barrou metade do estrago!");
            this.defendendo = false; // Desativa a defesa após receber o golpe
        }

        int novaVida = getVidaAtual() - danoFinal;
        setVidaAtual(novaVida);

        System.out.println("💥 " + getNome() + " sofreu " + danoFinal + " de dano!");
    }
}