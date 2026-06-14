package personagens.inimigos;

import personagens.Personagem;
import java.util.Random;

public class InimigoBoss extends Inimigo {
    private int escudoMax;
    private int escudoAtual;
    private int turnoParaEspecial; // Contador para controlar quando ele usa o "Ultimate"
    private boolean emFuria;
    private Random random;

    // Construtor do Boss: Note que ele pede status bem mais altos!
    public InimigoBoss(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        // Repassa os 5 parâmetros obrigatoriamente para a classe mãe Inimigo
        super(nome, vidaMaxima, vidaAtual, danoBase, dracmasDropadas);

        this.escudoMax = 100; // Boss tem o dobro de escudo máximo do jogador
        this.escudoAtual = 100;
        this.turnoParaEspecial = 0;
        this.emFuria = false;
        this.random = new Random();
    }

    /**
     * A IA do Boss é muito mais inteligente e perigosa!
     */
    public void executarTurno(Personagem jogador) {
        this.turnoParaEspecial++;
        checarFuria(); // Verifica se deve ativar o modo enfurecido

        // 1. Se o contador do especial carregar, ele usa o ataque supremo obrigatoriamente!
        if (this.turnoParaEspecial >= 3) {
            usarAtaqueEspecial(jogador);
            this.turnoParaEspecial = 0; // Reseta o carregamento
            return;
        }

        // 2. Se não for o turno do especial, ele decide entre atacar normal ou recuperar escudo
        int escolha = random.nextInt(100);

        if (escolha < 60) {
            this.atacar(jogador);
        } else {
            this.defender();
        }
    }

    @Override
    public void atacar(Personagem alvo) {
        // Se estiver em fúria, o dano base dele ganha +10 de bônus
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
     * O Ataque Especial do Boss (Ignora parte da defesa ou causa dano em área/crítico)
     */
    public void usarAtaqueEspecial(Personagem alvo) {
        System.out.println("\n⚡ [HABILIDADE SUPREMA] -> " + getNome() + " está conjurando o golpe apocalíptico!");
        int danoEspecial = getDanoBase() * 2; // Causa o DOBRO de dano
        alvo.receberDano(danoEspecial);
    }

    @Override
    public void defender() {
        System.out.println("\n🛡️ [TURNO DO BOSS] -> " + getNome() + " ergue suas defesas e regenera sua postura!");
        this.escudoAtual += 25; // Recupera muito mais escudo que um monstro comum
        if (this.escudoAtual > this.escudoMax) {
            this.escudoAtual = this.escudoMax;
        }
    }

    /**
     * O Boss possui escudo, então a lógica de receber dano dele é idêntica à do Jogador!
     */
    @Override
    public void receberDano(int danoBruto) {
        int danoFinal = danoBruto;

        // Se o Boss tiver escudo ativo, o escudo absorve primeiro
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

        // O dano que sobrou vai para a vida
        if (danoFinal > 0) {
            int novaVida = getVidaAtual() - danoFinal;
            setVidaAtual(novaVida);
            System.out.println("💥 " + getNome() + " sofreu " + danoFinal + " de dano direto na vida! Vida Atual: [" + getVidaAtual() + "/" + getVidaMaxima() + "]");
        }
    }

    /**
     * Método interno para verificar se o Boss entrou em modo de fúria
     */
    private void checarFuria() {
        if (!this.emFuria && getVidaAtual() <= (getVidaMaxima() / 2)) {
            this.emFuria = true;
            System.out.println("\n🚨 🚨 🚨 O " + getNome().toUpperCase() + " FICOU ENFURECIDO! OS ATAQUES DELE ESTÃO MAIS FORTES! 🚨 🚨 🚨");
        }
    }

    // --- GETTERS EXTRA CASO PRECISE NO GERENCIADOR ---
    public int getEscudoAtual() { return escudoAtual; }
    public int getEscudoMax() { return escudoMax; }
    public boolean isEmFuria() { return emFuria; }
}