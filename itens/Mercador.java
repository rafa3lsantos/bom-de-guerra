package itens;

import java.util.ArrayList;
import java.util.List;

public class Mercador {
    private String nome;
    private List<Item> estoque;

    public Mercador (String nome) {
        this.nome = nome;
        this.estoque = new ArrayList<>();
    }
}
