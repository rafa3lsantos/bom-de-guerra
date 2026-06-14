package personagens;

import interfaces.Combatente;

/**
 * Classe base (mãe) para qualquer criatura que entre em combate no jogo.
 * Serve de molde para o Jogador e os Inimigos, garantindo que todos tenham
 * nome, controle de vida e saibam lutar.
 * * @author Rafael
 */
public abstract class Personagem implements Combatente {
    private String nome;
    private int vidaMaxima;
    private int vidaAtual;

    /**
     * Construtor para nascer um personagem definindo seus dados vitais básicos.
     * * @param nome       Nome do personagem.
     * @param vidaMaxima Limite máximo de vida que ele pode ter.
     * @param vidaAtual  Vida inicial com que ele vai começar.
     */
    public Personagem(String nome, int vidaMaxima, int vidaAtual) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaAtual;
    }

    /**
     * Executa um ataque contra um oponente.
     * Como cada criatura ataca de um jeito, a regra exata de dano
     * é decidida pelas classes filhas.
     * * @param alvo O personagem que vai tomar o golpe.
     */
    public abstract void atacar(Personagem alvo);

    /**
     * Faz o personagem se defender para diminuir ou evitar o dano do próximo golpe.
     */
    public abstract void defender();

    /**
     * Aplica o dano sofrido direto nos status do personagem.
     * A forma como o dano é calculado (usando armadura, escudo, etc)
     * muda dependendo de quem está apanhando.
     * * @param danoBruto O valor total do dano vindo do atacante antes das reduções.
     */
    public abstract void receberDano(int danoBruto);

    /**
     * Vê se o personagem ainda está de pé para continuar lutando.
     * * @return true se a vida for maior que zero; false se ele foi derrotado.
     */
    public boolean isVivo() {
        return this.vidaAtual > 0;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    /**
     * Modifica a vida atual do personagem usando travas de segurança:
     * a vida nunca fica abaixo de zero e nunca passa do limite máximo.
     * * @param vidaAtual O novo valor de pontos de vida.
     */
    public void setVidaAtual(int vidaAtual) {
        if (vidaAtual < 0) {
            this.vidaAtual = 0;
        } else if (vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        } else {
            this.vidaAtual = vidaAtual;
        }
    }
}