package excecoes;
// Ao estender 'Exception', o Java passa a reconhecer esta classe como um erro oficial do sistema
public class InventarioCheioException extends Exception {

    // Construtor que recebe a mensagem personalizada de erro
    public InventarioCheioException(String mensagem) {
        // O 'super' repassa essa mensagem para a classe mãe (Exception) do Java
        super(mensagem);
    }
}
