// Definição dos campos da Ficha de Notificação/Conclusão do SINAN.
// O formulário de cadastro é gerado a partir desta lista; os valores das opções
// são exatamente os nomes dos enums da API, e o texto mostra o código impresso na ficha.

const OPCOES = {
  tipoNotificacao: { INDIVIDUAL: '2 - Individual' },
  unidadeIdade: { HORA: '1 - Hora', DIA: '2 - Dia', MES: '3 - Mês', ANO: '4 - Ano' },
  sexo: { MASCULINO: 'M - Masculino', FEMININO: 'F - Feminino', IGNORADO: 'I - Ignorado' },
  gestante: {
    PRIMEIRO_TRIMESTRE: '1 - 1º Trimestre', SEGUNDO_TRIMESTRE: '2 - 2º Trimestre', TERCEIRO_TRIMESTRE: '3 - 3º Trimestre',
    IDADE_GESTACIONAL_IGNORADA: '4 - Idade gestacional ignorada', NAO: '5 - Não', NAO_SE_APLICA: '6 - Não se aplica', IGNORADO: '9 - Ignorado'
  },
  racaCor: { BRANCA: '1 - Branca', PRETA: '2 - Preta', AMARELA: '3 - Amarela', PARDA: '4 - Parda', INDIGENA: '5 - Indígena', IGNORADO: '9 - Ignorado' },
  escolaridade: {
    ANALFABETO: '0 - Analfabeto', EF_1A_4A_INCOMPLETA: '1 - 1ª a 4ª série incompleta do EF', EF_4A_COMPLETA: '2 - 4ª série completa do EF',
    EF_5A_8A_INCOMPLETA: '3 - 5ª à 8ª série incompleta do EF', EF_COMPLETO: '4 - Ensino fundamental completo',
    EM_INCOMPLETO: '5 - Ensino médio incompleto', EM_COMPLETO: '6 - Ensino médio completo',
    SUPERIOR_INCOMPLETO: '7 - Educação superior incompleta', SUPERIOR_COMPLETO: '8 - Educação superior completa',
    IGNORADO: '9 - Ignorado', NAO_SE_APLICA: '10 - Não se aplica'
  },
  zona: { URBANA: '1 - Urbana', RURAL: '2 - Rural', PERIURBANA: '3 - Periurbana', IGNORADO: '9 - Ignorado' },
  classificacaoFinal: { CONFIRMADO: '1 - Confirmado', DESCARTADO: '2 - Descartado' },
  criterioConfirmacao: { LABORATORIAL: '1 - Laboratorial', CLINICO_EPIDEMIOLOGICO: '2 - Clínico-epidemiológico' },
  casoAutoctone: { SIM: '1 - Sim', NAO: '2 - Não', INDETERMINADO: '3 - Indeterminado' },
  doencaTrabalho: { SIM: '1 - Sim', NAO: '2 - Não', IGNORADO: '9 - Ignorado' },
  evolucaoCaso: {
    CURA: '1 - Cura', OBITO_PELO_AGRAVO_NOTIFICADO: '2 - Óbito pelo agravo notificado',
    OBITO_POR_OUTRAS_CAUSAS: '3 - Óbito por outras causas', IGNORADO: '9 - Ignorado'
  }
};

// tipo: text | date | number | select | textarea.  obrig: obrigatório em qualquer ficha.
// ref: número do campo na ficha.  uf: força maiúsculas.  largo/total: ocupa 2 colunas / a linha toda.
const SECOES = [
  { titulo: 'Dados gerais', campos: [
    { nome: 'numero', rotulo: 'Nº da notificação', ref: 'N.º', tipo: 'text', max: 20, obrig: true },
    { nome: 'tipoNotificacao', rotulo: 'Tipo de notificação', ref: 1, tipo: 'select', obrig: true, padrao: 'INDIVIDUAL' },
    { nome: 'agravo', rotulo: 'Agravo/doença', ref: 2, tipo: 'text', max: 120, obrig: true, largo: true },
    { nome: 'codigoCid10', rotulo: 'Código (CID10)', ref: 2, tipo: 'text', max: 10, dica: 'Ex.: A90' },
    { nome: 'dataNotificacao', rotulo: 'Data da notificação', ref: 3, tipo: 'date', obrig: true },
    { nome: 'ufNotificacao', rotulo: 'UF', ref: 4, tipo: 'text', max: 2, obrig: true, uf: true },
    { nome: 'municipioNotificacao', rotulo: 'Município de notificação', ref: 5, tipo: 'text', max: 100, obrig: true },
    { nome: 'codigoIbgeMunicipioNotificacao', rotulo: 'Código IBGE do município', ref: 5, tipo: 'text', max: 7, dica: '7 dígitos' },
    { nome: 'unidadeSaude', rotulo: 'Unidade de saúde (ou outra fonte notificadora)', ref: 6, tipo: 'text', max: 150, obrig: true, largo: true },
    { nome: 'codigoUnidadeSaude', rotulo: 'Cód. da unidade de saúde', ref: 6, tipo: 'text', max: 20 },
    { nome: 'dataPrimeirosSintomas', rotulo: 'Data dos primeiros sintomas', ref: 7, tipo: 'date', obrig: true }
  ]},
  { titulo: 'Notificação individual', campos: [
    { nome: 'nomePaciente', rotulo: 'Nome do paciente', ref: 8, tipo: 'text', max: 150, obrig: true, largo: true },
    { nome: 'dataNascimento', rotulo: 'Data de nascimento', ref: 9, tipo: 'date' },
    { nome: 'idade', rotulo: 'Idade', ref: 10, tipo: 'number', dica: 'Somente se a data de nascimento for desconhecida (obrigatória nesse caso)' },
    { nome: 'unidadeIdade', rotulo: 'Unidade da idade', ref: 10, tipo: 'select' },
    { nome: 'sexo', rotulo: 'Sexo', ref: 11, tipo: 'select', obrig: true },
    { nome: 'gestante', rotulo: 'Gestante', ref: 12, tipo: 'select', dica: 'Obrigatório quando o sexo é feminino (e idade ≥ 7 anos)' },
    { nome: 'racaCor', rotulo: 'Raça/Cor', ref: 13, tipo: 'select' },
    { nome: 'escolaridade', rotulo: 'Escolaridade', ref: 14, tipo: 'select', largo: true },
    { nome: 'cartaoSus', rotulo: 'Número do Cartão SUS', ref: 15, tipo: 'text', max: 15, dica: '15 dígitos' },
    { nome: 'nomeMae', rotulo: 'Nome da mãe', ref: 16, tipo: 'text', max: 150, largo: true }
  ]},
  { titulo: 'Dados de residência', campos: [
    { nome: 'ufResidencia', rotulo: 'UF', ref: 17, tipo: 'text', max: 2, uf: true, dica: 'Obrigatória se reside no Brasil' },
    { nome: 'municipioResidencia', rotulo: 'Município de residência', ref: 18, tipo: 'text', max: 100, dica: 'Obrigatório se a UF for informada' },
    { nome: 'codigoIbgeMunicipioResidencia', rotulo: 'Código IBGE do município', ref: 18, tipo: 'text', max: 7 },
    { nome: 'distritoResidencia', rotulo: 'Distrito', ref: 19, tipo: 'text', max: 100 },
    { nome: 'bairroResidencia', rotulo: 'Bairro', ref: 20, tipo: 'text', max: 100 },
    { nome: 'logradouro', rotulo: 'Logradouro (rua, avenida, ...)', ref: 21, tipo: 'text', max: 150, largo: true },
    { nome: 'codigoLogradouro', rotulo: 'Código do logradouro', ref: 21, tipo: 'text', max: 20 },
    { nome: 'numeroResidencia', rotulo: 'Número', ref: 22, tipo: 'text', max: 20 },
    { nome: 'complemento', rotulo: 'Complemento (apto., casa, ...)', ref: 23, tipo: 'text', max: 100 },
    { nome: 'geoCampo1', rotulo: 'Geo campo 1', ref: 24, tipo: 'text', max: 50 },
    { nome: 'geoCampo2', rotulo: 'Geo campo 2', ref: 25, tipo: 'text', max: 50 },
    { nome: 'pontoReferencia', rotulo: 'Ponto de referência', ref: 26, tipo: 'text', max: 150, largo: true },
    { nome: 'cep', rotulo: 'CEP', ref: 27, tipo: 'text', max: 9, dica: 'Ex.: 58800-000' },
    { nome: 'telefone', rotulo: '(DDD) Telefone', ref: 28, tipo: 'text', max: 20, dica: 'Ex.: (83) 99999-9999' },
    { nome: 'zona', rotulo: 'Zona', ref: 29, tipo: 'select' },
    { nome: 'paisResidencia', rotulo: 'País (se residente fora do Brasil)', ref: 30, tipo: 'text', max: 60, dica: 'Deixe em branco se reside no Brasil' }
  ]},
  { titulo: 'Conclusão', campos: [
    { nome: 'dataInvestigacao', rotulo: 'Data da investigação', ref: 31, tipo: 'date', dica: 'Obrigatória quando houver dados de conclusão' },
    { nome: 'classificacaoFinal', rotulo: 'Classificação final', ref: 32, tipo: 'select', dica: 'Exige a data de encerramento' },
    { nome: 'criterioConfirmacao', rotulo: 'Critério de confirmação/descarte', ref: 33, tipo: 'select' },
    { nome: 'dataEncerramento', rotulo: 'Data do encerramento', ref: 43, tipo: 'date', dica: 'Exige a classificação final' }
  ]},
  { titulo: 'Local provável da fonte de infecção (somente caso confirmado)', campos: [
    { nome: 'casoAutoctone', rotulo: 'O caso é autóctone do município de residência?', ref: 34, tipo: 'select', largo: true,
      dica: 'Se "Sim", o local de infecção é preenchido com os dados de residência' },
    { nome: 'ufInfeccao', rotulo: 'UF', ref: 35, tipo: 'text', max: 2, uf: true },
    { nome: 'paisInfeccao', rotulo: 'País', ref: 36, tipo: 'text', max: 60 },
    { nome: 'municipioInfeccao', rotulo: 'Município', ref: 37, tipo: 'text', max: 100 },
    { nome: 'codigoIbgeMunicipioInfeccao', rotulo: 'Código IBGE do município', ref: 37, tipo: 'text', max: 7 },
    { nome: 'distritoInfeccao', rotulo: 'Distrito', ref: 38, tipo: 'text', max: 100 },
    { nome: 'bairroInfeccao', rotulo: 'Bairro', ref: 39, tipo: 'text', max: 100 }
  ]},
  { titulo: 'Evolução do caso', campos: [
    { nome: 'doencaTrabalho', rotulo: 'Doença relacionada ao trabalho', ref: 40, tipo: 'select' },
    { nome: 'evolucaoCaso', rotulo: 'Evolução do caso', ref: 41, tipo: 'select', largo: true },
    { nome: 'dataObito', rotulo: 'Data do óbito', ref: 42, tipo: 'date', dica: 'Somente se a evolução for óbito' }
  ]},
  { titulo: 'Informações complementares e investigador', campos: [
    { nome: 'observacoes', rotulo: 'Observações adicionais', tipo: 'textarea', max: 2000, total: true },
    { nome: 'investigadorUnidade', rotulo: 'Município/Unidade de saúde', tipo: 'text', max: 150, largo: true },
    { nome: 'investigadorCodigoUnidade', rotulo: 'Cód. da unidade de saúde', tipo: 'text', max: 20 },
    { nome: 'investigadorNome', rotulo: 'Nome do investigador', tipo: 'text', max: 150, largo: true },
    { nome: 'investigadorFuncao', rotulo: 'Função', tipo: 'text', max: 100 }
  ]}
];

const TODOS_CAMPOS = SECOES.flatMap(s => s.campos);
