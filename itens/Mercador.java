package itens;

import personagens.jogador.Jogador;
import excecoes.InventarioCheioException;
import java.util.ArrayList;
import java.util.List;

public class Mercador {
    private String nome;
    private List<Item> estoque;

    public Mercador (String nome) {
        this.nome = nome;
        this.estoque = new ArrayList<>();
    }

    public void addEstoque(Item item) {
        this.estoque.add(item);
    }

    public void exibirEstoque() {
        System.out.println("\n🏪 --- LOJA DE " + this.nome.toUpperCase() + " ---");
        if (estoque.isEmpty()) {
            System.out.println("   [O estoque está completamente vazio!]");
            return;
        }

        for (int i = 0; i < estoque.size(); i++) {
            Item item = estoque.get(i);
            System.out.println("   [" + i + "] " + item.getNome() + " - Preço: " + item.getPreco() + " Dracmas");
        }
        System.out.println("----------------------------------------");
    }

    public void comprarItem(Jogador jogador, int indiceItem) {
        if(indiceItem < 0 || indiceItem >= this.estoque.size()) {
            System.out.println("\n❌ [LOJA] -> Opção inválida! Escolha um índice válido do estoque.");
            return;
        }

        Item itemDesejado = this.estoque.get(indiceItem);

        if(jogador.obterSaldoDracmas() < itemDesejado.getPreco()) {
            System.out.println("\n❌ [LOJA] -> Dracmas insuficientes! Você precisa de " + itemDesejado.getPreco() + " Dracmas.");
            return;
        }

        try {
            jogador.adquirirItemInventario(itemDesejado);

            // Se passou da linha anterior, a bolsa tinha espaço! Prossegue com a cobrança
            jogador.pagarMercador(itemDesejado.getPreco());
            this.estoque.remove(itemDesejado); // Remove do estoque do mercador

            System.out.println("🪙 Transação concluída com sucesso!");

        } catch (InventarioCheioException e) {
            // Captura o erro controlado. Nada foi alterado no saldo ou no estoque
            System.out.println("\n⚠️ [LOJA] -> Compra cancelada pelo sistema de inventário:");
            System.out.println("   " + e.getMessage());
        }
    }

    public void venderItem(Jogador jogador, Item itemParaVender) {
        if(!jogador.getInventario().contains(itemParaVender)) {
            System.out.println("\n❌ [LOJA] -> Você não pode vender um item que não está na sua bolsa.");
            return;
        }

        // Se o item for uma moeda, não faz sentido vender dinheiro por dinheiro
        if (itemParaVender instanceof Moeda) {
            System.out.println("\n❌ [LOJA] -> Você não pode vender suas próprias Dracmas!");
            return;
        }

        int valorRevenda = itemParaVender.getPreco() / 2;

        if (valorRevenda <= 0) {
            System.out.println("\n❌ [LOJA] -> Esse item não tem valor de revenda para o mercador.");
            return;
        }

        jogador.removerItemInventario(itemParaVender);
        this.estoque.add(itemParaVender);

        Moeda moedasGanhas = new Moeda(valorRevenda);
        try {
            // Como o jogador acabou de liberar um espaço removendo o item,
            // a moeda sempre vai entrar com segurança (acumulando ou em slot novo)
            jogador.adquirirItemInventario(moedasGanhas);
            System.out.println("💰 Você vendeu " + itemParaVender.getNome() + " por " + valorRevenda + " Dracmas!");
        } catch (InventarioCheioException e) {
            // Linha de segurança caso algo místico aconteça
            System.out.println(e.getMessage());
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Item> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<Item> estoque) {
        this.estoque = estoque;
    }
}
