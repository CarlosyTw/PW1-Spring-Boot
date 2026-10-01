package br.edu.ifpb.apisinan.entities.enums;

public enum EvolucaoCaso {

    CURA("1"),
    OBITO_PELO_AGRAVO_NOTIFICADO("2"),
    OBITO_POR_OUTRAS_CAUSAS("3"),
    IGNORADO("9");

    private final String codigo;

    EvolucaoCaso(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
