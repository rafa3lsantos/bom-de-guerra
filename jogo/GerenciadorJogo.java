package jogo;

import enums.TipoGenero;
import enums.TipoRaridade;
import itens.ItemAtaque;
import itens.ItemProtecao;
import personagens.Personagem;
import personagens.inimigos.Inimigo;
import personagens.inimigos.InimigoComum;
import personagens.jogador.Jogador;

import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorJogo {
    private Jogador player;
    private Inimigo inimigoAtual;
    private Scanner scanner;
    private boolean jogoRodando;


    public GerenciadorJogo () {
        this.scanner = new Scanner(System.in);
        this.jogoRodando = true;
    }

    public void iniciarJogo () {
        exibirIntroducao ();
        inicializarJogador ();

        while (jogoRodando && player.getVidaAtual() > 0) {
            menuPrincipal ();
        }

        exibirFimDeJogo ();
    }

    private void exibirIntroducao() {
        System.out.println("=======================================================================");
        System.out.println("                     CRÔNICAS DO OLIMPO: O DESPERTAR                   ");
        System.out.println("=======================================================================");
        System.out.println("O céu sobre a Grécia Antiga não é mais o mesmo. O Monte Olimpo racha");
        System.out.println("sob o peso de uma força ancestral: Cronos, o Titã do Tempo, está se");
        System.out.println("libertando de sua prisão no Tártaro. Monstros mitológicos outrora");
        System.out.println("banidos agora vagam pelas terras dos mortais, espalhando o caos.");
        System.out.println();
        System.out.println("Os deuses, enfraquecidos pela quebra das correntes temporais, decidiram");
        System.out.println("apostar suas últimas esperanças em um herói mortal carregado de sangue");
        System.out.println("divino — um semideus.");
        System.out.println();
        System.out.println("Você acorda nos limites do Santuário de Delfos. O Oráculo lhe deu uma");
        System.out.println("missão clara: marchar até as profundezas do Submundo e impedir o");
        System.out.println("despertar do Titã. O destino do Olimpo e da humanidade está em suas mãos.");
        System.out.println("=======================================================================");
        System.out.println("Pressione ENTER para começar sua jornada...");
        scanner.nextLine();
    }

    private void inicializarJogador() {
        System.out.println("\n--- Criação de Personagem ---");
        System.out.print("Digite o nome do seu Semideus: ");
        String nome = scanner.nextLine();

        ItemAtaque espadaBronze = new ItemAtaque("Espada de Bronze Celestial", TipoRaridade.COMUM, 5);
        ItemProtecao gibaoCouro = new ItemProtecao("Gibão de Couro de Nemeia", TipoRaridade.RARO, 10);

        this.player = new Jogador(nome, 100, 100, 20, espadaBronze, gibaoCouro, new ArrayList<>(), 50);

        this.player.setGenero(TipoGenero.MASCULINO);

        System.out.println("\nHerói " + player.getNome() + " criado com sucesso!");
        System.out.println("Você começa com uma " + espadaBronze.getNome() + " equipada.");
        System.out.println("=======================================================================");
    }

    private void menuPrincipal() {
        System.out.println("\n[1] Explorar as Ruínas (Combate)");
        System.out.println("[2] Olhar Bolsa (Inventário)");
        System.out.println("[3] Sair do Jogo");
        System.out.print("Escolha: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        switch (escolha) {
            case 1:
                gerarProximoCombate();
                break;
            case 2:
                gerenciarInventario();
                break;
            case 3:
                jogoRodando = false;
                break;
        }
    }

    private void gerarProximoCombate() {
        System.out.println("\nVocê entra em uma câmara escura...");

        double chance = Math.random();
        if (chance < 0.5) {
            this.inimigoAtual = new InimigoComum("Minotauro de Creta", 60, 12);
            System.out.println("Amedrontador! Um " + inimigoAtual.getNome() + " surge dos labirintos!");
        } else {
            this.inimigoAtual = new Ciclope("Ciclope Forjador", 80, 15);
            System.out.println("O chão treme! Um " + inimigoAtual.getNome() + " bloqueia sua saída!");
        }

        // Inicia o loop de combate passando as instâncias que guardamos aqui
        executarTurnoCombate();
    }

    private void executarTurnoCombate() {
        // Exemplo de loop de combate usando os métodos que você já programou na classe Jogador
        while (player.getVidaAtual() > 0 && inimigoAtual.getVidaAtual() > 0) {
            System.out.println("\n--- SEU TURNO ---");
            System.out.println("[1] Atacar   [2] Defender (Recuperar Escudo)");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1) {
                player.atacar(inimigoAtual); // Seu método atacar
                // inimigoAtual.receberDano(danoCalculado); // Lógica do monstro
            } else {
                player.defender(); // Seu método defender que recupera escudo
            }

            // Se o monstro sobreviveu, ele ataca de volta
            if (inimigoAtual.getVidaAtual() > 0) {
                System.out.println("\n--- TURNO DO INIMIGO ---");
                // Supondo que o inimigo tem um método getForca()
                int danoInimigo = 15;
                System.out.println(inimigoAtual.getNome() + " contra-ataca!");
                player.receberDano(danoInimigo); // Seu método complexo de absorção por escudo/armadura
            }
        }

        if (player.getVidaAtual() <= 0) {
            System.out.println("💀 Você foi derrotado e sua alma foi levada ao Tártaro...");
        } else {
            System.out.println("🎉 Vitória! Você derrotou o " + inimigoAtual.getNome());
            droparItemOlimpico();
        }
    }

    private void droparItemOlimpico() {
        // 6. INSTANCIANDO ITENS RECOMPENSA
        System.out.println("\nO monstro deixou algo cair no chão!");
        ItemConsumivel pocaoAmbrosia = new ItemConsumivel("Ambrósia Divina", TipoRecuperacao.CURA_VIDA, 30);

        // Chamando seu método adquirirItemInventario que valida os 20 espaços e agrupa moedas!
        player.adquirirItemInventario(pocaoAmbrosia);
    }

    private void gerenciarInventario() {
        System.out.println("\n--- Seu Inventário ---");
        if (player.getInventario().isEmpty()) {
            System.out.println("Sua bolsa está vazia.");
            return;
        }

        for (int i = 0; i < player.getInventario().size(); i++) {
            System.out.println(i + " - " + player.getInventario().get(i).getNome());
        }
        // Aqui você poderia dar a opção de digitar o número do item e chamar:
        // player.usarItemConsumivel(item) ou player.equiparItem(item)
    }

    private void exibirFimDeJogo() {
        System.out.println("\nObrigado por jogar Crônicas do Olimpo!");
    }

}
