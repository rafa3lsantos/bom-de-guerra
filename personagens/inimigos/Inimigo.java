package personagens.inimigos;

import personagens.Personagem;
import jogo.InterfaceUsuario;
import java.util.Random;

public class Inimigo extends Personagem {
    private int danoBase;
    private int dracmasDropadas;
    private Random random;

    public Inimigo(String nome, int vidaMaxima, int vidaAtual, int danoBase, int dracmasDropadas) {
        // Passa o nome e a vida para a classe mãe (Personagem)
        super(nome, vidaMaxima, vidaAtual);
        this.danoBase = danoBase;
        this.dracmasDropadas = dracmasDropadas;
        this.random = new Random();
    }

    public void executarTurno(Personagem jogador) {
        // Gera um número de 0 a 99 para criar as probabilidades
        int escolha = random.nextInt(100);

        if (escolha < 70) {
            // 70% de chance de atacar
            this.atacar(jogador);
        } else {
            // 30% de chance de defender
            this.defender();
        }
    }

    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n⚔️ [TURNO DO INIMIGO] -> " + getNome() + " avança para atacar!");

        // Aplica o dano bruto direto no método receberDano do jogador
        alvo.receberDano(this.danoBase);
    }

    @Override
    public void defender() {
        System.out.println("\n🛡️ [TURNO DO INIMIGO] -> " + getNome() + " rosnou e adotou uma postura defensiva!");
        // Aqui você pode dar escudo para o inimigo ou ativar um modificador de defesa
    }

    // Método que o GerenciadorJogo usa para aplicar o dano bruto sofrido
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
