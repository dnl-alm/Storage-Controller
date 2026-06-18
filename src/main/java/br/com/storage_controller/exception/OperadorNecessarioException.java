package br.com.storage_controller.exception;

public class OperadorNecessarioException extends RuntimeException {

    public OperadorNecessarioException() {
        super("Acesso negado: apenas Operators podem executar esta operação.");
    }

    public OperadorNecessarioException(String message) {
        super(message);
    }
}