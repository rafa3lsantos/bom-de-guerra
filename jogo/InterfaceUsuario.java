package jogo;

import itens.ItemAtaque;

public class InterfaceUsuario {

    public static void exibirInicioCombate(String nomeJogador, String nomeInimigo) {
        System.out.println("\n⚔️ ======================================== ⚔️");
        System.out.println("   " + nomeJogador.toUpperCase() + " DESAFIOU O MONSTRO: " + nomeInimigo.toUpperCase() + "!");
        System.out.println("============================================");
    }

    public static void exibirRelatorioDano(int danoBruto, int percentualDefesa, int danoMitigado, int danoFinal) {
        System.out.println("\n💥 IMPACTO!");
        System.out.println("   Dano Bruto do Inimigo: " + danoBruto);
        System.out.println("   Sua Armadura reduziu " + percentualDefesa + "% do dano (-" + danoMitigado + ").");
        System.out.println("   Dano Real que perfurou a defesa: " + danoFinal);
    }

    public static void exibirMensagemEscudo(String status) {
        if (status.equals("TOTAL")) {
            System.out.println("🛡️  Seu escudo absorveu todo o impacto!");
        } else if (status.equals("QUEBRADO")) {
            System.out.println("💥 Seu escudo foi QUEBRADO! O restante do dano passou.");
        }
    }

    public static void exibirStatusPersonagem(String nome, int vida, int vidaMax, int escudo, int escudoMax) {
        System.out.println("📊 STATUS [" + nome + "] -> Vida: " + vida + "/" + vidaMax + " | Escudo: " + escudo + "/" + escudoMax);
    }

    public static void atacou(boolean possuiArma, String nome, ItemAtaque nomeArmaEquipada){
        if(possuiArma) {
            System.out.println(nome + " atacou com " + nomeArmaEquipada + "!");
        } else {
            System.out.println(nome + " atacou com os " + nomeArmaEquipada);
        }
    }
}
