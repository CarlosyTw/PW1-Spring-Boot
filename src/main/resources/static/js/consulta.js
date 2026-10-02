// Página de consulta: filtros -> GET /notificacao?... -> tabela com colunas de alterar e excluir.

const formFiltros = document.getElementById('filtros');
const corpo = document.getElementById('corpo');
const faixaConsulta = document.getElementById('faixa');
const contagem = document.getElementById('contagem');

const SEM_FILTRO = '';
// Filtros de opção usam os mesmos textos da ficha (js/campos.js).
['sexo', 'classificacaoFinal', 'evolucaoCaso'].forEach(nome => {
  const select = document.getElementById(nome);
  select.append(new Option('Todos', SEM_FILTRO));
  Object.entries(OPCOES[nome]).forEach(([valor, texto]) => select.append(new Option(texto, valor)));
});

function montarConsulta() {
  const q = new URLSearchParams();
  new FormData(formFiltros).forEach((valor, chave) => {
    const v = String(valor).trim();
    if (v !== '') q.append(chave, chave === 'ufResidencia' ? v.toUpperCase() : v);
  });
  return q.toString();
}

function celula(linha, texto) {
  const td = document.createElement('td');
  td.textContent = texto ?? '';
  linha.append(td);
  return td;
}

function rotulo(grupo, valor) {
  return valor ? (OPCOES[grupo][valor] || valor) : '';
}

function desenharLinha(n) {
  const tr = document.createElement('tr');
  celula(tr, n.numero);
  celula(tr, n.agravo);
  celula(tr, formatarData(n.dataNotificacao));
  celula(tr, n.nomePaciente);
  celula(tr, formatarData(n.dataNascimento));
  celula(tr, n.nomeMae);
  celula(tr, rotulo('sexo', n.sexo));
  celula(tr, [n.municipioResidencia, n.ufResidencia].filter(Boolean).join(' / '));
  celula(tr, rotulo('classificacaoFinal', n.classificacaoFinal));
  celula(tr, rotulo('evolucaoCaso', n.evolucaoCaso));

  const alterar = document.createElement('a');
  alterar.className = 'botao sec peq';
  alterar.href = `cadastro.html?id=${n.id}`;
  alterar.textContent = 'Alterar';
  celula(tr, '').append(alterar);

  const excluir = document.createElement('button');
  excluir.type = 'button';
  excluir.className = 'perigo';
  excluir.textContent = 'Excluir';
  excluir.addEventListener('click', () => excluirNotificacao(n));
  celula(tr, '').append(excluir);
  return tr;
}

async function consultar() {
  esconderFaixa(faixaConsulta);
  const consulta = montarConsulta();
  const resposta = await chamarApi(consulta ? `${API}?${consulta}` : API);
  corpo.replaceChildren();

  if (!resposta.ok) {
    const erros = Object.values(resposta.dados?.errors || {});
    mostrarFaixa(faixaConsulta, 'erro', resposta.dados?.title || 'Erro na consulta',
      erros.length ? erros : [resposta.dados?.detail || '']);
    contagem.textContent = '';
    return;
  }
  const lista = resposta.dados;
  contagem.textContent = lista.length === 1 ? '1 notificação encontrada.' : `${lista.length} notificações encontradas.`;
  if (document.getElementById('duplicadas').checked) {
    mostrarFaixa(faixaConsulta, 'aviso', 'Exibindo apenas notificações possivelmente duplicadas',
      ['Mesmo agravo, paciente, data de nascimento e nome da mãe, com datas de notificação até 3 dias de diferença. ' +
       'Use "Alterar" ou "Excluir" para resolver.']);
  }
  if (!lista.length) {
    const tr = document.createElement('tr');
    const td = celula(tr, 'Nenhuma notificação encontrada.');
    td.colSpan = 12;
    td.className = 'vazio';
    corpo.append(tr);
    return;
  }
  lista.forEach(n => corpo.append(desenharLinha(n)));
}

async function excluirNotificacao(n) {
  if (!confirm(`Excluir a notificação nº ${n.numero} (${n.nomePaciente})? Esta ação não pode ser desfeita.`)) return;
  const resposta = await chamarApi(`${API}/${n.id}`, { method: 'DELETE' });
  if (!resposta.ok && resposta.status !== 404) {
    mostrarFaixa(faixaConsulta, 'erro', resposta.dados?.title || 'Não foi possível excluir', [resposta.dados?.detail || '']);
    return;
  }
  await consultar();
}

formFiltros.addEventListener('submit', e => { e.preventDefault(); consultar(); });
document.getElementById('limpar').addEventListener('click', () => { formFiltros.reset(); consultar(); });

consultar();
