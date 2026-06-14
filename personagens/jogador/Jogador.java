package personagens.jogador;

import enums.TipoGenero;
import itens.*;
import jogo.InterfaceUsuario;
import personagens.Personagem;
import personagens.inimigos.Inimigo;

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

    public Jogador(String nome, int vidaMaxima, int vidaAtual, int escudoAtual, ItemAtaque armaAtual, ItemProtecao armaduraAtual, List<Item> inventario, int escudoMax) {
        super(nome, 100, vidaAtual);
        this.escudoAtual = escudoAtual;
        this.escudoMax = 50;
        this.armaEquipada = armaEquipada;
        this.armaduraEquipada = armaduraEquipada;
        this.inventario = inventario;
        this.forcaBase = 10;
        this.inventario = new ArrayList<>();
    }

    @Override
    public void atacar(Personagem alvo){
        int danoTotal = this.forcaBase;
        if(this.armaEquipada != null) {
            danoTotal += this.armaEquipada.getDanoBonus();
            //System.out.println(getNome() + " atacou com " + this.armaEquipada.getNome() + "!");
            jogo.InterfaceUsuario.atacou(true, getNome(), getArmaEquipada());
        } else {
            //System.out.println(getNome() + " atacou sem armas");
            jogo.InterfaceUsuario.atacou(false, getNome(), getArmaEquipada());
        }
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

    public void receberDano(int danoBruto) {
        int percentualDefesa = 0;

        if(this.armaduraEquipada != null) {
            percentualDefesa = this.armaduraEquipada.getValorDefesa();
        }

        double reducao = danoBruto - (percentualDefesa / 100);
        int danoFinal = danoBruto - (int)reducao;

        jogo.InterfaceUsuario.exibirRelatorioDano(danoBruto, percentualDefesa, (int)reducao, danoFinal);

        if(escudoAtual > 0) {
            if(danoFinal <= escudoAtual) {
                this.escudoAtual -= danoFinal;
                danoFinal = 0;
                System.out.println("🛡️ Seu escudo absorveu todo o impacto!");
            } else {
                danoFinal -= this.escudoAtual;
                this.escudoAtual = 0;
                System.out.println("💥 Seu escudo foi QUEBRADO! O restante do dano passou.");
            }
        }

        if(danoFinal > 0) {
            int novaVida = getVidaAtual() - danoFinal;
            setVidaAtual(novaVida);
        }

        jogo.InterfaceUsuario.exibirStatusPersonagem(getNome(), getVidaAtual(), getVidaMaxima(), this.escudoAtual, this.escudoMax);
    }

    public TipoGenero getGenero() {
        return genero;
    }

    public void setGenero(TipoGenero genero) {
        this.genero = genero;
    }

    public int getEscudoMax() {
        return escudoMax;
    }

    public void setEscudoMax(int escudoMax) {
        this.escudoMax = escudoMax;
    }

    public int getEscudoAtual() {
        return escudoAtual;
    }

    public void setEscudoAtual(int escudoAtual) {
        this.escudoAtual = escudoAtual;
    }

    public ItemAtaque getArmaEquipada() {
        return armaEquipada;
    }

    public void setArmaEquipada(ItemAtaque armaEquipada) {
        this.armaEquipada = Jogador.this.armaEquipada;
    }

    public ItemProtecao getArmaduraEquipada() {
        return armaduraEquipada;
    }

    public void setArmaduraEquipada(ItemProtecao armaduraEquipada) {
        this.armaduraEquipada = Jogador.this.armaduraEquipada;
    }

    public List<Item> getInventario() {
        return inventario;
    }

    public void setInventario(List<Item> inventario) {
        this.inventario = inventario;
    }


















































































































































































































    public boolean adquirirItemInventario (Item item) {
    //ver se fica melhor guardar todas em um saquinho que ocupa um espaço de 20 no inventario
    // ou guardar em N saquinhos com N moedas dentro cada um e que ocupe N espacos no inventario

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
            return false;
        }
    }

    public Moeda buscarSaquinhoMoedas () {
        for (Item item: this.inventario) {
            if (item instanceof Moeda) {
                return (Moeda) item;
            }
        }
        return null;
    }

    public boolean removerItemInventario (Item item) {

        if (this.inventario.contains(item)) {
            this.inventario.remove(item);
            InterfaceUsuario.exibirItemRemovido(item.getNome(), this.inventario.size(), 20);
            return true;
        }

        InterfaceUsuario.exibirErroRemocao(item.getNome());
        return false;
    }


    public boolean equiparItem (Item itemGenerico) { //sejam eles: item de ataque ou protecao

        if (!this.inventario.contains(itemGenerico)) {
            InterfaceUsuario.exibirErroItemNaoPossuido(itemGenerico.getNome());
            return false;
        }

        if (itemGenerico instanceof ItemAtaque) {
            ItemAtaque novaArma = (ItemAtaque) itemGenerico;
            this.inventario.remove(novaArma); //remove da bolsa, p ir pra mao
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
        }
        else {
            InterfaceUsuario.exibirErroItemNaoConsumivel(itemGenerico.getNome());
            return false;
        }
    }




}
