package br.ada.ecommerce.application.usecases.exception;

public class IllegalOrderStateException extends RuntimeException {

    public IllegalOrderStateException(String message) {
        super(message);
    }

}
