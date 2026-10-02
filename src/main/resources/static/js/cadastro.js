// Página de cadastro e alteração. Com ?id=N carrega a notificação e salva com PUT; sem id, salva com POST.
// As regras daqui só ajudam o preenchimento: quem decide é sempre a API.

const params = new URLSearchParams(location.search);
const idEdicao = params.get('id');
const formulario = document.getElementById('formulario');
const faixa = document.getElementById('faixa');

const el = nome => document.getElementById(nome);
const val = nome => el(nome).value.trim();

// ---------------------------------------------------------------- montagem do formulário

function criarControle(c) {
  let controle;
  if (c.tipo === 'select') {
    controle = document.createElement('select');
    controle.append(new Option('', ''));
    Object.entries(OPCOES[c.nome] || {}).forEach(([valor, texto]) => controle.append(new Option(texto, valor)));
  } else if (c.tipo === 'textarea') {
    controle = document.createElement('textarea');
    controle.rows = 3;
  } else {
    controle = document.createElement('input');
    controle.type = c.tipo;
    if (c.tipo === 'number') { controle.min = 0; controle.max = 150; }
  }
  controle.id = controle.name = c.nome;
  if (c.max && c.tipo !== 'number') controle.maxLength = c.max;
  if (c.tipo === 'date') controle.max = new Date().toISOString().slice(0, 10);
  return controle;
}

function montarFormulario() {
  SECOES.forEach(secao => {
    const fs = document.createElement('fieldset');
    const lg = document.createElement('legend');
    lg.textContent = secao.titulo;
    const grade = document.createElement('div');
    grade.className = 'grade';

    secao.campos.forEach(c => {
      const bloco = document.createElement('div');
      bloco.className = 'campo' + (c.largo ? ' largo' : '') + (c.total ? ' total' : '');
      bloco.id = `campo-${c.nome}`;

      const rotulo = document.createElement('label');
      rotulo.htmlFor = c.nome;
      rotulo.textContent = c.rotulo;
      if (c.obrig) { const s = document.createElement('span'); s.className = 'obrig'; s.textContent = '*'; rotulo.append(s); }
      if (c.ref) { const r = document.createElement('span'); r.className = 'ref'; r.textContent = ` (${c.ref})`; rotulo.append(r); }

      const controle = criarControle(c);
      if (c.padrao) controle.value = c.padrao;
      controle.addEventListener('input', () => {
        if (c.uf) controle.value = controle.value.toUpperCase();
        limparErro(c.nome);
        atualizarDependencias();
      });

      const erro = document.createElement('span');
      erro.className = 'msg-erro';
      erro.id = `erro-${c.nome}`;

      bloco.append(rotulo, controle);
      if (c.dica) { const d = document.createElement('span'); d.className = 'dica'; d.textContent = c.dica; bloco.append(d); }
      bloco.append(erro);
      grade.append(bloco);
    });
    fs.append(lg, grade);
    formulario.append(fs);
  });

  const acoes = document.createElement('div');
  acoes.className = 'acoes';
  acoes.innerHTML = '<button type="submit">Salvar</button><a class="botao sec" href="index.html">Cancelar</a>';
  formulario.append(acoes);
}

// ---------------------------------------------------------------- dependências entre campos (espelham as regras da API)

function ehBrasil(pais) {
  return !pais || pais.normalize('NFD').replace(/\p{M}/gu, '').trim().toLowerCase() === 'brasil';
}

function desabilitar(nomes, desab) {
  [].concat(nomes).forEach(n => { el(n).disabled = desab; if (desab) el(n).value = ''; });
}

function idadeEmAnos() {
  if (val('dataNascimento')) {
    const nasc = new Date(val('dataNascimento') + 'T00:00:00');
    const ref = val('dataNotificacao') ? new Date(val('dataNotificacao') + 'T00:00:00') : new Date();
    let anos = ref.getFullYear() - nasc.getFullYear();
    if (ref.getMonth() < nasc.getMonth() || (ref.getMonth() === nasc.getMonth() && ref.getDate() < nasc.getDate())) anos--;
    return anos < 0 ? null : anos;
  }
  if (val('idade') !== '' && val('unidadeIdade')) {
    const n = parseInt(val('idade'), 10);
    return val('unidadeIdade') === 'ANO' ? n : val('unidadeIdade') === 'MES' ? Math.floor(n / 12) : 0;
  }
  return null;
}

function atualizarDependencias() {
  // RN02 - idade só quando a data de nascimento é desconhecida
  desabilitar(['idade', 'unidadeIdade'], !!val('dataNascimento'));

  // RN02 - gestante: "Não se aplica" automático se não for feminino ou tiver menos de 7 anos
  const g = el('gestante');
  const anos = idadeEmAnos();
  const automatico = val('sexo') !== 'FEMININO' || (anos !== null && anos < 7);
  const estavaAutomatico = g.disabled;
  g.disabled = automatico;
  if (automatico) g.value = 'NAO_SE_APLICA';
  else if (estavaAutomatico) g.value = '';

  // RN03 - residente em outro país não informa UF/município
  const exterior = !!val('paisResidencia') && !ehBrasil(val('paisResidencia'));
  desabilitar(['ufResidencia', 'municipioResidencia', 'codigoIbgeMunicipioResidencia'], exterior);

  // Local provável de infecção: só em caso confirmado; se autóctone, a API copia a residência
  const confirmado = val('classificacaoFinal') === 'CONFIRMADO';
  desabilitar('casoAutoctone', !confirmado);
  const informar = confirmado && val('casoAutoctone') === 'NAO';
  desabilitar(['ufInfeccao', 'paisInfeccao', 'municipioInfeccao', 'codigoIbgeMunicipioInfeccao',
    'distritoInfeccao', 'bairroInfeccao'], !informar);

  // Data do óbito só quando a evolução é óbito
  desabilitar('dataObito', !val('evolucaoCaso').startsWith('OBITO'));
}

// ---------------------------------------------------------------- erros

function limparErro(nome) {
  const bloco = document.getElementById(`campo-${nome}`);
  if (bloco) bloco.classList.remove('invalido');
  const msg = document.getElementById(`erro-${nome}`);
  if (msg) msg.textContent = '';
}

function limparErros() {
  TODOS_CAMPOS.forEach(c => limparErro(c.nome));
  esconderFaixa(faixa);
}

function mostrarErrosDaApi(resposta) {
  const problema = resposta.dados || {};
  const erros = problema.errors || {};
  const rotulos = Object.fromEntries(TODOS_CAMPOS.map(c => [c.nome, c.rotulo]));
  const itens = [];
  let primeiro = null;

  Object.entries(erros).forEach(([campo, msg]) => {
    itens.push(`${rotulos[campo] || campo}: ${msg}`);
    const bloco = document.getElementById(`campo-${campo}`);
    if (bloco) {
      bloco.classList.add('invalido');
      document.getElementById(`erro-${campo}`).textContent = msg;
      primeiro = primeiro || el(campo);
    }
  });
  if (resposta.status === 409) {
    const bloco = document.getElementById('campo-numero');
    bloco.classList.add('invalido');
    document.getElementById('erro-numero').textContent = problema.detail || '';
    primeiro = el('numero');
  }
  mostrarFaixa(faixa, 'erro', problema.title || 'Não foi possível salvar', itens.length ? itens : [problema.detail || '']);
  window.scrollTo({ top: 0, behavior: 'smooth' });
  if (primeiro) primeiro.focus({ preventScroll: true });
}

// ---------------------------------------------------------------- leitura, envio e carga

function montarPayload() {
  const dados = {};
  TODOS_CAMPOS.forEach(c => {
    const v = val(c.nome);
    dados[c.nome] = v === '' ? null : c.tipo === 'number' ? parseInt(v, 10) : v;
  });
  return dados;
}

formulario.addEventListener('submit', async evento => {
  evento.preventDefault();
  limparErros();
  const payload = montarPayload();
  const resposta = idEdicao
    ? await chamarApi(`${API}/${idEdicao}`, { method: 'PUT', body: payload })
    : await chamarApi(API, { method: 'POST', body: payload });

  if (!resposta.ok) { mostrarErrosDaApi(resposta); return; }

  if (!idEdicao) {
    location.href = `cadastro.html?id=${resposta.dados.id}&salvo=1`;   // próximos salvamentos viram PUT
    return;
  }
  preencher(resposta.dados);
  mostrarFaixa(faixa, 'ok', 'Notificação atualizada com sucesso.');
  window.scrollTo({ top: 0, behavior: 'smooth' });
});

function preencher(dados) {
  TODOS_CAMPOS.forEach(c => { el(c.nome).value = dados[c.nome] ?? ''; });
  atualizarDependencias();
}

async function iniciar() {
  montarFormulario();
  atualizarDependencias();
  if (!idEdicao) return;

  document.getElementById('titulo').textContent = 'Alterar notificação';
  document.title = 'Alterar notificação - SINAN';
  const resposta = await chamarApi(`${API}/${idEdicao}`);
  if (!resposta.ok) {
    mostrarFaixa(faixa, 'erro', resposta.dados?.title || 'Erro', [resposta.dados?.detail || 'Não foi possível carregar a notificação.']);
    formulario.querySelectorAll('input, select, textarea, button').forEach(e => e.disabled = true);
    return;
  }
  preencher(resposta.dados);
  document.getElementById('titulo').textContent = `Alterar notificação nº ${resposta.dados.numero}`;
  if (params.get('salvo')) mostrarFaixa(faixa, 'ok', 'Notificação cadastrada com sucesso.');
}

iniciar();
