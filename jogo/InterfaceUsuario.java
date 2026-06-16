package jogo;

import itens.ItemAtaque;

import java.util.ArrayList;

/**
 * Classe responsável por centralizar todas as mensagens de texto do jogo.
 * Ela cuida de deixar os menus de combate, inventário, equipamentos e uso
 * de poções organizados e bonitos no terminal para o usuário.
 * * @author Rafael e Rafaela
 */
public class InterfaceUsuario {

    /**
     * Mostra o cabeçalho inicial quando um combate contra um monstro começa.
     * * @param nomeJogador O nome do herói.
     * @param nomeInimigo O nome do monstro desafiado.
     */
    public static void exibirInicioCombate(String nomeJogador, String nomeInimigo) {
        System.out.println("\n⚔️ ======================================== ⚔️");
        System.out.println("   " + nomeJogador.toUpperCase() + " DESAFIOU O MONSTRO: " + nomeInimigo.toUpperCase() + "!");
        System.out.println("============================================");
    }

    /**
     * Imprime o relatório detalhado de quanto dano o monstro deu e quanto
     * a armadura do jogador conseguiu mitigar.
     * * @param danoBruto       O dano inicial do monstro sem defesa.
     * @param percentualDefesa O valor de proteção da armadura em %.
     * @param danoMitigado    A quantidade de dano que a armadura barrou.
     * @param danoFinal       O dano que sobrou e vai atingir o jogador.
     */
    public static void exibirRelatorioDano(int danoBruto, int percentualDefesa, int danoMitigado, int danoFinal) {
        System.out.println("\n💥 IMPACTO!");
        System.out.println("   Dano Bruto do Inimigo: " + danoBruto);
        System.out.println("   Sua Armadura reduziu " + percentualDefesa + "% do dano (-" + danoMitigado + ").");
        System.out.println("   Dano Real que perfurou a defesa: " + danoFinal);
    }

    /**
     * Mostra o aviso rápido sobre o estado do escudo do herói durante a pancadaria.
     * * @param status O estado atual do escudo ("TOTAL" para segurar tudo ou "QUEBRADO").
     */
    public static void exibirMensagemEscudo(String status) {
        if (status.equals("TOTAL")) {
            System.out.println("🛡️  Seu escudo absorveu todo o impacto!");
        } else if (status.equals("QUEBRADO")) {
            System.out.println("💥 Seu escudo foi QUEBRADO! O restante do dano passou.");
        }
    }

    /**
     * Mostra uma barra de status simples com os pontos de vida e escudo de qualquer personagem.
     * * @param nome      Nome do dono dos status.
     * @param vida      Quantidade de vida atual.
     * @param vidaMax   Limite máximo de vida.
     * @param escudo    Quantidade de escudo atual.
     * @param escudoMax Limite máximo de escudo.
     */
    public static void exibirStatusPersonagem(String nome, int vida, int vidaMax, int escudo, int escudoMax) {
        System.out.println("📊 STATUS [" + nome + "] -> Vida: " + vida + "/" + vidaMax + " | Escudo: " + escudo + "/" + escudoMax);
    }

    /**
     * Avisa no terminal com qual arma ou equipamento o personagem realizou seu ataque.
     * * @param possuiArma        Coloque true se o jogador tiver uma arma equipada.
     * @param nome              Nome do atacante.
     * @param nomeArmaEquipada  A arma usada (pode ser o objeto da arma ou null para punhos).
     */
    public static void atacou(boolean possuiArma, String nome, ItemAtaque nomeArmaEquipada){
        if(possuiArma) {
            System.out.println(nome + " atacou com " + nomeArmaEquipada.getNome() + "!");
        } else {
            System.out.println(nome + " atacou com os " + nomeArmaEquipada);
        }
    }

    /**
     * Mostra uma mensagem de sucesso confirmando que um item entrou na bolsa.
     * * @param nomeItem     O nome do item coletado.
     * @param tamanhoAtual Quantos slots da bolsa estão cheios agora.
     * @param limiteMax    O tamanho máximo total do inventário.
     */
    public static void exibirItemAdicionado(String nomeItem, int tamanhoAtual, int limiteMax) {
        System.out.println("\n🎒 [INVENTÁRIO] -> " + nomeItem + " foi guardado na sua bolsa.");
        System.out.println("   Espaço ocupado: [" + tamanhoAtual + "/" + limiteMax + "]");
    }

    /**
     * Avisa que o item não pôde ser coletado porque o inventário está lotado.
     * * @param nomeItem O nome do item que o jogador tentou pegar e perdeu.
     */
    public static void exibirInventarioCheio(String nomeItem) {
        System.out.println("\n⚠️  [⚠️ ALERTA] -> Não foi possível coletar: " + nomeItem);
        System.out.println("❌ Sua bolsa está completamente cheia! Limite de 20 itens atingido.");
        System.out.println("   Abra espaço no inventário ou venda itens para o Mercador.");
    }

    /**
     * Confirma na tela que um item foi jogado fora ou removido da bolsa.
     * * @param nomeItem     O nome do item descartado.
     * @param tamanhoAtual Slots ocupados na bolsa após a remoção.
     * @param limiteMax    O tamanho máximo total do inventário.
     */
    public static void exibirItemRemovido(String nomeItem, int tamanhoAtual, int limiteMax) {
        System.out.println("\n🗑️  [INVENTÁRIO] -> " + nomeItem + " foi removido da sua bolsa.");
        System.out.println("   Espaço restante: [" + tamanhoAtual + "/" + limiteMax + "]");
    }

    /**
     * Avisa que o item selecionado para exclusão não existe dentro da lista.
     * * @param nomeItem O nome do item que deu erro ao tentar remover.
     */
    public static void exibirErroRemocao(String nomeItem) {
        System.out.println("\n❌ [ERRO] -> Não foi possível remover o item: " + nomeItem + ".");
        System.out.println("   Este item não foi encontrado no seu inventário.");
    }

    /**
     * Erro disparado se o jogador tentar interagir, equipar ou usar algo que não está na bolsa.
     * * @param nomeItem Nome do item procurado.
     */
    public static void exibirErroItemNaoPossuido(String nomeItem) {
        System.out.println("\n❌ [ERRO] -> Você não possui o item '" + nomeItem + "' no seu inventário!");
    }

    /**
     * Informa que a arma antiga do slot ativo foi retirada e guardada de volta na bolsa.
     * * @param nomeArmaAntiga Nome da arma que foi desequipada.
     */
    public static void exibirArmaGuardada(String nomeArmaAntiga) {
        System.out.println("🎒 [INVENTÁRIO] -> " + nomeArmaAntiga + " foi desequipada e guardada na bolsa.");
    }

    /**
     * Informa que a armadura antiga do slot ativo foi retirada e guardada de volta na bolsa.
     * * @param nomeArmaduraAntiga Nome da armadura que foi desequipada.
     */
    public static void exibirArmaduraGuardada(String nomeArmaduraAntiga) {
        System.out.println("🎒 [INVENTÁRIO] -> " + nomeArmaduraAntiga + " foi desequipada e guardada na bolsa.");
    }

    /**
     * Comemora que o jogador equipou uma nova arma e mostra o bônus de dano ganho.
     * * @param nomeNovaArma Nome do item equipado.
     * @param danoBonus    O bônus numérico de ataque da arma.
     */
    public static void exibirArmaEquipada(String nomeNovaArma, int danoBonus) {
        System.out.println("⚔️  [EQUIPAMENTO] -> " + nomeNovaArma + " foi empunhada com sucesso!");
        System.out.println("🔥 Seu bônus de ataque agora é de +" + danoBonus + " de dano místico.");
    }

    /**
     * Comemora que o jogador vestiu uma nova armadura e mostra o valor de defesa ganho.
     * * @param nomeNovaArmadura Nome da armadura equipada.
     * @param valorDefesa      O bônus numérico de proteção do item.
     */
    public static void exibirArmaduraEquipada(String nomeNovaArmadura, int valorDefesa) {
        System.out.println("⚔️  [EQUIPAMENTO] -> " + nomeNovaArmadura + " foi empunhada com sucesso!");
        System.out.println("🔥 Seu valor de proteção agora é de +" + valorDefesa);
    }

    /**
     * Alerta dramático avisando que o herói vai lutar na mão limpa.
     * * @param nomeJogador Nome do herói esquecido.
     */
    public static void exibirAvisoSemArma(String nomeJogador) {
        System.out.println("\n👊 [COMBATE] -> " + nomeJogador + " não tem nenhuma arma equipada!");
        System.out.println("   Você vai lutar usando os seus punhos nus!");
    }

    /**
     * Erro exibido se o jogador tentar colocar poções ou moedas nos slots de equipamentos.
     * * @param nomeItem Nome do item inválido escolhido.
     */
    public static void exibirAvisoItemNaoEquipavel (String nomeItem) {
        System.out.println("\n❌ [AÇÃO INVÁLIDA] -> O item '" + nomeItem + "' não pode ser equipado!");
        System.out.println("   Apenas Armas e Armaduras podem ser colocadas nos slots de equipamento.");
    }

    /**
     * Avisa o sucesso do consumo de uma poção de vida e mostra o ganho atualizado.
     * * @param nomeItem   Nome do consumível usado.
     * @param qtdCurada  Quantos pontos de vida foram restaurados.
     * @param vidaAtual  Vida final do jogador após beber.
     * @param vidaMax    Limite máximo de vida do herói.
     */
    public static void exibirCuraVida(String nomeItem, int qtdCurada, int vidaAtual, int vidaMax) {
        System.out.println("\n🧪 [CONSUMÍVEL] -> Você tomou " + nomeItem + ".");
        System.out.println("❤️  +" + qtdCurada + " de Vida restaurada!");
        System.out.println("📊 Status Atualizado -> Vida: [" + vidaAtual + "/" + vidaMax + "]");
    }

    /**
     * Avisa o sucesso do consumo de uma poção de escudo e mostra o ganho atualizado.
     * * @param nomeItem   Nome do consumível usado.
     * @param qtdCurada  Quantos pontos de escudo foram restaurados.
     * @param escudoAtual Escudo final do jogador após beber.
     * @param escudoMax  Limite máximo de escudo do herói.
     */
    public static void exibirCuraEscudo(String nomeItem, int qtdCurada, int escudoAtual, int escudoMax) {
        System.out.println("\n🧪 [CONSUMÍVEL] -> Você tomou " + nomeItem + ".");
        System.out.println("🛡️  +" + qtdCurada + " de Escudo restaurado!");
        System.out.println("📊 Status Atualizado -> Escudo: [" + escudoAtual + "/" + escudoMax + "]");
    }

    /**
     * Erro exibido se o usuário tentar beber armas, moedas ou escudos.
     * * @param nomeItem Nome do item que ele tentou usar.
     */
    public static void exibirErroItemNaoConsumivel(String nomeItem) {
        System.out.println("\n❌ [AÇÃO INVÁLIDA] -> O item '" + nomeItem + "' não é um consumível!");
        System.out.println("   Você não pode beber ou usar este item para recuperar status.");
    }
}