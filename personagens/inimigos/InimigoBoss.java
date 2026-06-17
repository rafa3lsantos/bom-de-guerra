package personagens.inimigos;

import personagens.Personagem;
import java.util.Random;

/**
 * Classe que representa o chefe de fase do jogo (Boss).
 * Ele possui mecânicas avançadas de combate, incluindo pontos de escudo recarregáveis,
 * um medidor para disparar um ataque especial supremo e um modo de fúria passivo.
 * * @author Rafael
 */
public class InimigoBoss extends Inimigo {
    private int escudoMax;
    private int escudoAtual;
    private int turnoParaEspecial;
    private boolean emFuria;
    private Random random;

    /**
     * Construtor para inicializar o Boss com atributos elevados de vida e dano.
     * O escudo nasce automaticamente cheio com 100 pontos de capacidade.
     * * @param nome            Nome do chefe.
     * @param vidaMaxima      Total de vida máxima dele.
     * @param vidaAtual       Vida com que ele inicia o combate.
     * @param danoBase        Dano padrão do ataque físico dele.
     * @param dracmasDropadas Quantidade de moedas deixadas como recompensa ao ser derrotado.
     */
    public InimigoBoss(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        super(nome, vidaMaxima, vidaAtual, danoBase, dracmasDropadas);

        this.escudoMax = 100;
        this.escudoAtual = 100;
        this.turnoParaEspecial = 0;
        this.emFuria = false;
        this.random = new Random();
    }

    /**
     * Gerencia a IA avançada do chefe. Atualiza os medidores de turnos e fúria,
     * obriga o uso do ataque especial a cada 3 turnos e, nos turnos comuns,
     * escolhe entre atacar (60% de chance) ou defender e regenerar escudo (40%).
     * * @param jogador O herói que está enfrentando o chefe.
     */
    public void executarTurno(Personagem jogador) {
        this.turnoParaEspecial++;
        checarFuria();

        if (this.turnoParaEspecial >= 3) {
            usarAtaqueEspecial(jogador);
            this.turnoParaEspecial = 0;
            return;
        }

        int escolha = random.nextInt(100);

        if (escolha < 60) {
            this.atacar(jogador);
        } else {
            this.defender();
        }
    }

    /**
     * Executa o ataque básico do chefe. Se o Boss estiver abaixo de 50% de vida,
     * o modo de fúria adiciona automaticamente +10 pontos de dano bruto ao golpe.
     * * @param alvo O herói que vai sofrer o ataque.
     */
    @Override
    public void atacar(Personagem alvo) {
        int danoEfetivo = getDanoBase();
        if (this.emFuria) {
            danoEfetivo += 10;
            System.out.println("\n🔥 [BOSS ENFURECIDO] -> " + getNome() + " deserece um golpe esmagador!");
        } else {
            System.out.println("\n⚔️ [TURNO DO BOSS] -> " + getNome() + " ataca com sua arma pesada!");
        }

        alvo.receberDano(danoEfetivo);
    }

    /**
     * Dispara a habilidade suprema carregada do chefe, causando
     * o dobro do valor do seu dano base diretamente contra o herói.
     * * @param alvo O herói que receberá o golpe crítico.
     */
    public void usarAtaqueEspecial(Personagem alvo) {
        System.out.println("\n⚡ [HABILIDADE SUPREMA] -> " + getNome() + " está conjurando o golpe apocalíptico!");
        int danoEspecial = getDanoBase() * 2;
        alvo.receberDano(danoEspecial);
    }

    /**
     * Faz o chefe erguer as defesas, recuperando 25 pontos de escudo atual
     * sem ultrapassar o limite máximo da sua barra de postura.
     */
    @Override
    public void defender() {
        this.escudoAtual += 25;
        if (this.escudoAtual > this.escudoMax) {
            this.escudoAtual = this.escudoMax;
        }
        System.out.println("\n🛡️ [TURNO DO BOSS] -> " + getNome() + " ergue suas defesas e regenerou 25 de escudo! Escudo Atual: [" + this.escudoAtual + "/" + this.escudoMax + "]");
    }

    /**
     * Processa a redução de pontos vitais sofrida pelo chefe.
     * Os pontos de escudo absorvem o impacto primeiro e o dano restante perfura a vida.
     * * @param danoBruto O valor total do dano enviado pelo jogador.
     */
    @Override
    public void receberDano(int danoBruto) {
        int danoFinal = danoBruto;

        if (this.escudoAtual > 0) {
            if (danoFinal <= this.escudoAtual) {
                this.escudoAtual -= danoFinal;
                danoFinal = 0;
                System.out.println("🛡️ O escudo do Boss absorveu o golpe! Escudo Atual: [" + this.escudoAtual + "/" + this.escudoMax + "]");
            } else {
                danoFinal -= this.escudoAtual;
                this.escudoAtual = 0;
                System.out.println("💥 Você QUEBROU o escudo do Boss! O restante do dano passou para a vida.");
            }
        }

        if (danoFinal > 0) {
            int novaVida = getVidaAtual() - danoFinal;
            setVidaAtual(novaVida);
            System.out.println("💥 " + getNome() + " sofreu " + danoFinal + " de dano direto na vida! Vida Atual: [" + getVidaAtual() + "/" + getVidaMaxima() + "]");
        }
    }

    /**
     * Verifica a física do chefe. Caso a vida atual
     * caia para 50% ou menos do total, ativa de forma permanente o estado
     * de fúria, aumentando a potência dos ataques físicos básicos.
     */
    private void checarFuria() {
        if (!this.emFuria && getVidaAtual() <= (getVidaMaxima() / 2)) {
            this.emFuria = true;
            System.out.println("\n🚨 🚨 🚨 O " + getNome().toUpperCase() + " FICOU ENFURECIDO! OS ATAQUES DELE ESTÃO MAIS FORTES! 🚨 🚨 🚨");
        }
    }

    public int getEscudoAtual() { return escudoAtual; }
    public int getEscudoMax() { return escudoMax; }
    public boolean isEmFuria() { return emFuria; }
}