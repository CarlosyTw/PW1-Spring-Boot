// Funções compartilhadas pelas páginas: chamadas à API e pequenos formatadores.
const API = '/notificacao';

/**
 * Chama a API e devolve { ok, status, dados }. Respostas de erro vêm em Problem Details
 * (RFC 9457); 204 não tem corpo.
 */
async function chamarApi(url, opcoes = {}) {
  const config = { method: 'GET', headers: {}, ...opcoes };
  if (config.body !== undefined) {
    config.headers['Content-Type'] = 'application/json';
    config.body = JSON.stringify(config.body);
  }
  let resposta;
  try {
    resposta = await fetch(url, config);
  } catch (e) {
    return { ok: false, status: 0, dados: { title: 'Sem conexão', detail: 'Não foi possível falar com o servidor.' } };
  }
  let dados = null;
  if (resposta.status !== 204) {
    const texto = await resposta.text();
    try { dados = texto ? JSON.parse(texto) : null; } catch (e) { dados = { title: 'Resposta inesperada', detail: texto.slice(0, 200) }; }
  }
  return { ok: resposta.ok, status: resposta.status, dados };
}

/** "2026-03-12" -> "12/03/2026" */
function formatarData(iso) {
  if (!iso) return '';
  const [a, m, d] = iso.split('-');
  return `${d}/${m}/${a}`;
}

/** Mostra uma faixa de mensagem dentro do elemento informado (substitui a anterior). */
function mostrarFaixa(elemento, tipo, titulo, itens = []) {
  elemento.className = `faixa ${tipo}`;
  elemento.hidden = false;
  elemento.replaceChildren();
  const t = document.createElement('strong');
  t.textContent = titulo;
  elemento.append(t);
  if (itens.length) {
    const ul = document.createElement('ul');
    itens.forEach(i => { const li = document.createElement('li'); li.textContent = i; ul.append(li); });
    elemento.append(ul);
  }
}

function esconderFaixa(elemento) {
  elemento.hidden = true;
  elemento.replaceChildren();
}
