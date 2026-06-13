package interfaces;

import itens.Item;
import personagens.Personagem;

public interface Combatente {
    public void atacar(Personagem alvo);
    public void defender();
}
