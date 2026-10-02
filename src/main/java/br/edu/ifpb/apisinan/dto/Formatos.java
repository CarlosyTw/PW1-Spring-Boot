package br.edu.ifpb.apisinan.dto;

public final class Formatos {

    public static final String UF =
            "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO";
    public static final String IBGE = "\\d{7}";
    public static final String CEP = "\\d{5}-?\\d{3}";
    public static final String CARTAO_SUS = "\\d{15}";
    public static final String TELEFONE = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}";
    public static final String CID10 = "[A-Za-z]\\d{2}(\\.?\\d{1,2})?";

    private Formatos() { }
}

