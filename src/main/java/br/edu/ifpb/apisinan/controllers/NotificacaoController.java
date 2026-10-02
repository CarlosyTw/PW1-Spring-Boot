package br.edu.ifpb.apisinan.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.apisinan.dto.NotificacaoFiltro;
import br.edu.ifpb.apisinan.dto.NotificacaoRequest;
import br.edu.ifpb.apisinan.dto.NotificacaoResponse;
import br.edu.ifpb.apisinan.dto.NotificacaoResumo;
import br.edu.ifpb.apisinan.services.NotificacaoService;
import jakarta.validation.Valid;

/** CRUD de notificações. O controller só traduz HTTP; a regra de negócio fica no service. */
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    /** GET /notificacao?[filtros]&duplicadas=true  ->  200 com a lista (vazia quando nada casa). */
    @GetMapping
    public List<NotificacaoResumo> listar(@ModelAttribute NotificacaoFiltro filtro) {
        return service.listar(filtro);
    }

    /** 200, ou 404 quando o id não existe. */
    @GetMapping("/{id}")
    public NotificacaoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** 201 com cabeçalho Location apontando para o novo recurso. */
    @PostMapping
    public ResponseEntity<NotificacaoResponse> criar(@Valid @RequestBody NotificacaoRequest dados) {
        NotificacaoResponse criada = service.criar(dados);
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(local).body(criada);
    }

    /** Substituição completa da ficha: 200, ou 404 quando o id não existe. */
    @PutMapping("/{id}")
    public NotificacaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody NotificacaoRequest dados) {
        return service.atualizar(id, dados);
    }

    /** 204 sem corpo, ou 404 quando o id não existe. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}

