package br.edu.ifpb.apisinan.entities.enums;

public enum TipoNotificacao {

    INDIVIDUAL("2");

    private final String codigo;

    TipoNotificacao(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
