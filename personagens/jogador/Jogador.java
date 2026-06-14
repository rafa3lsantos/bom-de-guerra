package personagens.jogador;

import enums.TipoGenero;
import excecoes.InventarioCheioException;
import itens.*;
import jogo.InterfaceUsuario;
import personagens.Personagem;
import personagens.inimigos.Inimigo;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe que controla as ações, status e inventário do herói do jogo.
 * Cuida de equipar armas, usar poções, gerenciar dinheiro e defender golpes.
 * * @author Rafael e Rafaela
 */
public class Jogador extends Personagem {
    private TipoGenero genero;
    private int escudoMax;
    private int escudoAtual;
    private ItemAtaque armaEquipada;
    private ItemProtecao armaduraEquipada;
    private List<Item> inventario;
    private int forcaBase;

    /**
     * Construtor para criar o seu herói com todos os status iniciais.
     * O inventário começa automaticamente como uma lista vazia e limpa.
     * * @param nome             Nome do herói.
     * @param vidaMaxima       Limite máximo de pontos de vida.
     * @param vidaAtual        Vida com que o jogador vai começar.
     * @param escudoAtual      Escudo inicial do jogador.
     * @param armaEquipada     Arma que ele já começa segurando (pode ser null).
     * @param armaduraEquipada Armadura que ele já começa vestindo (pode ser null).
     * @param inventario       Lista para os itens (reiniciada internamente).
     * @param escudoMax        Limite máximo do escudo.
     */
    public Jogador(String nome, int vidaMaxima, int vidaAtual, int escudoAtual, ItemAtaque armaEquipada, ItemProtecao armaduraEquipada, List<Item> inventario, int escudoMax) {
        super(nome, 100, vidaAtual);
        this.escudoAtual = escudoAtual;
        this.escudoMax = 50;
        this.armaEquipada = armaEquipada;
        this.armaduraEquipada = armaduraEquipada;
        this.inventario = inventario;
        this.forcaBase = 10;
        this.inventario = new ArrayList<>(); // Inicializa a lista vazia
    }

    /**
     * Dá um golpe no oponente passado como alvo.
     * O dano total junta a força base do herói com o bônus da arma equipada.
     * * @param alvo O inimigo ou personagem que vai receber o ataque.
     */
    @Override
    public void atacar(Personagem alvo) {
        int danoTotal = this.forcaBase;
        if(this.armaEquipada != null) {
            danoTotal += this.armaEquipada.getDanoBonus();
            jogo.InterfaceUsuario.atacou(true, getNome(), getArmaEquipada());
        } else {
            jogo.InterfaceUsuario.atacou(false, getNome(), getArmaEquipada());
        }

        alvo.receberDano(danoTotal);
    }

    /**
     * Faz o jogador entrar em postura de guarda, recuperando uma quantidade fixa de escudo.
     */
    @Override
    public void defender() {
        int recuperacao = 10;
        this.escudoAtual += recuperacao;
        if (this.escudoAtual > this.escudoMax) {
            this.escudoAtual = this.escudoMax;
        }
        System.out.println(getNome() + " adotou uma postura defensiva e recuperou " + recuperacao + " de escudo!");
    }

    /**
     * Calcula o dano recebido aplicando a redução da armadura equipada.
     * O escudo absorve o impacto primeiro e, se quebrar, o resto vai direto para a vida.
     * * @param danoBruto O valor do dano do monstro antes de qualquer defesa do jogador.
     */
    @Override
    public void receberDano(int danoBruto) {
        int percentualDefesa = 0;

        if (this.armaduraEquipada != null) {
            percentualDefesa = this.armaduraEquipada.getValorDefesa();
        }

        double reducao = danoBruto * (percentualDefesa / 100.0);
        int danoFinal = danoBruto - (int)reducao;

        InterfaceUsuario.exibirRelatorioDano(danoBruto, percentualDefesa, (int) reducao, danoFinal);

        if (escudoAtual > 0) {
            if (danoFinal <= escudoAtual) {
                this.escudoAtual -= danoFinal;
                danoFinal = 0;
                System.out.println("🛡️ Seu escudo absorveu todo o impacto!");
            } else {
                danoFinal -= this.escudoAtual;
                this.escudoAtual = 0;
                System.out.println("💥 Seu escudo foi QUEBRADO! O restante do dano passou.");
            }
        }

        if (danoFinal > 0) {
            int novaVida = getVidaAtual() - danoFinal;
            setVidaAtual(novaVida);
        }

        InterfaceUsuario.exibirStatusPersonagem(getNome(), getVidaAtual(), getVidaMaxima(), this.escudoAtual, this.escudoMax);
    }

    /**
     * Guarda um novo item na bolsa do jogador. Se for dinheiro, junta de forma inteligente
     * no mesmo saquinho, sem gastar espaços extras do inventário.
     * * @param item O item ou moeda que o jogador acabou de achar.
     * @return true se deu tudo certo ao guardar ou acumular o item.
     * @throws InventarioCheioException Se a bolsa já estiver com o limite máximo de 20 slots ocupados.
     */
    public boolean adquirirItemInventario(Item item) throws InventarioCheioException {
        if (item instanceof Moeda) {
            Moeda moedaNova = (Moeda) item;
            Moeda saquinhoExistente = buscarSaquinhoMoedas();

            if (saquinhoExistente != null) {
                saquinhoExistente.adicionarQuantidade(moedaNova.getQuantidade());
                InterfaceUsuario.exibirItemAdicionado(moedaNova.getQuantidade() + " Dracmas", this.inventario.size(), 20);
                return true;
            }
        }

        if (this.inventario.size() < 20) {
            this.inventario.add(item);
            InterfaceUsuario.exibirItemAdicionado(item.getNome(), this.inventario.size(), 20);
            return true;
        } else {
            InterfaceUsuario.exibirInventarioCheio(item.getNome());
            throw new InventarioCheioException("O inventário atingiu o limite máximo de 20 itens.");
        }
    }

    public Moeda buscarSaquinhoMoedas() {
        for (Item item : this.inventario) {
            if (item instanceof Moeda) {
                return (Moeda) item;
            }
        }
        return null;
    }

    /**
     * Joga fora um item do inventário. É usado nas vendas do Mercador.
     * * @param item O item que vai sumir da bolsa.
     * @return true se o jogador realmente tinha esse item e ele foi removido.
     */
    public boolean removerItemInventario(Item item) {
        if (this.inventario.contains(item)) {
            this.inventario.remove(item);
            InterfaceUsuario.exibirItemRemovido(item.getNome(), this.inventario.size(), 20);
            return true;
        }

        InterfaceUsuario.exibirErroRemocao(item.getNome());
        return false;
    }

    /**
     * Tira uma arma ou armadura da bolsa e coloca nos slots de equipamento ativo.
     * Se você já tinha algo equipado, o item antigo volta em segurança para o inventário.
     * * @param itemGenerico O item clicado/escolhido dentro da bolsa.
     * @return true se o item puder ser equipado com sucesso.
     */
    public boolean equiparItem(Item itemGenerico) {
        if (!this.inventario.contains(itemGenerico)) {
            InterfaceUsuario.exibirErroItemNaoPossuido(itemGenerico.getNome());
            return false;
        }

        if (itemGenerico instanceof ItemAtaque) {
            ItemAtaque novaArma = (ItemAtaque) itemGenerico;
            this.inventario.remove(novaArma);
            if(this.armaEquipada != null) {
                this.inventario.add(this.armaEquipada);
                InterfaceUsuario.exibirArmaGuardada(this.armaEquipada.getNome());
            }
            this.armaEquipada = novaArma;
            InterfaceUsuario.exibirArmaEquipada(this.armaEquipada.getNome(), this.armaEquipada.getDanoBonus());
            return true;
        }

        else if (itemGenerico instanceof ItemProtecao) {
            ItemProtecao novaArmadura = (ItemProtecao) itemGenerico;
            this.inventario.remove(novaArmadura);
            if (this.armaduraEquipada != null) {
                this.inventario.add(this.armaduraEquipada);
                InterfaceUsuario.exibirArmaduraGuardada(this.armaduraEquipada.getNome());
            }
            this.armaduraEquipada = novaArmadura;
            InterfaceUsuario.exibirArmaduraEquipada(this.armaduraEquipada.getNome(), this.armaduraEquipada.getValorDefesa());
            return true;
        }

        else {
            InterfaceUsuario.exibirAvisoItemNaoEquipavel(itemGenerico.getNome());
            return false;
        }
    }

    /**
     * Consome uma poção da bolsa. Recupera vida ou escudo dependendo do tipo da poção
     * e joga o frasco vazio fora (deleta o item da lista) depois do uso.
     * * @param itemGenerico A poção selecionada no inventário.
     * @return true se o item foi bebido/usado corretamente.
     */
    public boolean usarItemConsumivel (Item itemGenerico) {
        if (!this.inventario.contains(itemGenerico)) {
            InterfaceUsuario.exibirErroItemNaoPossuido(itemGenerico.getNome());
            return false;
        }

        if (itemGenerico instanceof ItemConsumivel) {
            ItemConsumivel pocao = (ItemConsumivel) itemGenerico;
            int cura = pocao.getValorRecuperacao();

            switch (pocao.getTipoRecuperacao()){
                case CURA_VIDA:
                    int vidaAntes = getVidaAtual();
                    setVidaAtual(getVidaAtual() + cura);
                    int ganhoRealVida = getVidaAtual() - vidaAntes;
                    InterfaceUsuario.exibirCuraVida(pocao.getNome(), ganhoRealVida, getVidaAtual(), getVidaMaxima());
                    break;

                case CURA_ESCUDO:
                    int escudoAntes = getEscudoAtual();
                    setEscudoAtual(getEscudoAtual() + cura);
                    if (this.escudoAtual > getEscudoMax()) {
                        this.escudoAtual = getEscudoMax();
                    }
                    int ganhoRealEscudo = this.escudoAtual - escudoAntes;
                    InterfaceUsuario.exibirCuraEscudo(pocao.getNome(), ganhoRealEscudo, this.escudoAtual, this.escudoMax);
                    break;
            }

            this.inventario.remove(pocao);
            return true;
        } else {
            InterfaceUsuario.exibirErroItemNaoConsumivel(itemGenerico.getNome());
            return false;
        }
    }

    /**
     * Diz quantas moedas (Dracmas) o jogador tem guardadas ao todo.
     * * @return O saldo total em dinheiro, ou 0 se a bolsa estiver sem nenhuma moeda.
     */
    public int obterSaldoDracmas() {
        Moeda saquinho = buscarSaquinhoMoedas();
        if (saquinho != null) {
            return saquinho.getQuantidade();
        }
        return 0;
    }

    /**
     * Gasta as moedas da bolsa para pagar uma compra feita com o Mercador.
     * Se o dinheiro zerar, remove o item da lista para não gastar espaço de bobeira.
     * * @param custo Valor cobrado pelo item na loja.
     */
    public void pagarMercador(int custo) {
        Moeda saquinho = buscarSaquinhoMoedas();
        if (saquinho != null) {
            saquinho.removerQuantidade(custo);
            if (saquinho.getQuantidade() <= 0) {
                this.inventario.remove(saquinho);
            }
        }
    }

    public TipoGenero getGenero() { return genero; }
    public void setGenero(TipoGenero genero) { this.genero = genero; }

    public int getEscudoMax() { return escudoMax; }
    public void setEscudoMax(int escudoMax) { this.escudoMax = escudoMax; }

    public int getEscudoAtual() { return escudoAtual; }
    public void setEscudoAtual(int escudoAtual) { this.escudoAtual = escudoAtual; }

    public ItemAtaque getArmaEquipada() { return armaEquipada; }
    public void setArmaEquipada(ItemAtaque armaEquipada) { this.armaEquipada = armaEquipada; }

    public ItemProtecao getArmaduraEquipada() { return armaduraEquipada; }
    public void setArmaduraEquipada(ItemProtecao armaduraEquipada) { this.armaduraEquipada = armaduraEquipada; }

    public List<Item> getInventario() { return inventario; }
    public void setInventario(List<Item> inventario) { this.inventario = inventario; }
}