package personagens;

import interfaces.Combatente;

public abstract class Personagem implements Combatente {
    private String nome;
    private int vidaMaxima;
    private int vidaAtual;

    public Personagem(String nome, int vidaMaxima, int vidaAtual) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaAtual;
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

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public void atacar(Personagem personagem){

    }

    public void defender() {

    }
}
