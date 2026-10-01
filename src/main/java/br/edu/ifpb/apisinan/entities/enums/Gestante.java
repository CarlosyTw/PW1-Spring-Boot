package br.edu.ifpb.apisinan.entities.enums;

public enum Gestante {

    PRIMEIRO_TRIMESTRE("1"),
    SEGUNDO_TRIMESTRE("2"),
    TERCEIRO_TRIMESTRE("3"),
    IDADE_GESTACIONAL_IGNORADA("4"),
    NAO("5"),
    NAO_SE_APLICA("6"),
    IGNORADO("9");

    private final String codigo;

    Gestante(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
