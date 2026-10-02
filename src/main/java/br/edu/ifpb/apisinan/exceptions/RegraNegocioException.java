package br.edu.ifpb.apisinan.exceptions;

import java.util.Map;

/** Violação de regra condicional da ficha (RN02, RN03 etc.). Guarda campo -> mensagem. */
public class RegraNegocioException extends RuntimeException {

    private final Map<String, String> erros;

    public RegraNegocioException(Map<String, String> erros) {
        super("Dados inválidos");
        this.erros = erros;
    }

    public Map<String, String> getErros() {
        return erros;
    }
}
