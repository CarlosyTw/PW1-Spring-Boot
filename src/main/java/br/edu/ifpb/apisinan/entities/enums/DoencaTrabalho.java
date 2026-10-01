package br.edu.ifpb.apisinan.entities.enums;

public enum DoencaTrabalho {

    SIM("1"),
    NAO("2"),
    IGNORADO("9");

    private final String codigo;

    DoencaTrabalho(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
