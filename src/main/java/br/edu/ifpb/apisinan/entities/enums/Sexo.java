package br.edu.ifpb.apisinan.entities.enums;

public enum Sexo {

    MASCULINO("M"),
    FEMININO("F"),
    IGNORADO("I");

    private final String codigo;

    Sexo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
