package excecoes;

/**
 * Exceção disparada para indicar que o inventário do jogador atingiu
 * o limite máximo de capacidade permitido (20 slots).
 * * @author Rafael
 */
public class InventarioCheioException extends Exception {

    /**
     * Cria a exceção definindo o texto explicativo sobre o erro do inventário.
     * * @param mensagem O texto detalhado descrevendo o motivo do travamento.
     */
    public InventarioCheioException(String mensagem) {
        super(mensagem);
    }
}