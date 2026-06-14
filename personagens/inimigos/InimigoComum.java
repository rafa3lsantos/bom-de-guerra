package personagens.inimigos;

import personagens.Personagem;
import jogo.InterfaceUsuario;
import java.util.Random;

public class InimigoComum extends Inimigo {
    private int danoBase;
    private int dracmasDropadas;
    private Random random;

    // Construtor: o inimigo comum nasce com seus atributos de combate e recompensa
    public InimigoComum(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        // Passa o nome e a vida para a classe mãe (Inimigo -> Personagem)
        super(nome, vidaMaxima, vidaAtual, danoBase, dracmasDropadas);
        this.random = new Random();
    }

    /**
     * O cérebro do monstro. O GerenciadorJogo chama isso no turno dele.
     */
    public void executarTurno(Personagem jogador) {
        // Gera um número de 0 a 99 para criar as probabilidades de ação
        int escolha = random.nextInt(100);

        if (escolha < 75) {
            // 75% de chance de atacar o jogador
            this.atacar(jogador);
        } else {
            // 25% de chance de se defender/rosnar
            this.defender();
        }
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n⚔️ [TURNO DO INIMIGO] -> " + getNome() + " avança furioso para atacar!");

        // Aplica o dano direto no método receberDano do Jogador
        alvo.receberDano(this.danoBase);
    }

    @Override
    public void defender() {
        System.out.println("\n🛡️ [TURNO DO INIMIGO] -> " + getNome() + " adotou uma postura defensiva para mitigar o próximo impacto!");
        // Aqui você pode adicionar lógica de escudo para o inimigo se quiser, ou deixar como um turno de preparação
    }

    @Override
    public void receberDano(int danoBruto) {
        // Conta simples de dano para o monstro comum (subtrai direto da vida)
        int novaVida = getVidaAtual() - danoBruto;
        setVidaAtual(novaVida);

        System.out.println("💥 " + getNome() + " sofreu " + danoBruto + " de dano!");
    }

    // --- GETTERS ---
    public int getDanoBase() {
        return danoBase;
    }

    public int getDracmasDropadas() {
        return dracmasDropadas;
    }
}