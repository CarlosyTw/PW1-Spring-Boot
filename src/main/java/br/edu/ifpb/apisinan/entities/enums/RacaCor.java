package br.edu.ifpb.apisinan.entities.enums;

public enum RacaCor {

    BRANCA("1"),
    PRETA("2"),
    AMARELA("3"),
    PARDA("4"),
    INDIGENA("5"),
    IGNORADO("9");

    private final String codigo;

    RacaCor(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
