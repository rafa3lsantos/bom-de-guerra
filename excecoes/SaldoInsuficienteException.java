package excecoes;

/**
 * Exceção disparada quando o jogador tenta realizar uma compra no mercador
 * mas não possui a quantidade de Dracmas exigida pelo item.
 * @author Rafael
 */
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}