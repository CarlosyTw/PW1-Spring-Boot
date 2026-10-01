package br.edu.ifpb.apisinan.entities.enums;

public enum CriterioConfirmacao {

    LABORATORIAL("1"),
    CLINICO_EPIDEMIOLOGICO("2");

    private final String codigo;

    CriterioConfirmacao(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}

