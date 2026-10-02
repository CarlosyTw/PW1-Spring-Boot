package br.edu.ifpb.apisinan.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifpb.apisinan.dto.NotificacaoFiltro;
import br.edu.ifpb.apisinan.entities.Notificacao;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public final class NotificacaoSpecs {

    private NotificacaoSpecs() { }

    public static Specification<Notificacao> comFiltro(NotificacaoFiltro f) {
        return (root, query, cb) -> {
            List<Predicate> filtros = new ArrayList<>();

            contem(cb, filtros, root.<String>get("numero"), f.numero());
            contem(cb, filtros, root.<String>get("agravo"), f.agravo());
            contem(cb, filtros, root.<String>get("nomePaciente"), f.nomePaciente());
            contem(cb, filtros, root.<String>get("nomeMae"), f.nomeMae());
            contem(cb, filtros, root.<String>get("municipioResidencia"), f.municipioResidencia());

            if (preenchido(f.ufResidencia())) {
                filtros.add(cb.equal(cb.upper(root.<String>get("ufResidencia")), f.ufResidencia().trim().toUpperCase()));
            }
            if (f.dataNotificacaoInicio() != null) {
                filtros.add(cb.greaterThanOrEqualTo(root.<java.time.LocalDate>get("dataNotificacao"), f.dataNotificacaoInicio()));
            }
            if (f.dataNotificacaoFim() != null) {
                filtros.add(cb.lessThanOrEqualTo(root.<java.time.LocalDate>get("dataNotificacao"), f.dataNotificacaoFim()));
            }
            if (f.sexo() != null) {
                filtros.add(cb.equal(root.get("sexo"), f.sexo()));
            }
            if (f.classificacaoFinal() != null) {
                filtros.add(cb.equal(root.get("classificacaoFinal"), f.classificacaoFinal()));
            }
            if (f.evolucaoCaso() != null) {
                filtros.add(cb.equal(root.get("evolucaoCaso"), f.evolucaoCaso()));
            }
            return cb.and(filtros.toArray(new Predicate[0]));
        };
    }

    public static Specification<Notificacao> comIds(List<Long> ids) {
        return (root, query, cb) -> ids.isEmpty() ? cb.disjunction() : root.get("id").in(ids);
    }

    private static void contem(CriteriaBuilder cb, List<Predicate> filtros, Expression<String> campo, String texto) {
        if (preenchido(texto)) {
            String padrao = "%" + escapar(texto.trim().toLowerCase()) + "%";
            filtros.add(cb.like(cb.lower(campo), padrao, '\\'));
        }
    }

    private static String escapar(String texto) {
        return texto.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }

    private static boolean preenchido(String texto) {
        return texto != null && !texto.isBlank();
    }
}

