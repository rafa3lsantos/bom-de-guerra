package itens;

import personagens.jogador.Jogador;
import excecoes.InventarioCheioException;
import excecoes.SaldoInsuficienteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe do comerciante do jogo. Cuida de guardar os itens da lojinha,
 * mostrar o estoque na tela e processar os negócios de compra e venda com o jogador.
 * * @author Rafael
 */
public class Mercador {
    private String nome;
    private List<Item> estoque;

    /**
     * Construtor para criar um Mercador informando o nome dele.
     * O estoque começa totalmente vazio.
     * * @param nome O nome do comerciante.
     */
    public Mercador (String nome) {
        this.nome = nome;
        this.estoque = new ArrayList<>();
    }

    /**
     * Coloca um novo item à venda no estoque do mercador.
     * * @param item O item que será adicionado à loja.
     */
    public void addEstoque(Item item) {
        this.estoque.add(item);
    }

    /**
     * Lista no terminal todos os itens que estão na loja junto com os seus preços.
     */
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

    /**
     * Cuida da compra de um item da loja pelo jogador.
     * Checa se o índice é válido, se o herói tem dinheiro e se tem espaço na bolsa.
     * * @param jogador    O herói que está comprando.
     * @param indiceItem O número do item escolhido na lista do estoque.
     * @throws SaldoInsuficienteException Caso o jogador não tenha Dracmas suficientes para o item.
     */
    public void comprarItem(Jogador jogador, int indiceItem) throws SaldoInsuficienteException {
        if(indiceItem < 0 || indiceItem >= this.estoque.size()) {
            System.out.println("\n❌ [LOJA] -> Opção inválida! Escolha um índice válido do estoque.");
            return;
        }

        Item itemDesejado = this.estoque.get(indiceItem);

        if(jogador.obterSaldoDracmas() < itemDesejado.getPreco()) {
            throw new SaldoInsuficienteException("Você precisa de " + itemDesejado.getPreco() + " Dracmas para comprar '" + itemDesejado.getNome() + "'.");
        }

        try {
            jogador.adquirirItemInventario(itemDesejado);

            jogador.pagarMercador(itemDesejado.getPreco());
            this.estoque.remove(itemDesejado); // Remove do estoque do mercador

            System.out.println("🪙 Transação concluída com sucesso!");

        } catch (InventarioCheioException e) {
            // Captura o erro controlado. Nada foi alterado no saldo ou no estoque
            System.out.println("\n⚠️ [LOJA] -> Compra cancelada pelo sistema de inventário:");
            System.out.println("   " + e.getMessage());
        }
    }

    /**
     * Cuida da venda de um item do jogador para a loja.
     * O mercador paga metade do preço original do item e o coloca no seu estoque.
     * * @param jogador O herói que está vendendo o item.
     * @param itemParaVender O item da bolsa que será vendido.
     */
    public void venderItem(Jogador jogador, Item itemParaVender) {
        if(!jogador.getInventario().contains(itemParaVender)) {
            System.out.println("\n❌ [LOJA] -> Você não pode vender um item que não está na sua bolsa.");
            return;
        }

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
            // Linha de segurança caso algo místico aconteça kkk
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