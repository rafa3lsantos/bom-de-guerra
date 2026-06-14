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

    public static void exibirItemAdicionado(String nomeItem, int tamanhoAtual, int limiteMax) {
        System.out.println("\n🎒 [INVENTÁRIO] -> " + nomeItem + " foi guardado na sua bolsa.");
        System.out.println("   Espaço ocupado: [" + tamanhoAtual + "/" + limiteMax + "]");
    }

    public static void exibirInventarioCheio(String nomeItem) {
        System.out.println("\n⚠️  [⚠️ ALERTA] -> Não foi possível coletar: " + nomeItem);
        System.out.println("❌ Sua bolsa está completamente cheia! Limite de 20 itens atingido.");
        System.out.println("   Abra espaço no inventário ou venda itens para o Mercador.");
    }

    public static void exibirItemRemovido(String nomeItem, int tamanhoAtual, int limiteMax) {
        System.out.println("\n🗑️  [INVENTÁRIO] -> " + nomeItem + " foi removido da sua bolsa.");
        System.out.println("   Espaço restante: [" + tamanhoAtual + "/" + limiteMax + "]");
    }

    public static void exibirErroRemocao(String nomeItem) {
        System.out.println("\n❌ [ERRO] -> Não foi possível remover o item: " + nomeItem + ".");
        System.out.println("   Este item não foi encontrado no seu inventário.");
    }

    public static void exibirErroItemNaoPossuido(String nomeItem) {
        System.out.println("\n❌ [ERRO] -> Você não possui o item '" + nomeItem + "' no seu inventário!");
    }

    public static void exibirArmaGuardada(String nomeArmaAntiga) {
        System.out.println("🎒 [INVENTÁRIO] -> " + nomeArmaAntiga + " foi desequipada e guardada na bolsa.");
    }
    public static void exibirArmaduraGuardada(String nomeArmaduraAntiga) {
        System.out.println("🎒 [INVENTÁRIO] -> " + nomeArmaduraAntiga + " foi desequipada e guardada na bolsa.");
    }

    public static void exibirArmaEquipada(String nomeNovaArma, int danoBonus) {
        System.out.println("⚔️  [EQUIPAMENTO] -> " + nomeNovaArma + " foi empunhada com sucesso!");
        System.out.println("🔥 Seu bônus de ataque agora é de +" + danoBonus + " de dano místico.");
    }
    public static void exibirArmaduraEquipada(String nomeNovaArmadura, int valorDefesa) {
        System.out.println("⚔️  [EQUIPAMENTO] -> " + nomeNovaArmadura + " foi empunhada com sucesso!");
        System.out.println("🔥 Seu valor de proteção agora é de +" + valorDefesa);
    }

    public static void exibirAvisoSemArma(String nomeJogador) {
        System.out.println("\n👊 [COMBATE] -> " + nomeJogador + " não tem nenhuma arma equipada!");
        System.out.println("   Você vai lutar usando os seus punhos nus!");
    }

    public static void exibirAvisoItemNaoEquipavel (String nomeItem) {
        System.out.println("\n❌ [AÇÃO INVÁLIDA] -> O item '" + nomeItem + "' não pode ser equipado!");
        System.out.println("   Apenas Armas e Armaduras podem ser colocadas nos slots de equipamento.");
    }

    public static void exibirCuraVida(String nomeItem, int qtdCurada, int vidaAtual, int vidaMax) {
        System.out.println("\n🧪 [CONSUMÍVEL] -> Você tomou " + nomeItem + ".");
        System.out.println("❤️  +" + qtdCurada + " de Vida restaurada!");
        System.out.println("📊 Status Atualizado -> Vida: [" + vidaAtual + "/" + vidaMax + "]");
    }

    public static void exibirCuraEscudo(String nomeItem, int qtdCurada, int escudoAtual, int escudoMax) {
        System.out.println("\n🧪 [CONSUMÍVEL] -> Você tomou " + nomeItem + ".");
        System.out.println("🛡️  +" + qtdCurada + " de Escudo restaurado!");
        System.out.println("📊 Status Atualizado -> Escudo: [" + escudoAtual + "/" + escudoMax + "]");
    }

    public static void exibirErroItemNaoConsumivel(String nomeItem) {
        System.out.println("\n❌ [AÇÃO INVÁLIDA] -> O item '" + nomeItem + "' não é um consumível!");
        System.out.println("   Você não pode beber ou usar este item para recuperar status.");
    }
}
