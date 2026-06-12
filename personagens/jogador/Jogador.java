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
        this.armaEquipada = armaEquipada;
        this.armaduraEquipada = armaduraEquipada;
        this.inventario = inventario;
        this.escudoMax = 50;
        this.forcaBase = 10;
        this.inventario = new ArrayList<>();
    }

    public void atacar(Personagem alvo){
        int danoTotal = this.forcaBase;
        if(this.armaEquipada != null) {
            //fazer
        }
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
