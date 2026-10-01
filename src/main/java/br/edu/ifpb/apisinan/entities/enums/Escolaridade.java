package br.edu.ifpb.apisinan.entities.enums;

public enum Escolaridade {

    ANALFABETO("0"),
    EF_1A_4A_INCOMPLETA("1"),
    EF_4A_COMPLETA("2"),
    EF_5A_8A_INCOMPLETA("3"),
    EF_COMPLETO("4"),
    EM_INCOMPLETO("5"),
    EM_COMPLETO("6"),
    SUPERIOR_INCOMPLETO("7"),
    SUPERIOR_COMPLETO("8"),
    IGNORADO("9"),
    NAO_SE_APLICA("10");

    private final String codigo;

    Escolaridade(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
