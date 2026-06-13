package personagens.jogador;

import enums.TipoGenero;
import itens.Item;
import itens.ItemAtaque;
import itens.ItemProtecao;
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
}
