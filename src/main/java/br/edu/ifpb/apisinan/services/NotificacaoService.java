package br.edu.ifpb.apisinan.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.apisinan.dto.NotificacaoFiltro;
import br.edu.ifpb.apisinan.dto.NotificacaoMapper;
import br.edu.ifpb.apisinan.dto.NotificacaoRequest;
import br.edu.ifpb.apisinan.dto.NotificacaoResponse;
import br.edu.ifpb.apisinan.dto.NotificacaoResumo;
import br.edu.ifpb.apisinan.entities.Notificacao;
import br.edu.ifpb.apisinan.exceptions.ConflitoException;
import br.edu.ifpb.apisinan.exceptions.NotificacaoNaoEncontradaException;
import br.edu.ifpb.apisinan.exceptions.RegraNegocioException;
import br.edu.ifpb.apisinan.regras.NotificacaoRegras;
import br.edu.ifpb.apisinan.repositories.NotificacaoRepository;
import br.edu.ifpb.apisinan.repositories.NotificacaoSpecs;

@Service
public class NotificacaoService {

    private static final Sort ORDEM_PADRAO = Sort.by(Sort.Order.desc("dataNotificacao"), Sort.Order.desc("id"));
    /** Na visão de duplicadas, deixa as notificações parecidas lado a lado. */
    private static final Sort ORDEM_DUPLICADAS =
            Sort.by("agravo", "nomePaciente", "dataNascimento", "dataNotificacao", "id");

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResumo> listar(NotificacaoFiltro filtro) {
        Specification<Notificacao> spec = NotificacaoSpecs.comFiltro(filtro);
        Sort ordem = ORDEM_PADRAO;

        // RN01: o filtro de duplicidade se combina com os demais (E lógico).
        if (Boolean.TRUE.equals(filtro.duplicadas())) {
            spec = spec.and(NotificacaoSpecs.comIds(repository.findIdsDuplicados()));
            ordem = ORDEM_DUPLICADAS;
        }
        return repository.findAll(spec, ordem).stream().map(NotificacaoMapper::toResumo).toList();
    }

    @Transactional(readOnly = true)
    public NotificacaoResponse buscar(Long id) {
        return NotificacaoMapper.toResponse(obter(id));
    }

    @Transactional
    public NotificacaoResponse criar(NotificacaoRequest dados) {
        Notificacao nova = prepararEValidar(dados);
        if (repository.existsByNumero(nova.getNumero())) {
            throw new ConflitoException(mensagemNumeroRepetido(nova.getNumero()));
        }
        return NotificacaoMapper.toResponse(repository.save(nova));
    }

    /** PUT: substitui toda a ficha pelo conteúdo enviado, mantendo o id da URL. */
    @Transactional
    public NotificacaoResponse atualizar(Long id, NotificacaoRequest dados) {
        obter(id);
        Notificacao atualizada = prepararEValidar(dados);
        if (repository.existsByNumeroAndIdNot(atualizada.getNumero(), id)) {
            throw new ConflitoException(mensagemNumeroRepetido(atualizada.getNumero()));
        }
        atualizada.setId(id);
        return NotificacaoMapper.toResponse(repository.save(atualizada));
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(obter(id));
    }

    private Notificacao obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotificacaoNaoEncontradaException(id));
    }

    private Notificacao prepararEValidar(NotificacaoRequest dados) {
        Notificacao notificacao = NotificacaoMapper.toEntity(dados);
        NotificacaoRegras.normalizar(notificacao);
        Map<String, String> erros = NotificacaoRegras.validar(notificacao);
        if (!erros.isEmpty()) {
            throw new RegraNegocioException(erros);
        }
        return notificacao;
    }

    private String mensagemNumeroRepetido(String numero) {
        return "Já existe uma notificação com o número '" + numero + "'.";
    }
}

