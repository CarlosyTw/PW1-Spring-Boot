package br.edu.ifpb.apisinan.entities.enums;

public enum ClassificacaoFinal {

    CONFIRMADO("1"),
    DESCARTADO("2");

    private final String codigo;

    ClassificacaoFinal(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
