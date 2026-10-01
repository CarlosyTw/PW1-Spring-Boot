package br.edu.ifpb.apisinan.entities;

import java.time.LocalDate;

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

public class Notificacao {
 // Atributos (Seguindo tabela do sinan)
  // Identificação
  private Long id;
  private String numero;

  // Dados gerais
  private TipoNotificacao tipoNotificacao;
  private String agravo;
  private String codigoCid10;
  private LocalDate dataNotificacao;
  private String ufNotificacao;
  private String municipioNotificacao;
  private String codigoIbgeMunicipioNotificacao;
  private String unidadeSaude;
  private String codigoUnidadeSaude;
  private LocalDate dataPrimeirosSintomas;

  // Notificação individual
  private String nomePaciente;
  private LocalDate dataNascimento;
  private Integer idade;
  private UnidadeIdade unidadeIdade;
  private Sexo sexo;
  private Gestante gestante;
  private RacaCor racaCor;
  private Escolaridade escolaridade;
  private String cartaoSus;
  private String nomeMae;

  // Dados de residência
  private String ufResidencia;
  private String municipioResidencia;
  private String codigoIbgeMunicipioResidencia;
  private String distritoResidencia;
  private String bairroResidencia;
  private String logradouro;
  private String codigoLogradouro;
  private String numeroResidencia;
  private String complemento;
  private String geoCampo1;
  private String geoCampo2;
  private String pontoReferencia;
  private String cep;
  private String telefone;
  private Zona zona;
  private String paisResidencia;

  // Conclusão
  private LocalDate dataInvestigacao;
  private ClassificacaoFinal classificacaoFinal;
  private CriterioConfirmacao criterioConfirmacao;

  // Local provável da fonte de infecção
  private CasoAutoctone casoAutoctone;
  private String ufInfeccao;
  private String paisInfeccao;
  private String municipioInfeccao;
  private String codigoIbgeMunicipioInfeccao;
  private String distritoInfeccao;
  private String bairroInfeccao;

  // Fechamento
  private DoencaTrabalho doencaTrabalho;
  private EvolucaoCaso evolucaoCaso;
  private LocalDate dataObito;
  private LocalDate dataEncerramento;

  // Observações
  private String observacoes;

  // Investigador
  private String investigadorUnidade;
  private String investigadorCodigoUnidade;
  private String investigadorNome;
  private String investigadorFuncao;

  // Construtores
  public Notificacao() {}

  public Notificacao(
      String numero,
      TipoNotificacao tipoNotificacao,
      String agravo,
      String codigoCid10,
      LocalDate dataNotificacao,
      String ufNotificacao,
      String municipioNotificacao,
      String codigoIbgeMunicipioNotificacao,
      String unidadeSaude,
      String codigoUnidadeSaude,
      LocalDate dataPrimeirosSintomas,
      String nomePaciente,
      LocalDate dataNascimento,
      Integer idade,
      UnidadeIdade unidadeIdade,
      Sexo sexo,
      Gestante gestante,
      RacaCor racaCor,
      Escolaridade escolaridade,
      String cartaoSus,
      String nomeMae,
      String ufResidencia,
      String municipioResidencia,
      String codigoIbgeMunicipioResidencia,
      String distritoResidencia,
      String bairroResidencia,
      String logradouro,
      String codigoLogradouro,
      String numeroResidencia,
      String complemento,
      String geoCampo1,
      String geoCampo2,
      String pontoReferencia,
      String cep,
      String telefone,
      Zona zona,
      String paisResidencia,
      LocalDate dataInvestigacao,
      ClassificacaoFinal classificacaoFinal,
      CriterioConfirmacao criterioConfirmacao,
      CasoAutoctone casoAutoctone,
      String ufInfeccao,
      String paisInfeccao,
      String municipioInfeccao,
      String codigoIbgeMunicipioInfeccao,
      String distritoInfeccao,
      String bairroInfeccao,
      DoencaTrabalho doencaTrabalho,
      EvolucaoCaso evolucaoCaso,
      LocalDate dataObito,
      LocalDate dataEncerramento,
      String observacoes,
      String investigadorUnidade,
      String investigadorCodigoUnidade,
      String investigadorNome,
      String investigadorFuncao) {
    this.numero = numero;
    this.tipoNotificacao = tipoNotificacao;
    this.agravo = agravo;
    this.codigoCid10 = codigoCid10;
    this.dataNotificacao = dataNotificacao;
    this.ufNotificacao = ufNotificacao;
    this.municipioNotificacao = municipioNotificacao;
    this.codigoIbgeMunicipioNotificacao = codigoIbgeMunicipioNotificacao;
    this.unidadeSaude = unidadeSaude;
    this.codigoUnidadeSaude = codigoUnidadeSaude;
    this.dataPrimeirosSintomas = dataPrimeirosSintomas;
    this.nomePaciente = nomePaciente;
    this.dataNascimento = dataNascimento;
    this.idade = idade;
    this.unidadeIdade = unidadeIdade;
    this.sexo = sexo;
    this.gestante = gestante;
    this.racaCor = racaCor;
    this.escolaridade = escolaridade;
    this.cartaoSus = cartaoSus;
    this.nomeMae = nomeMae;
    this.ufResidencia = ufResidencia;
    this.municipioResidencia = municipioResidencia;
    this.codigoIbgeMunicipioResidencia = codigoIbgeMunicipioResidencia;
    this.distritoResidencia = distritoResidencia;
    this.bairroResidencia = bairroResidencia;
    this.logradouro = logradouro;
    this.codigoLogradouro = codigoLogradouro;
    this.numeroResidencia = numeroResidencia;
    this.complemento = complemento;
    this.geoCampo1 = geoCampo1;
    this.geoCampo2 = geoCampo2;
    this.pontoReferencia = pontoReferencia;
    this.cep = cep;
    this.telefone = telefone;
    this.zona = zona;
    this.paisResidencia = paisResidencia;
    this.dataInvestigacao = dataInvestigacao;
    this.classificacaoFinal = classificacaoFinal;
    this.criterioConfirmacao = criterioConfirmacao;
    this.casoAutoctone = casoAutoctone;
    this.ufInfeccao = ufInfeccao;
    this.paisInfeccao = paisInfeccao;
    this.municipioInfeccao = municipioInfeccao;
    this.codigoIbgeMunicipioInfeccao = codigoIbgeMunicipioInfeccao;
    this.distritoInfeccao = distritoInfeccao;
    this.bairroInfeccao = bairroInfeccao;
    this.doencaTrabalho = doencaTrabalho;
    this.evolucaoCaso = evolucaoCaso;
    this.dataObito = dataObito;
    this.dataEncerramento = dataEncerramento;
    this.observacoes = observacoes;
    this.investigadorUnidade = investigadorUnidade;
    this.investigadorCodigoUnidade = investigadorCodigoUnidade;
    this.investigadorNome = investigadorNome;
    this.investigadorFuncao = investigadorFuncao;
  }

  // Getters e Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public TipoNotificacao getTipoNotificacao() {
    return tipoNotificacao;
  }

  public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
    this.tipoNotificacao = tipoNotificacao;
  }

  public String getAgravo() {
    return agravo;
  }

  public void setAgravo(String agravo) {
    this.agravo = agravo;
  }

  public String getCodigoCid10() {
    return codigoCid10;
  }

  public void setCodigoCid10(String codigoCid10) {
    this.codigoCid10 = codigoCid10;
  }

  public LocalDate getDataNotificacao() {
    return dataNotificacao;
  }

  public void setDataNotificacao(LocalDate dataNotificacao) {
    this.dataNotificacao = dataNotificacao;
  }

  public String getUfNotificacao() {
    return ufNotificacao;
  }

  public void setUfNotificacao(String ufNotificacao) {
    this.ufNotificacao = ufNotificacao;
  }

  public String getMunicipioNotificacao() {
    return municipioNotificacao;
  }

  public void setMunicipioNotificacao(String municipioNotificacao) {
    this.municipioNotificacao = municipioNotificacao;
  }

  public String getCodigoIbgeMunicipioNotificacao() {
    return codigoIbgeMunicipioNotificacao;
  }

  public void setCodigoIbgeMunicipioNotificacao(String codigoIbgeMunicipioNotificacao) {
    this.codigoIbgeMunicipioNotificacao = codigoIbgeMunicipioNotificacao;
  }

  public String getUnidadeSaude() {
    return unidadeSaude;
  }

  public void setUnidadeSaude(String unidadeSaude) {
    this.unidadeSaude = unidadeSaude;
  }

  public String getCodigoUnidadeSaude() {
    return codigoUnidadeSaude;
  }

  public void setCodigoUnidadeSaude(String codigoUnidadeSaude) {
    this.codigoUnidadeSaude = codigoUnidadeSaude;
  }

  public LocalDate getDataPrimeirosSintomas() {
    return dataPrimeirosSintomas;
  }

  public void setDataPrimeirosSintomas(LocalDate dataPrimeirosSintomas) {
    this.dataPrimeirosSintomas = dataPrimeirosSintomas;
  }

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public Integer getIdade() {
    return idade;
  }

  public void setIdade(Integer idade) {
    this.idade = idade;
  }

  public UnidadeIdade getUnidadeIdade() {
    return unidadeIdade;
  }

  public void setUnidadeIdade(UnidadeIdade unidadeIdade) {
    this.unidadeIdade = unidadeIdade;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }

  public Gestante getGestante() {
    return gestante;
  }

  public void setGestante(Gestante gestante) {
    this.gestante = gestante;
  }

  public RacaCor getRacaCor() {
    return racaCor;
  }

  public void setRacaCor(RacaCor racaCor) {
    this.racaCor = racaCor;
  }

  public Escolaridade getEscolaridade() {
    return escolaridade;
  }

  public void setEscolaridade(Escolaridade escolaridade) {
    this.escolaridade = escolaridade;
  }

  public String getCartaoSus() {
    return cartaoSus;
  }

  public void setCartaoSus(String cartaoSus) {
    this.cartaoSus = cartaoSus;
  }

  public String getNomeMae() {
    return nomeMae;
  }

  public void setNomeMae(String nomeMae) {
    this.nomeMae = nomeMae;
  }

  public String getUfResidencia() {
    return ufResidencia;
  }

  public void setUfResidencia(String ufResidencia) {
    this.ufResidencia = ufResidencia;
  }

  public String getMunicipioResidencia() {
    return municipioResidencia;
  }

  public void setMunicipioResidencia(String municipioResidencia) {
    this.municipioResidencia = municipioResidencia;
  }

  public String getCodigoIbgeMunicipioResidencia() {
    return codigoIbgeMunicipioResidencia;
  }

  public void setCodigoIbgeMunicipioResidencia(String codigoIbgeMunicipioResidencia) {
    this.codigoIbgeMunicipioResidencia = codigoIbgeMunicipioResidencia;
  }

  public String getDistritoResidencia() {
    return distritoResidencia;
  }

  public void setDistritoResidencia(String distritoResidencia) {
    this.distritoResidencia = distritoResidencia;
  }

  public String getBairroResidencia() {
    return bairroResidencia;
  }

  public void setBairroResidencia(String bairroResidencia) {
    this.bairroResidencia = bairroResidencia;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getCodigoLogradouro() {
    return codigoLogradouro;
  }

  public void setCodigoLogradouro(String codigoLogradouro) {
    this.codigoLogradouro = codigoLogradouro;
  }

  public String getNumeroResidencia() {
    return numeroResidencia;
  }

  public void setNumeroResidencia(String numeroResidencia) {
    this.numeroResidencia = numeroResidencia;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public String getGeoCampo1() {
    return geoCampo1;
  }

  public void setGeoCampo1(String geoCampo1) {
    this.geoCampo1 = geoCampo1;
  }

  public String getGeoCampo2() {
    return geoCampo2;
  }

  public void setGeoCampo2(String geoCampo2) {
    this.geoCampo2 = geoCampo2;
  }

  public String getPontoReferencia() {
    return pontoReferencia;
  }

  public void setPontoReferencia(String pontoReferencia) {
    this.pontoReferencia = pontoReferencia;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public Zona getZona() {
    return zona;
  }

  public void setZona(Zona zona) {
    this.zona = zona;
  }

  public String getPaisResidencia() {
    return paisResidencia;
  }

  public void setPaisResidencia(String paisResidencia) {
    this.paisResidencia = paisResidencia;
  }

  public LocalDate getDataInvestigacao() {
    return dataInvestigacao;
  }

  public void setDataInvestigacao(LocalDate dataInvestigacao) {
    this.dataInvestigacao = dataInvestigacao;
  }

  public ClassificacaoFinal getClassificacaoFinal() {
    return classificacaoFinal;
  }

  public void setClassificacaoFinal(ClassificacaoFinal classificacaoFinal) {
    this.classificacaoFinal = classificacaoFinal;
  }

  public CriterioConfirmacao getCriterioConfirmacao() {
    return criterioConfirmacao;
  }

  public void setCriterioConfirmacao(CriterioConfirmacao criterioConfirmacao) {
    this.criterioConfirmacao = criterioConfirmacao;
  }

  public CasoAutoctone getCasoAutoctone() {
    return casoAutoctone;
  }

  public void setCasoAutoctone(CasoAutoctone casoAutoctone) {
    this.casoAutoctone = casoAutoctone;
  }

  public String getUfInfeccao() {
    return ufInfeccao;
  }

  public void setUfInfeccao(String ufInfeccao) {
    this.ufInfeccao = ufInfeccao;
  }

  public String getPaisInfeccao() {
    return paisInfeccao;
  }

  public void setPaisInfeccao(String paisInfeccao) {
    this.paisInfeccao = paisInfeccao;
  }

  public String getMunicipioInfeccao() {
    return municipioInfeccao;
  }

  public void setMunicipioInfeccao(String municipioInfeccao) {
    this.municipioInfeccao = municipioInfeccao;
  }

  public String getCodigoIbgeMunicipioInfeccao() {
    return codigoIbgeMunicipioInfeccao;
  }

  public void setCodigoIbgeMunicipioInfeccao(String codigoIbgeMunicipioInfeccao) {
    this.codigoIbgeMunicipioInfeccao = codigoIbgeMunicipioInfeccao;
  }

  public String getDistritoInfeccao() {
    return distritoInfeccao;
  }

  public void setDistritoInfeccao(String distritoInfeccao) {
    this.distritoInfeccao = distritoInfeccao;
  }

  public String getBairroInfeccao() {
    return bairroInfeccao;
  }

  public void setBairroInfeccao(String bairroInfeccao) {
    this.bairroInfeccao = bairroInfeccao;
  }

  public DoencaTrabalho getDoencaTrabalho() {
    return doencaTrabalho;
  }

  public void setDoencaTrabalho(DoencaTrabalho doencaTrabalho) {
    this.doencaTrabalho = doencaTrabalho;
  }

  public EvolucaoCaso getEvolucaoCaso() {
    return evolucaoCaso;
  }

  public void setEvolucaoCaso(EvolucaoCaso evolucaoCaso) {
    this.evolucaoCaso = evolucaoCaso;
  }

  public LocalDate getDataObito() {
    return dataObito;
  }

  public void setDataObito(LocalDate dataObito) {
    this.dataObito = dataObito;
  }

  public LocalDate getDataEncerramento() {
    return dataEncerramento;
  }

  public void setDataEncerramento(LocalDate dataEncerramento) {
    this.dataEncerramento = dataEncerramento;
  }

  public String getObservacoes() {
    return observacoes;
  }

  public void setObservacoes(String observacoes) {
    this.observacoes = observacoes;
  }

  public String getInvestigadorUnidade() {
    return investigadorUnidade;
  }

  public void setInvestigadorUnidade(String investigadorUnidade) {
    this.investigadorUnidade = investigadorUnidade;
  }

  public String getInvestigadorCodigoUnidade() {
    return investigadorCodigoUnidade;
  }

  public void setInvestigadorCodigoUnidade(String investigadorCodigoUnidade) {
    this.investigadorCodigoUnidade = investigadorCodigoUnidade;
  }

  public String getInvestigadorNome() {
    return investigadorNome;
  }

  public void setInvestigadorNome(String investigadorNome) {
    this.investigadorNome = investigadorNome;
  }

  public String getInvestigadorFuncao() {
    return investigadorFuncao;
  }

  public void setInvestigadorFuncao(String investigadorFuncao) {
    this.investigadorFuncao = investigadorFuncao;
  }

}
