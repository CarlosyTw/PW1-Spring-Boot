package br.edu.ifpb.apisinan.entities.enums;

public enum CasoAutoctone {

    SIM("1"),
    NAO("2"),
    INDETERMINADO("3");

    private final String codigo;

    CasoAutoctone(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
