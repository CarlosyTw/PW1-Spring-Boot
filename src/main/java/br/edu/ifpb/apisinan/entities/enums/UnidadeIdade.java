package br.edu.ifpb.apisinan.entities.enums;

public enum UnidadeIdade {

    HORA("1"),
    DIA("2"),
    MES("3"),
    ANO("4");

    private final String codigo;

    UnidadeIdade(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
