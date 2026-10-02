package br.edu.ifpb.apisinan.regras;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.Map;

import br.edu.ifpb.apisinan.entities.Notificacao;
import br.edu.ifpb.apisinan.entities.enums.CasoAutoctone;
import br.edu.ifpb.apisinan.entities.enums.ClassificacaoFinal;
import br.edu.ifpb.apisinan.entities.enums.EvolucaoCaso;
import br.edu.ifpb.apisinan.entities.enums.Gestante;
import br.edu.ifpb.apisinan.entities.enums.Sexo;
import br.edu.ifpb.apisinan.entities.enums.UnidadeIdade;

/**
 * Regras condicionais de preenchimento da Ficha de Notificação/Conclusão do SINAN
 * (campos obrigatórios que dependem de outros campos). Classe sem dependências do Spring:
 * recebe a entidade e devolve mensagens por campo, o que a torna simples de testar.
 *
 * <ul>
 *   <li>RN02 - idade (campo 10) e gestante (campo 12);</li>
 *   <li>RN03 - residência (campos 17, 18 e 30);</li>
 *   <li>conclusão - campos 31 a 43, incluindo o local provável de infecção.</li>
 * </ul>
 *
 * As chaves do mapa devolvido são os nomes dos campos do JSON, para o front-end marcar o campo.
 */
public final class NotificacaoRegras {

    private static final int IDADE_MINIMA_GESTACAO = 7;
    private static final String BRASIL = "brasil";

    private NotificacaoRegras() { }

    /**
     * Completa os campos que o próprio SINAN preenche automaticamente. Deve rodar antes de
     * {@link #validar(Notificacao)}.
     * <ul>
     *   <li>Gestante = "Não se aplica" quando o sexo não é feminino ou a idade é menor que 7 anos;</li>
     *   <li>caso confirmado e autóctone: local provável de infecção = dados de residência.</li>
     * </ul>
     */
    public static void normalizar(Notificacao n) {
        if (n.getNumero() != null) {
            n.setNumero(n.getNumero().trim());
        }

        if (n.getSexo() != Sexo.FEMININO) {
            n.setGestante(Gestante.NAO_SE_APLICA);
        } else {
            Integer anos = idadeEmAnos(n);
            if (anos != null && anos < IDADE_MINIMA_GESTACAO) {
                n.setGestante(Gestante.NAO_SE_APLICA);
            }
        }

        if (n.getClassificacaoFinal() == ClassificacaoFinal.CONFIRMADO
                && n.getCasoAutoctone() == CasoAutoctone.SIM) {
            n.setUfInfeccao(n.getUfResidencia());
            n.setPaisInfeccao(preenchido(n.getPaisResidencia()) ? n.getPaisResidencia() : "Brasil");
            n.setMunicipioInfeccao(n.getMunicipioResidencia());
            n.setCodigoIbgeMunicipioInfeccao(n.getCodigoIbgeMunicipioResidencia());
            n.setDistritoInfeccao(n.getDistritoResidencia());
            n.setBairroInfeccao(n.getBairroResidencia());
        }
    }

    /** Aplica todas as regras condicionais. Mapa vazio significa que a ficha está coerente. */
    public static Map<String, String> validar(Notificacao n) {
        Map<String, String> erros = new LinkedHashMap<>();
        validarDatas(n, erros);
        validarIdadeEGestante(n, erros);
        validarResidencia(n, erros);
        validarConclusao(n, erros);
        validarLocalProvavelDeInfeccao(n, erros);
        return erros;
    }

    /** Idade em anos completos na data da notificação (ou hoje), ou null se não for possível saber. */
    public static Integer idadeEmAnos(Notificacao n) {
        if (n.getDataNascimento() != null) {
            LocalDate referencia = n.getDataNotificacao() != null ? n.getDataNotificacao() : LocalDate.now();
            if (n.getDataNascimento().isAfter(referencia)) {
                return null;
            }
            return Period.between(n.getDataNascimento(), referencia).getYears();
        }
        if (n.getIdade() != null && n.getUnidadeIdade() != null) {
            return n.getUnidadeIdade() == UnidadeIdade.ANO ? n.getIdade()
                    : n.getUnidadeIdade() == UnidadeIdade.MES ? n.getIdade() / 12
                    : 0;
        }
        return null;
    }

    /** "Brasil", em qualquer caixa e com ou sem acento, ou vazio: residente/infectado no Brasil. */
    static boolean ehBrasil(String pais) {
        return !preenchido(pais) || BRASIL.equals(semAcento(pais).trim().toLowerCase());
    }

    // ------------------------------------------------------------------ datas

    private static void validarDatas(Notificacao n, Map<String, String> erros) {
        LocalDate notificacao = n.getDataNotificacao();
        if (notificacao == null) {
            return;
        }
        if (n.getDataNascimento() != null && n.getDataNascimento().isAfter(notificacao)) {
            erros.putIfAbsent("dataNascimento", "A data de nascimento não pode ser posterior à data da notificação.");
        }
        if (n.getDataPrimeirosSintomas() != null && n.getDataPrimeirosSintomas().isAfter(notificacao)) {
            erros.putIfAbsent("dataPrimeirosSintomas",
                    "A data dos primeiros sintomas não pode ser posterior à data da notificação.");
        }
        if (n.getDataInvestigacao() != null && n.getDataInvestigacao().isBefore(notificacao)) {
            erros.putIfAbsent("dataInvestigacao",
                    "A data da investigação não pode ser anterior à data da notificação.");
        }
        if (n.getDataEncerramento() != null && n.getDataEncerramento().isBefore(notificacao)) {
            erros.putIfAbsent("dataEncerramento",
                    "A data de encerramento não pode ser anterior à data da notificação.");
        }
    }

    // ------------------------------------------------------------------ RN02

    private static void validarIdadeEGestante(Notificacao n, Map<String, String> erros) {
        if (n.getDataNascimento() == null) {
            if (n.getIdade() == null) {
                erros.put("idade", "Informe a idade, pois a data de nascimento não foi informada (campo 10).");
            }
            if (n.getUnidadeIdade() == null) {
                erros.put("unidadeIdade", "Informe a unidade da idade (hora, dia, mês ou ano).");
            }
        } else if (n.getIdade() != null && n.getUnidadeIdade() == null) {
            erros.put("unidadeIdade", "Informe a unidade da idade.");
        }

        if (n.getSexo() == Sexo.FEMININO && n.getGestante() == null) {
            erros.put("gestante", "Informe o período gestacional: obrigatório quando o sexo é feminino (campo 12).");
        }
    }

    // ------------------------------------------------------------------ RN03

    private static void validarResidencia(Notificacao n, Map<String, String> erros) {
        boolean ufInformada = preenchido(n.getUfResidencia());
        boolean outroPais = preenchido(n.getPaisResidencia()) && !ehBrasil(n.getPaisResidencia());

        if (!ufInformada && !outroPais) {
            erros.put("ufResidencia", "Informe a UF de residência: obrigatória quando o paciente reside no Brasil (campo 17).");
            erros.put("paisResidencia",
                    "Informe o país de residência se o paciente reside em outro país (campo 30).");
        }
        if (ufInformada && outroPais) {
            erros.put("ufResidencia", "Não informe a UF quando o paciente reside em outro país.");
        }
        if (ufInformada && !outroPais && !preenchido(n.getMunicipioResidencia())) {
            erros.put("municipioResidencia",
                    "Informe o município de residência: obrigatório quando a UF é informada (campo 18).");
        }
        if (outroPais && preenchido(n.getMunicipioResidencia())) {
            erros.putIfAbsent("municipioResidencia", "Não informe o município quando o paciente reside em outro país.");
        }
    }

    // ------------------------------------------------------------------ conclusão (31 a 43)

    private static void validarConclusao(Notificacao n, Map<String, String> erros) {
        boolean temConclusao = n.getClassificacaoFinal() != null || n.getCriterioConfirmacao() != null
                || n.getDataEncerramento() != null || n.getEvolucaoCaso() != null
                || n.getDoencaTrabalho() != null || n.getCasoAutoctone() != null || n.getDataObito() != null;
        if (temConclusao && n.getDataInvestigacao() == null) {
            erros.put("dataInvestigacao",
                    "Informe a data da investigação: obrigatória quando há dados de conclusão (campo 31).");
        }
        if (n.getDataEncerramento() != null && n.getClassificacaoFinal() == null) {
            erros.put("classificacaoFinal",
                    "Informe a classificação final: obrigatória quando a data de encerramento está preenchida (campo 32).");
        }
        if (n.getClassificacaoFinal() != null && n.getDataEncerramento() == null) {
            erros.put("dataEncerramento",
                    "Informe a data de encerramento: obrigatória quando a classificação final está preenchida (campo 43).");
        }

        boolean obito = n.getEvolucaoCaso() == EvolucaoCaso.OBITO_PELO_AGRAVO_NOTIFICADO
                || n.getEvolucaoCaso() == EvolucaoCaso.OBITO_POR_OUTRAS_CAUSAS;
        if (obito && n.getDataObito() == null) {
            erros.put("dataObito", "Informe a data do óbito, pois a evolução do caso é óbito (campo 42).");
        }
        if (!obito && n.getDataObito() != null) {
            erros.put("dataObito", "A data do óbito só deve ser informada quando a evolução do caso é óbito.");
        }
    }

    // ------------------------------------------------------------------ local provável de infecção (34 a 39)

    private static void validarLocalProvavelDeInfeccao(Notificacao n, Map<String, String> erros) {
        boolean confirmado = n.getClassificacaoFinal() == ClassificacaoFinal.CONFIRMADO;

        if (!confirmado) {
            marcarSePreenchido(erros, "casoAutoctone", n.getCasoAutoctone() != null);
            marcarLocal(n, erros, "O local provável de infecção só pode ser informado em caso confirmado.");
            return;
        }
        if (n.getCasoAutoctone() == null) {
            erros.put("casoAutoctone", "Informe se o caso é autóctone: obrigatório em caso confirmado (campo 34).");
            return;
        }
        if (n.getCasoAutoctone() == CasoAutoctone.INDETERMINADO) {
            marcarLocal(n, erros, "Não preencha o local provável de infecção quando a autoctonia é indeterminada.");
            return;
        }
        if (n.getCasoAutoctone() == CasoAutoctone.NAO) {
            if (!preenchido(n.getPaisInfeccao())) {
                erros.put("paisInfeccao",
                        "Informe o país do local provável de infecção: obrigatório quando o caso não é autóctone (campo 36).");
            } else if (ehBrasil(n.getPaisInfeccao())) {
                if (!preenchido(n.getUfInfeccao())) {
                    erros.put("ufInfeccao", "Informe a UF do local provável de infecção (campo 35).");
                }
                if (!preenchido(n.getMunicipioInfeccao())) {
                    erros.put("municipioInfeccao", "Informe o município do local provável de infecção (campo 37).");
                }
            }
        }
        // Autóctone (SIM): os campos foram copiados da residência em normalizar().
    }

    private static void marcarLocal(Notificacao n, Map<String, String> erros, String mensagem) {
        marcarSePreenchido(erros, "ufInfeccao", preenchido(n.getUfInfeccao()), mensagem);
        marcarSePreenchido(erros, "paisInfeccao", preenchido(n.getPaisInfeccao()), mensagem);
        marcarSePreenchido(erros, "municipioInfeccao", preenchido(n.getMunicipioInfeccao()), mensagem);
        marcarSePreenchido(erros, "codigoIbgeMunicipioInfeccao", preenchido(n.getCodigoIbgeMunicipioInfeccao()), mensagem);
        marcarSePreenchido(erros, "distritoInfeccao", preenchido(n.getDistritoInfeccao()), mensagem);
        marcarSePreenchido(erros, "bairroInfeccao", preenchido(n.getBairroInfeccao()), mensagem);
    }

    private static void marcarSePreenchido(Map<String, String> erros, String campo, boolean preenchido) {
        marcarSePreenchido(erros, campo, preenchido,
                "O local provável de infecção só pode ser informado em caso confirmado.");
    }

    private static void marcarSePreenchido(Map<String, String> erros, String campo, boolean preenchido, String msg) {
        if (preenchido) {
            erros.put(campo, msg);
        }
    }

    // ------------------------------------------------------------------ utilitários

    private static boolean preenchido(String texto) {
        return texto != null && !texto.isBlank();
    }

    private static String semAcento(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
}
