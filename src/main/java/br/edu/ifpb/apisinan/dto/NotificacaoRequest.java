package br.edu.ifpb.apisinan.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import br.edu.ifpb.apisinan.entities.enums.CasoAutoctone;
import br.edu.ifpb.apisinan.entities.enums.ClassificacaoFinal;
import br.edu.ifpb.apisinan.entities.enums.CriterioConfirmacao;
import br.edu.ifpb.apisinan.entities.enums.DoencaTrabalho;
import br.edu.ifpb.apisinan.entities.enums.Escolaridade;
import br.edu.ifpb.apisinan.entities.enums.EvolucaoCaso;
import br.edu.ifpb.apisinan.entities.enums.Gestante;
import br.edu.ifpb.apisinan.entities.enums.RacaCor;
import br.edu.ifpb.apisinan.entities.enums.Sexo;
import br.edu.ifpb.apisinan.entities.enums.TipoNotificacao;
import br.edu.ifpb.apisinan.entities.enums.UnidadeIdade;
import br.edu.ifpb.apisinan.entities.enums.Zona;

public record NotificacaoRequest(
    @NotBlank(message = "O número da notificação é obrigatório")
    @Size(max = 20)
    String numero,
    @NotNull(message = "O tipo de notificação é obrigatório")
    TipoNotificacao tipoNotificacao,
    @NotBlank(message = "O agravo/doença é obrigatório")
    @Size(max = 120)
    String agravo,
    @Pattern(regexp = Formatos.CID10, message = "CID-10 inválido (ex.: A90 ou A90.1)")
    String codigoCid10,
    @NotNull(message = "A data da notificação é obrigatória")
    @PastOrPresent(message = "A data da notificação não pode ser futura")
    LocalDate dataNotificacao,
    @NotBlank(message = "A UF da notificação é obrigatória")
    @Pattern(regexp = Formatos.UF, message = "UF inválida (use a sigla, ex.: PB)")
    String ufNotificacao,
    @NotBlank(message = "O município de notificação é obrigatório")
    @Size(max = 100)
    String municipioNotificacao,
    @Pattern(regexp = Formatos.IBGE, message = "O código IBGE deve ter 7 dígitos")
    String codigoIbgeMunicipioNotificacao,
    @NotBlank(message = "A unidade de saúde é obrigatória")
    @Size(max = 150)
    String unidadeSaude,
    @Size(max = 20)
    String codigoUnidadeSaude,
    @NotNull(message = "A data dos primeiros sintomas é obrigatória")
    @PastOrPresent(message = "A data dos primeiros sintomas não pode ser futura")
    LocalDate dataPrimeirosSintomas,
    @NotBlank(message = "O nome do paciente é obrigatório")
    @Size(max = 150)
    String nomePaciente,
    @PastOrPresent(message = "A data de nascimento não pode ser futura")
    LocalDate dataNascimento,
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 150, message = "Idade inválida")
    Integer idade,
    UnidadeIdade unidadeIdade,
    @NotNull(message = "O sexo é obrigatório")
    Sexo sexo,
    Gestante gestante,
    RacaCor racaCor,
    Escolaridade escolaridade,
    @Pattern(regexp = Formatos.CARTAO_SUS, message = "O Cartão SUS deve ter 15 dígitos")
    String cartaoSus,
    @Size(max = 150)
    String nomeMae,
    @Pattern(regexp = Formatos.UF, message = "UF inválida (use a sigla, ex.: PB)")
    String ufResidencia,
    @Size(max = 100)
    String municipioResidencia,
    @Pattern(regexp = Formatos.IBGE, message = "O código IBGE deve ter 7 dígitos")
    String codigoIbgeMunicipioResidencia,
    @Size(max = 100)
    String distritoResidencia,
    @Size(max = 100)
    String bairroResidencia,
    @Size(max = 150)
    String logradouro,
    @Size(max = 20)
    String codigoLogradouro,
    @Size(max = 20)
    String numeroResidencia,
    @Size(max = 100)
    String complemento,
    @Size(max = 50)
    String geoCampo1,
    @Size(max = 50)
    String geoCampo2,
    @Size(max = 150)
    String pontoReferencia,
    @Pattern(regexp = Formatos.CEP, message = "CEP inválido (ex.: 58800-000)")
    String cep,
    @Pattern(regexp = Formatos.TELEFONE, message = "Telefone inválido (ex.: (83) 99999-9999)")
    String telefone,
    Zona zona,
    @Size(max = 60)
    String paisResidencia,
    @PastOrPresent(message = "A data da investigação não pode ser futura")
    LocalDate dataInvestigacao,
    ClassificacaoFinal classificacaoFinal,
    CriterioConfirmacao criterioConfirmacao,
    CasoAutoctone casoAutoctone,
    @Pattern(regexp = Formatos.UF, message = "UF inválida (use a sigla, ex.: PB)")
    String ufInfeccao,
    @Size(max = 60)
    String paisInfeccao,
    @Size(max = 100)
    String municipioInfeccao,
    @Pattern(regexp = Formatos.IBGE, message = "O código IBGE deve ter 7 dígitos")
    String codigoIbgeMunicipioInfeccao,
    @Size(max = 100)
    String distritoInfeccao,
    @Size(max = 100)
    String bairroInfeccao,
    DoencaTrabalho doencaTrabalho,
    EvolucaoCaso evolucaoCaso,
    @PastOrPresent(message = "A data do óbito não pode ser futura")
    LocalDate dataObito,
    @PastOrPresent(message = "A data de encerramento não pode ser futura")
    LocalDate dataEncerramento,
    @Size(max = 2000)
    String observacoes,
    @Size(max = 150)
    String investigadorUnidade,
    @Size(max = 20)
    String investigadorCodigoUnidade,
    @Size(max = 150)
    String investigadorNome,
    @Size(max = 100)
    String investigadorFuncao) { }

