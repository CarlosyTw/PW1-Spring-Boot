package br.edu.ifpb.apisinan.entities.enums;

public enum Zona {

    URBANA("1"),
    RURAL("2"),
    PERIURBANA("3"),
    IGNORADO("9");

    private final String codigo;

    Zona(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
