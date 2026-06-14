package personagens.jogador;

import enums.TipoGenero;
import excecoes.InventarioCheioException;
import itens.*;
import jogo.InterfaceUsuario;
import personagens.Personagem;

import java.util.List;
import java.util.ArrayList;

public class Jogador extends Personagem {
    private TipoGenero genero;
    private int escudoMax;
    private int escudoAtual;
    private ItemAtaque armaEquipada;
    private ItemProtecao armaduraEquipada;
    private List<Item> inventario;
    private int forcaBase;

    public Jogador(String nome, int vidaMaxima, int vidaAtual, int escudoAtual, ItemAtaque armaEquipada, ItemProtecao armaduraEquipada, List<Item> inventario, int escudoMax) {
        super(nome, 100, vidaAtual);
        this.escudoAtual = escudoAtual;
        this.escudoMax = 50;
        this.armaEquipada = armaEquipada;
        this.armaduraEquipada = armaduraEquipada;
        this.forcaBase = 10;
        this.inventario = new ArrayList<>(); // Inicializa a lista vazia
    }

    @Override
    public void atacar(Personagem alvo) {
        int danoTotal = this.forcaBase;
        if (this.armaEquipada != null) {
            danoTotal += this.armaEquipada.getDanoBonus();
            InterfaceUsuario.atacou(true, getNome(), getArmaEquipada());
        } else {
            InterfaceUsuario.atacou(false, getNome(), getArmaEquipada());
        }

        // Aplica o ataque no inimigo passsado como alvo
        alvo.receberDano(danoTotal);
    }

    @Override
    public void defender() {
        int recuperacao = 10;
        this.escudoAtual += recuperacao;
        if (this.escudoAtual > this.escudoMax) {
            this.escudoAtual = this.escudoMax;
        }
        System.out.println(getNome() + " adotou uma postura defensiva e recuperou " + recuperacao + " de escudo!");
    }

    @Override
    public void receberDano(int danoBruto) {
        int percentualDefesa = 0;

        if (this.armaduraEquipada != null) {
            percentualDefesa = this.armaduraEquipada.getValorDefesa();
        }

        // CORRIGIDO: Divisão por 100.0 garante que o Java faça conta com casas decimais
        double reducao = danoBruto * (percentualDefesa / 100.0);
        int danoFinal = danoBruto - (int) reducao;

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

    // CORRIGIDO: Parênteses adicionados, parâmetro mapeado e "return" fantasma removido do throw
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

    public boolean removerItemInventario(Item item) {
        if (this.inventario.contains(item)) {
            this.inventario.remove(item);
            InterfaceUsuario.exibirItemRemovido(item.getNome(), this.inventario.size(), 20);
            return true;
        }

        InterfaceUsuario.exibirErroRemocao(item.getNome());
        return false;
    }

    public boolean equiparItem(Item itemGenerico) {
        if (!this.inventario.contains(itemGenerico)) {
            InterfaceUsuario.exibirErroItemNaoPossuido(itemGenerico.getNome());
            return false;
        }

        if (itemGenerico instanceof ItemAtaque) {
            ItemAtaque novaArma = (ItemAtaque) itemGenerico;
            this.inventario.remove(novaArma);
            if (this.armaEquipada != null) {
                this.inventario.add(this.armaEquipada);
                InterfaceUsuario.exibirArmaGuardada(this.armaEquipada.getNome());
            }
            this.armaEquipada = novaArma;
            InterfaceUsuario.exibirArmaEquipada(this.armaEquipada.getNome(), this.armaEquipada.getDanoBonus());
            return true;
        } else if (itemGenerico instanceof ItemProtecao) {
            ItemProtecao novaArmadura = (ItemProtecao) itemGenerico;
            this.inventario.remove(novaArmadura);
            if (this.armaduraEquipada != null) {
                this.inventario.add(this.armaduraEquipada);
                InterfaceUsuario.exibirArmaduraGuardada(this.armaduraEquipada.getNome());
            }
            this.armaduraEquipada = novaArmadura;
            InterfaceUsuario.exibirArmaduraEquipada(this.armaduraEquipada.getNome(), this.armaduraEquipada.getValorDefesa());
            return true;
        } else {
            InterfaceUsuario.exibirAvisoItemNaoEquipavel(itemGenerico.getNome());
            return false;
        }
    }

    public boolean usarItemConsumivel(Item itemGenerico) {
        if (!this.inventario.contains(itemGenerico)) {
            InterfaceUsuario.exibirErroItemNaoPossuido(itemGenerico.getNome());
            return false;
        }

        if (itemGenerico instanceof ItemConsumivel) {
            ItemConsumivel pocao = (ItemConsumivel) itemGenerico;
            int cura = pocao.getValorRecuperacao();

            switch (pocao.getTipoRecuperacao()) {
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

    public int obtenerSaldoDracmas() {
        Moeda saquinho = buscarSaquinhoMoedas();
        if (saquinho != null) {
            return saquinho.getQuantidade();
        }
        return 0;
    }

    public void pagarMercador(int custo) {
        Moeda saquinho = buscarSaquinhoMoedas();
        if (saquinho != null) {
            saquinho.removerQuantidade(custo);
            if (saquinho.getQuantidade() <= 0) {
                this.inventario.remove(saquinho);
            }
        }
    }

    // --- GETTERS & SETTERS CORRIGIDOS ---
    public TipoGenero getGenero() { return genero; }
    public void setGenero(TipoGenero genero) { this.genero = genero; }

    public int getEscudoMax() { return escudoMax; }
    public void setEscudoMax(int escudoMax) { this.escudoMax = escudoMax; }

    public int getEscudoAtual() { return escudoAtual; }
    public void setEscudoAtual(int escudoAtual) { this.escudoAtual = escudoAtual; }

    public ItemAtaque getArmaEquipada() { return armaEquipada; }
    public void setArmaEquipada(ItemAtaque armaEquipada) { this.armaEquipada = armaEquipada; } // Corrigido

    public ItemProtecao getArmaduraEquipada() { return armaduraEquipada; }
    public void setArmaduraEquipada(ItemProtecao armaduraEquipada) { this.armaduraEquipada = armaduraEquipada; } // Corrigido

    public List<Item> getInventario() { return inventario; }
    public void setInventario(List<Item> inventario) { this.inventario = inventario; }
}