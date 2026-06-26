package jogo;

import java.io.Serializable;


public class MarcaCombo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int danoBonus;

    public MarcaCombo(int danoBonus) {
        this.danoBonus = danoBonus;
    }

    public int getDanoBonus() {
        return danoBonus;
    }
}