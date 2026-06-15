package jogo;

import enums.TipoConsumivel;
import enums.TipoGenero;
import enums.TipoRaridade;
import excecoes.InventarioCheioException;
import itens.*;
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

        ArrayList<String> cenarios = new ArrayList<>();

        cenarios.add("Você atravessa as ruínas abandonadas de Micenas...");
        cenarios.add("Você caminha pelas florestas sagradas de Ártemis...");
        cenarios.add("Você desce as escadarias de mármore do Templo de Atena...");
        cenarios.add("Você adentra um antigo labirinto construído por Dédalo...");
        cenarios.add("Você cruza os campos dourados próximos ao Monte Olimpo...");
        cenarios.add("Você navega por uma costa assolada pelas tempestades de Poseidon...");
        cenarios.add("Você percorre os corredores esquecidos do Palácio de Cnossos...");
        cenarios.add("Você chega aos portões sombrios do Submundo...");
        cenarios.add("Você atravessa uma ponte sobre as águas negras do Rio Estige...");
        cenarios.add("Você explora uma caverna iluminada por cristais divinos...");
        cenarios.add("Você caminha entre as ruínas de uma cidade destruída pelos Titãs...");
        cenarios.add("Você atravessa um vale coberto pela névoa de Nix...");
        cenarios.add("Você alcança um altar abandonado dedicado a Hécate...");
        cenarios.add("Você se aproxima de uma arena esquecida pelos deuses...");
        cenarios.add("Você percorre uma praia onde sereias observam das rochas...");
        cenarios.add("Você atravessa uma floresta amaldiçoada pelas Erínias...");
        cenarios.add("Você chega ao topo de um penhasco voltado para o Mar Egeu...");
        cenarios.add("Você entra em uma forja colossal construída pelos Ciclopes...");
        cenarios.add("Você explora os jardins sagrados das Hespérides...");
        cenarios.add("Você pisa nas profundezas do Tártaro...");

        String cenario = cenarios.get( (int)(Math.random() * cenarios.size()) );

        System.out.println("\n=======================================================================");
        System.out.println(cenario);
        System.out.println("=======================================================================");

        ArrayList<Inimigo> monstros = new ArrayList<>();

        monstros.add(new InimigoComum("Minotauro de Creta", 60, 60, 12, 20));
        monstros.add(new InimigoComum("Ciclope Forjador", 80, 80, 15, 22));
        monstros.add(new InimigoComum("Harpia Sombria", 50, 50, 10, 15));
        monstros.add(new InimigoComum("Esqueleto de Esparta", 55, 55, 11, 14));
        monstros.add(new InimigoComum("Górgona Menor", 65, 65, 13, 18));
        monstros.add(new InimigoComum("Centauro Corrompido", 70, 70, 14, 20));
        monstros.add(new InimigoComum("Leão de Nemeia Jovem", 90, 90, 16, 25));
        monstros.add(new InimigoComum("Cão Infernal", 75, 75, 15, 21));
        monstros.add(new InimigoComum("Sátiro Enlouquecido", 45, 45, 9, 12));
        monstros.add(new InimigoComum("Soldado do Tártaro", 85, 85, 17, 24));
        monstros.add(new InimigoComum("Quimera Filhote", 95, 95, 18, 28));
        monstros.add(new InimigoComum("Serpente do Estige", 60, 60, 13, 17));
        monstros.add(new InimigoComum("Guardião de Delfos", 100, 100, 20, 30));
        monstros.add(new InimigoComum("Mirmidão Amaldiçoado", 80, 80, 16, 23));
        monstros.add(new InimigoComum("Dracenae Venenosa", 70, 70, 15, 22));

        inimigoAtual = monstros.get( (int)(Math.random() * monstros.size()) );

        System.out.println("\n⚔ Um " + inimigoAtual.getNome() + " surge diante de você!");
        System.out.println("Vida: " + inimigoAtual.getVidaAtual());
        System.out.println("Dano: " + inimigoAtual.getDanoBase());

        executarTurnoCombate();
    }

    private void executarTurnoCombate() {
        while (player.getVidaAtual() > 0 && inimigoAtual.getVidaAtual() > 0) {
            System.out.println("\n--- SEU TURNO ---");
            System.out.println("[1] Atacar   [2] Defender (Recuperar Escudo)");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1) {
                player.atacar(inimigoAtual);
                inimigoAtual.receberDano(20);
            } else {
                player.defender();
            }

            if (inimigoAtual.getVidaAtual() > 0) {
                System.out.println("\n--- TURNO DO INIMIGO ---");
                int danoInimigo = 15;
                System.out.println(inimigoAtual.getNome() + " contra-ataca!");
                player.receberDano(danoInimigo);
            }
        }

        if (player.getVidaAtual() <= 0) {
            System.out.println("Você foi derrotado e sua alma foi levada ao Tártaro...");
        } else {
            System.out.println("Vitória! Você derrotou o " + inimigoAtual.getNome());
            try {
                droparItemOlimpico();
            } catch (InventarioCheioException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void oferecerItemAoJogador(Item itemDropado) throws InventarioCheioException {
        System.out.println("\n=======================================================================");
        System.out.println("                     VOCÊ ENCONTROU UM ITEM!                             ");
        System.out.println("=======================================================================  ");
        System.out.println("Item: " + itemDropado.getNome());

        if (itemDropado instanceof ItemAtaque) {
            System.out.println("Tipo: Arma (Dano Bônus: +" + ((ItemAtaque) itemDropado).getDanoBonus() + ")");
        } else if (itemDropado instanceof ItemProtecao) {
            System.out.println("Tipo: Armadura (Defesa: " + ((ItemProtecao) itemDropado).getValorDefesa() + "%)");
        } else if (itemDropado instanceof ItemConsumivel) {
            System.out.println("Tipo: Consumível (Recuperação: " + ((ItemConsumivel) itemDropado).getValorRecuperacao() + ")");
        } else if (itemDropado instanceof Moeda) {
            System.out.println("Tipo: Moeda (Quantidade: " + ((Moeda) itemDropado).getQuantidade() + " Dracmas)");
        }
        System.out.println("Espaço atual na bolsa: " + player.getInventario().size() + "/20");
        System.out.println("=======================================================================");

        System.out.println("Deseja colocar este item na sua bolsa?");
        System.out.println("[1] Sim, coletar item");
        System.out.println("[2] Não, deixar para trás");
        System.out.print("Escolha: ");

        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            boolean conseguiuAdicionar = player.adquirirItemInventario(itemDropado);

            if (conseguiuAdicionar) {
                System.out.println("Item guardado com sucesso!");
            } else {
                System.out.println(" Você não conseguiu pegar o item porque seu inventário está lotado.");
                System.out.println("Dica: Vá ao menu do inventário e descarte algo velho se quiser abrir espaço.");
            }
        } else {
            System.out.println("Você desdenhou do item e o deixou apodrecer no chão da Grécia.");
        }
        System.out.println("=======================================================================");
    }

    private void droparItemOlimpico() throws InventarioCheioException {

        Item itemGerado = null;

        ArrayList<ItemAtaque> armas = new ArrayList<>();

        armas.add(new ItemAtaque("Adaga de Prata de Ártemis", TipoRaridade.EPICO, 20));
        armas.add(new ItemAtaque("Lança Forjada por Hefesto", TipoRaridade.COMUM, 10));
        armas.add(new ItemAtaque("Tridente de Bronze das Marés", TipoRaridade.LENDARIO, 22));
        armas.add(new ItemAtaque("Foice de Cronos", TipoRaridade.RARO, 12));
        armas.add(new ItemAtaque("Chicote de Hades", TipoRaridade.COMUM, 10));
        armas.add(new ItemAtaque("Espada de Aquiles", TipoRaridade.RARO, 12));
        armas.add(new ItemAtaque("Adaga da Medusa", TipoRaridade.EPICO, 20));
        armas.add(new ItemAtaque("Machado do Ciclopes", TipoRaridade.RARO, 12));
        armas.add(new ItemAtaque("Lança dos Argonautas", TipoRaridade.EPICO, 20));
        armas.add(new ItemAtaque("Espada Celestial de Zeus", TipoRaridade.LENDARIO, 22));

        ArrayList<ItemConsumivel> consumiveis = new ArrayList<>();

        consumiveis.add(new ItemConsumivel("Ambrósia Restauradora", TipoRaridade.RARO, 30, TipoConsumivel.CURA_VIDA));
        consumiveis.add(new ItemConsumivel("Néctar dos Deuses", TipoRaridade.COMUM, 20, TipoConsumivel.CURA_VIDA));
        consumiveis.add(new ItemConsumivel("Essência de Ícor", TipoRaridade.RARO, 25, TipoConsumivel.CURA_ESCUDO));
        consumiveis.add(new ItemConsumivel("Poção de Atena", TipoRaridade.EPICO, 40, TipoConsumivel.CURA_VIDA));
        consumiveis.add(new ItemConsumivel("Elixir Olímpico", TipoRaridade.LENDARIO, 50, TipoConsumivel.CURA_VIDA));
        consumiveis.add(new ItemConsumivel("Lágrima de Nix", TipoRaridade.RARO, 30, TipoConsumivel.CURA_ESCUDO));
        consumiveis.add(new ItemConsumivel("Essência Lunar", TipoRaridade.COMUM, 15, TipoConsumivel.CURA_ESCUDO));
        consumiveis.add(new ItemConsumivel("Frasco de Hermes", TipoRaridade.EPICO, 35, TipoConsumivel.CURA_VIDA));
        consumiveis.add(new ItemConsumivel("Sangue Divino", TipoRaridade.EPICO, 40, TipoConsumivel.CURA_ESCUDO));
        consumiveis.add(new ItemConsumivel("Poção do Olimpo", TipoRaridade.LENDARIO, 60, TipoConsumivel.CURA_VIDA));

        ArrayList<ItemProtecao> protecoes = new ArrayList<>();

        protecoes.add(new ItemProtecao("Égide de Atena", TipoRaridade.LENDARIO, 30));
        protecoes.add(new ItemProtecao("Peitoral de Guerra de Ares", TipoRaridade.EPICO, 25));
        protecoes.add(new ItemProtecao("Capacete de Hades", TipoRaridade.RARO, 18));
        protecoes.add(new ItemProtecao("Escudo dos Argonautas", TipoRaridade.COMUM, 12));
        protecoes.add(new ItemProtecao("Armadura do Olimpo", TipoRaridade.LENDARIO, 35));
        protecoes.add(new ItemProtecao("Braçadeiras de Hércules", TipoRaridade.RARO, 16));
        protecoes.add(new ItemProtecao("Manto de Perséfone", TipoRaridade.EPICO, 22));
        protecoes.add(new ItemProtecao("Escudo de Esparta", TipoRaridade.COMUM, 10));
        protecoes.add(new ItemProtecao("Armadura de Bronze Sagrada", TipoRaridade.RARO, 20));
        protecoes.add(new ItemProtecao("Coroa Defensiva de Atena", TipoRaridade.EPICO, 24));

        double chanceCategoria = Math.random();

        if (chanceCategoria < 0.25) {
            int[] valoresMoeda = {10,20,30,40,50,60,70,80,90,100};
            int indice = (int) (Math.random() * valoresMoeda.length);
            itemGerado = new Moeda(valoresMoeda[indice]);
        } else if (chanceCategoria < 0.50) {
            itemGerado = consumiveis.get( (int) (Math.random() * consumiveis.size()) );
        } else if (chanceCategoria < 0.75) {
            itemGerado = armas.get( (int) (Math.random() * armas.size()) );
        } else {
            itemGerado = protecoes.get( (int) (Math.random() * protecoes.size()) );
        }

        oferecerItemAoJogador(itemGerado);
    }

    private void gerenciarInventario() {
        System.out.println("\n=======================================================================");
        System.out.println("                          SEU INVENTÁRIO:                                ");
        System.out.println("=======================================================================  ");

        if (player.getInventario().isEmpty()) {
            System.out.println("Sua bolsa está vazia. Vá derrotar uns monstros para conseguir itens!");
            System.out.println("=======================================================================");
            return;
        }

        for (int i = 0; i < player.getInventario().size(); i++) {
            Item item = player.getInventario().get(i);
            System.out.println("[" + i + "] " + item.getNome());
        }
        System.out.println("[" + player.getInventario().size() + "] Voltar ao menu anterior");
        System.out.println("=======================================================================");

        System.out.print("Escolha o número do item para interagir: ");
        int indiceItem = scanner.nextInt();
        scanner.nextLine();

        if (indiceItem == player.getInventario().size()) {
            System.out.println("Fechando inventário...");
            return;
        }

        if (indiceItem < 0 || indiceItem >= player.getInventario().size()) {
            System.out.println("Esse item não existe na sua bolsa.");
            return;
        }

        Item itemEscolhido = player.getInventario().get(indiceItem);

        System.out.println("\nVocê selecionou: " + itemEscolhido.getNome());
        System.out.println("[1] Usar (Se for Consumível/Poção)");
        System.out.println("[2] Equipar (Se for Arma ou Armadura)");
        System.out.println("[3] Descartar (Apagar da bolsa)");
        System.out.println("[4] Voltar");
        System.out.print("O que deseja fazer? ");
        int acao = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        switch (acao) {
            case 1:
                player.usarItemConsumivel(itemEscolhido);
                break;

            case 2:
                player.equiparItem(itemEscolhido);
                break;

            case 3:
                System.out.print("Tem certeza que quer jogar " + itemEscolhido.getNome() + " fora? (1-Sim / 2-Não): ");
                int confirmar = scanner.nextInt();
                scanner.nextLine();
                if (confirmar == 1) {
                    player.removerItemInventario(itemEscolhido);
                } else {
                    System.out.println("Ação cancelada.");
                }
                break;

            case 4:
                System.out.println("Voltando...");
                break;

            default:
                System.out.println(" Opção inválida!");
                break;
        }
    }

    private void exibirFimDeJogo() {
        System.out.println("\nObrigado por jogar Crônicas do Olimpo!");
    }

}
