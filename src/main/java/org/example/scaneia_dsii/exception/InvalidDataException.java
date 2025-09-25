package org.example.scaneia_dsii.exception;

import java.util.Map;

public class InvalidDataException extends RuntimeException{
    private final Map<String, String> erros;

    public InvalidDataException(Map<String, String> erros) {
        super("Dados inválidos");
        this.erros = erros;
    }

    public Map<String, String> getErros() {
        return erros;
    }

}
