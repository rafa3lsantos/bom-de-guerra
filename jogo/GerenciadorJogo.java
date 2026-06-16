package jogo;

import enums.TipoConsumivel;
import enums.TipoGenero;
import enums.TipoRaridade;
import excecoes.InventarioCheioException;
import excecoes.SaldoInsuficienteException;
import itens.*;
import personagens.inimigos.Inimigo;
import personagens.inimigos.InimigoBoss;
import personagens.inimigos.InimigoComum;
import personagens.jogador.Jogador;

import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorJogo {
    private Jogador player;
    private Inimigo inimigoAtual;
    private Scanner scanner;
    private boolean jogoRodando;
    private Mercador mercador;
    private int faseAtual;
    private int monstrosDerrotadosNaFase;



    public GerenciadorJogo () {
        this.scanner = new Scanner(System.in);
        this.jogoRodando = true;
        this.mercador = new Mercador("Hermes");
        this.faseAtual = 1;
        this.monstrosDerrotadosNaFase = 0;

    }

    public void iniciarJogo () throws InventarioCheioException {
        exibirIntroducao();

        // TENTA CARREGAR O JOGO AQUI 👇
        Jogador saveAntigo = GerenciadorArquivo.carregarJogo();

        if (saveAntigo != null) {
            System.out.println("[1] Continuar de onde parou (Herói: " + saveAntigo.getNome() + ")");
            System.out.println("[2] Começar uma Nova Jornada");
            System.out.print("Escolha: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                this.player = saveAntigo;
                inicializarMercador(); // Só carrega o mercador e pula a criação
            } else {
                inicializarJogador(); // Cria do zero
            }
        } else {
            inicializarJogador(); // Não tem save, cria do zero direto
        }

        while (jogoRodando && player.getVidaAtual() > 0) {
            menuPrincipal();
        }

        exibirFimDeJogo();
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

    private void inicializarMercador() {
        mercador.addEstoque(new ItemConsumivel("Poção de Vida", TipoRaridade.COMUM, 40, TipoConsumivel.CURA_VIDA ));
        mercador.addEstoque(new ItemConsumivel("Poção de Escudo", TipoRaridade.COMUM, 35, TipoConsumivel.CURA_ESCUDO));
        mercador.addEstoque(new ItemAtaque("Espada de Ouro", TipoRaridade.RARO, 18));
        mercador.addEstoque(new ItemAtaque("Lança Olímpica", TipoRaridade.EPICO, 25));
        mercador.addEstoque(new ItemProtecao("Armadura de Atena", TipoRaridade.EPICO, 25));
        mercador.addEstoque(new ItemProtecao("Escudo Divino", TipoRaridade.LENDARIO, 35));
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
        inicializarMercador();
    }

    private void menuPrincipal() throws InventarioCheioException {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("[1] Explorar as Ruínas (Combate)");
        System.out.println("[2] Olhar Bolsa (Inventário)");
        System.out.println("[3] Salvar o Progresso"); // 👇 OPÇÃO NOVA!
        System.out.println("[4] Sair do Jogo");
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
                // 👇 CHAMA O SALVAMENTO AQUI
                GerenciadorArquivo.salvarJogo(this.player);
                break;
            case 4:
                System.out.println("Saindo... Que os deuses o acompanhem!");
                jogoRodando = false;
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void gerarProximoCombate() throws InventarioCheioException {

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
        System.out.println(cenario + " [Fase " + faseAtual + " - Progresso: " + monstrosDerrotadosNaFase + "/4]");
        System.out.println("=======================================================================");


        if (monstrosDerrotadosNaFase < 4) {
            ArrayList<Inimigo> monstros = new ArrayList<>();
            monstros.add(new InimigoComum("Minotauro de Creta", 60, 60, 12, 20));
            monstros.add(new InimigoComum("Ciclope Forjador", 80, 80, 15, 22));
            monstros.add(new InimigoComum("Harpia Sombria", 50, 50, 10, 15));
            monstros.add(new InimigoComum("Esqueleto de Esparta", 55, 55, 11, 14));
            monstros.add(new InimigoComum("Górgona Menor", 65, 65, 13, 18));
            monstros.add(new InimigoComum("Centauro Corrompido", 70, 70, 14, 20));
            monstros.add(new InimigoComum("Leão de Nemeia Jovem", 90, 90, 16, 25));
            monstros.add(new InimigoComum("Cão Infernal", 75, 75, 15, 21));

            inimigoAtual = monstros.get( (int)(Math.random() * monstros.size()) );
            System.out.println("\nUm " + inimigoAtual.getNome() + " bloqueia seu caminho!");
        } else {
            System.out.println("\n O CLIMA MUDA REVOLTADO... O BOSS DA FASE SE APROXIMA... ");

            switch (faseAtual) {
                case 1:
                    // Importe ou use o pacote correto de InimigoBoss
                    inimigoAtual = new InimigoBoss("Medusa, a Rainha das Górgonas", 100, 90, 22, 100);
                    break;
                case 2:
                    inimigoAtual = new InimigoBoss("Cérbero, o Cão do Submundo", 120, 100, 28, 200);
                    break;
                case 3:
                    inimigoAtual = new InimigoBoss("Hidra de Lerna", 120, 100, 35, 300);
                    break;
                case 4:
                    inimigoAtual = new InimigoBoss("CRONOS, O TITÃ DO TEMPO", 110, 90, 45, 500);
                    break;
            }
            System.out.println("BOSS: " + inimigoAtual.getNome() + " surge das sombras!");
        }
        System.out.println("Vida: " + inimigoAtual.getVidaAtual());
        System.out.println("Dano Base: " + inimigoAtual.getDanoBase());

        executarTurnoCombate();
    }

    private void executarTurnoCombate() throws InventarioCheioException {
        while (player.getVidaAtual() > 0 && inimigoAtual.getVidaAtual() > 0) {
            System.out.println("\n--- SEU TURNO ---");
            System.out.println("[1] Atacar   [2] Defender (Recuperar Escudo)");
            System.out.print("Escolha: ");
            int acao = scanner.nextInt();
            scanner.nextLine();

            if (acao == 1) {
                player.atacar(inimigoAtual);
            } else {
                player.defender();
            }

            if (inimigoAtual.getVidaAtual() > 0) {
                System.out.println("\n--- TURNO DO INIMIGO ---");
                inimigoAtual.executarTurno(player);
            }
        }

        if (player.getVidaAtual() <= 0) {
            System.out.println("\n Você foi derrotado e sua alma foi levada ao Tártaro...");
            jogoRodando = false;
        } else {
            System.out.println("\n Vitória! Você derrotou o " + inimigoAtual.getNome());

            int dracmasGanhas = (int) (Math.random() * 21) + 15; // de 15 a 35 dracmas
            System.out.println("💰 Você coletou " + dracmasGanhas + " Dracmas do corpo do inimigo.");
            player.adquirirItemInventario(new Moeda(dracmasGanhas));

            try {
                oferecerEquipamentoOlimpico();
            } catch (InventarioCheioException e) {
                System.out.println("Bolsa cheia, o item extra caiu no chão.");
            }

            if (monstrosDerrotadosNaFase < 4) {
                monstrosDerrotadosNaFase++;
                System.out.println("Progresso da Fase " + faseAtual + ": " + monstrosDerrotadosNaFase + "/4 monstros comuns derrotados.");
            } else {
                // Era o Boss da fase!
                System.out.println("\n PARABÉNS! Você derrotou o Chefe da Fase " + faseAtual + "!");

                if (faseAtual == 4) {
                    System.out.println("CRONOS FOI APRISIONADO NOVAMENTE! VOCÊ SALVOU O OLIMPO! ");
                    jogoRodando = false;
                    return;
                }

                player.setVidaAtual(player.getVidaMaxima());
                player.setEscudoAtual(player.getEscudoMax());
                System.out.println(" Os deuses purificaram suas feridas! Vida e Escudo totalmente restaurados! ");

                abrirLojaDoMercador();

                faseAtual++;
                monstrosDerrotadosNaFase = 0;
                System.out.println("\n🔮 Uma nova passagem se abre... Você entrou na FASE " + faseAtual + "!");
            }
        }
    }

    private void abrirLojaDoMercador() {
        System.out.println("\n=======================================================================");
        System.out.println(" MERCADOR " + mercador.getNome().toUpperCase() + " SE APROXIMA VOANDO");
        System.out.println("=======================================================================");
        System.out.println("\"Saudações, Semideus! Deseja negociar provisões do Olimpo?\"");

        for (Item i : mercador.getEstoque()) {
            if (i.getPreco() <= 0) {
                i.setPreco(calcularPrecoItem(i));
            }
        }

        boolean naLoja = true;
        while (naLoja) {
            System.out.println("\n=======================================================================");
            System.out.println(" Seu Saldo: " + player.obterSaldoDracmas() + " Dracmas |  Bolsa: " + player.getInventario().size() + "/20");
            System.out.println("=======================================================================");
            System.out.println("[1] Ver Itens à Venda (Comprar)");
            System.out.println("[2] Oferecer Itens da sua Bolsa (Vender)");
            System.out.println("[3] Sair da Loja e seguir viagem");
            System.out.println("=======================================================================");
            System.out.print("O que deseja fazer, Herói? ");
            int menuLoja = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (menuLoja) {
                case 1:
                    // --- SUBMENU DE COMPRA ---
                    mercador.exibirEstoque();
                    System.out.println("[" + mercador.getEstoque().size() + "] Voltar ao menu anterior");
                    System.out.println("----------------------------------------");
                    System.out.print("Escolha o número do item para COMPRAR: ");
                    int opcaoCompra = scanner.nextInt();
                    scanner.nextLine();

                    if (opcaoCompra == mercador.getEstoque().size()) {
                        break; // Volta pro menu da loja
                    }

                    // 🔥 Protegendo a chamada com try-catch para capturar a nova exceção
                    try {
                        mercador.comprarItem(player, opcaoCompra);
                    } catch (SaldoInsuficienteException e) {
                        // Mostra a mensagem amigável que definimos lá no Mercador
                        System.out.println("\n❌ [NEGÓCIO RECUSADO] -> " + e.getMessage());
                    }
                    break;

                case 2:
                    ArrayList<Item> itensParaVender = new ArrayList<>();
                    for (Item item : player.getInventario()) {
                        if (!(item instanceof Moeda)) {
                            itensParaVender.add(item);
                        }
                    }

                    if (itensParaVender.isEmpty()) {
                        System.out.println("\n Você não tem equipamentos ou poções na bolsa para vender!");
                        break;
                    }

                    System.out.println("\n💰 --- SEUS ITENS PARA VENDA (O Mercador paga metade do preço) ---");
                    for (int i = 0; i < itensParaVender.size(); i++) {
                        Item itemBolsa = itensParaVender.get(i);
                        // Define preço se não tiver
                        if (itemBolsa.getPreco() <= 0) { itemBolsa.setPreco(calcularPrecoItem(itemBolsa)); }

                        int valorVenda = itemBolsa.getPreco() / 2;
                        System.out.println("   [" + i + "] " + itemBolsa.getNome() + " -> Hermes paga: " + valorVenda + " Dracmas");
                    }
                    System.out.println("   [" + itensParaVender.size() + "] Voltar ao menu anterior");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.print("Escolha o número do item que deseja VENDER: ");
                    int opcaoVenda = scanner.nextInt();
                    scanner.nextLine();

                    if (opcaoVenda == itensParaVender.size()) {
                        break; // Volta pro menu da loja
                    }

                    if (opcaoVenda >= 0 && opcaoVenda < itensParaVender.size()) {
                        Item itemEscolhido = itensParaVender.get(opcaoVenda);
                        mercador.venderItem(player, itemEscolhido);
                    } else {
                        System.out.println(" Opção inválida!");
                    }
                    break;

                case 3:
                    System.out.println("\n\"Que os ventos do Olimpo guiem seus passos!\" - Hermes desaparece em um rastro dourado.");
                    naLoja = false;
                    break;

                default:
                    System.out.println("❌ Opção inválida!");
                    break;
            }
        }
    }

    private int calcularPrecoItem(Item item) {
        switch (item.getRaridade()) {
            case COMUM: return 30;
            case RARO: return 60;
            case EPICO: return 120;
            case LENDARIO: return 250;
            default: return 50;
        }
    }

    private Item clonarItemParaVenda(Item original) {
        if (original instanceof ItemConsumivel) {
            ItemConsumivel c = (ItemConsumivel) original;
            return new ItemConsumivel(c.getNome(), c.getRaridade(), c.getValorRecuperacao(), c.getTipoRecuperacao());
        } else if (original instanceof ItemAtaque) {
            ItemAtaque a = (ItemAtaque) original;
            return new ItemAtaque(a.getNome(), a.getRaridade(), a.getDanoBonus());
        } else {
            ItemProtecao p = (ItemProtecao) original;
            return new ItemProtecao(p.getNome(), p.getRaridade(), p.getValorDefesa());
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
            System.out.println("Você não quis o item e o deixou apodrecer no chão da Grécia.");
        }
        System.out.println("=======================================================================");
    }

    private void oferecerEquipamentoOlimpico() throws InventarioCheioException {

       // if (Math.random() > 0.50) {
       //     System.out.println("\nO monstro não carregava nenhum equipamento extra...");
       //     return;
        // }

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

        if (chanceCategoria < 0.50) {
            itemGerado = consumiveis.get((int) (Math.random() * consumiveis.size()));
        } else if (chanceCategoria < 0.80) {
            itemGerado = armas.get((int) (Math.random() * armas.size()));
        } else {
            itemGerado = protecoes.get((int) (Math.random() * protecoes.size()));
        }

        // Seta um preço padrão aleatório pro item gerado (caso o jogador queira vender pro Hermes depois!)
        if (itemGerado.getPreco() <= 0) {
            itemGerado.setPreco(calcularPrecoItem(itemGerado));
        }
        oferecerItemAoJogador(itemGerado);
    }

    private void gerenciarInventario() {
        System.out.println("\n=======================================================================");
        System.out.println("                          SEU INVENTÁRIO:                                ");
        System.out.println("=======================================================================  ");

        // 💰 MOSTRAR O SALDO DE DRACMAS NO TOPO
        Moeda saquinho = player.buscarSaquinhoMoedas();
        int saldoDracmas = (saquinho != null) ? saquinho.getQuantidade() : 0;
        System.out.println("💰 Saldo Atual: " + saldoDracmas + " Dracmas");
        System.out.println("=======================================================================");

        if (player.getInventario().isEmpty() || (player.getInventario().size() == 1 && saquinho != null)) {
            System.out.println("Sua bolsa não possui equipamentos ou poções.");
            System.out.println("=======================================================================");
            return;
        }

        // LISTAR APENAS ITENS QUE NÃO SEJAM MOEDA
        // Para manter os índices batendo certinho com a escolha do usuário, criamos uma lista auxiliar:
        ArrayList<Item> itensExibidos = new ArrayList<>();
        for (Item item : player.getInventario()) {
            if (!(item instanceof Moeda)) {
                itensExibidos.add(item);
            }
        }

        for (int i = 0; i < itensExibidos.size(); i++) {
            Item item = itensExibidos.get(i);
            String statusExtra = "";

            // Checa se o item atual é uma Arma
            if (item instanceof ItemAtaque) {
                ItemAtaque arma = (ItemAtaque) item;
                statusExtra = " [Dano Bônus: +" + arma.getDanoBonus() + "]";
            }
            // Checa se o item atual é uma Armadura
            else if (item instanceof ItemProtecao) {
                ItemProtecao armadura = (ItemProtecao) item;
                statusExtra = " [Proteção: " + armadura.getValorDefesa() + "%]";
            }
            // Checa se o item atual é um Consumível (Poção)
            else if (item instanceof ItemConsumivel) {
                ItemConsumivel consumivel = (ItemConsumivel) item;
                statusExtra = " [Regenera: +" + consumivel.getValorRecuperacao() + "]";
            }

            // Imprime o índice, o nome e o status extra que descobrimos acima
            System.out.println("[" + i + "] " + item.getNome() + statusExtra);
        }

        System.out.println("[" + itensExibidos.size() + "] Voltar ao menu anterior");
        System.out.println("=======================================================================");

        System.out.print("Escolha o número do item para interagir: ");
        int indiceItem = scanner.nextInt();
        scanner.nextLine();

        if (indiceItem == itensExibidos.size()) {
            System.out.println("Fechando inventário...");
            return;
        }

        if (indiceItem < 0 || indiceItem >= itensExibidos.size()) {
            System.out.println("Esse item não existe na sua bolsa.");
            return;
        }

        // Agora pegamos o item correto da lista filtrada
        Item itemEscolhido = itensExibidos.get(indiceItem);


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
        System.out.println("\nObrigado por jogar Bom De Guerra!");
    }

}
