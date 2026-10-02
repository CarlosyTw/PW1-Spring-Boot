package br.edu.ifpb.apisinan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifpb.apisinan.entities.Notificacao;

public interface NotificacaoRepository
        extends JpaRepository<Notificacao, Long>, JpaSpecificationExecutor<Notificacao> {

    boolean existsByNumero(String numero);

    boolean existsByNumeroAndIdNot(String numero, Long id);

    /**
     * RN01 - ids das notificações que são duplicadas de ao menos outra notificação.
     *
     * <p>Duas notificações são duplicadas quando, ao mesmo tempo: têm o mesmo agravo, o mesmo
     * nome de paciente, a mesma data de nascimento, o mesmo nome da mãe e datas de notificação
     * com diferença de até 3 dias (inclusive). Textos são comparados sem diferenciar maiúsculas
     * de minúsculas e ignorando espaços nas pontas e repetidos. Notificações com algum desses
     * campos em branco ficam de fora (a igualdade com NULL nunca é verdadeira, e os textos em
     * branco são excluídos no WHERE). SQL nativo do PostgreSQL: {@code data - data} resulta em
     * número de dias.
     */
    @Query(value = """
            select distinct a.id
            from notificacao a
            join notificacao b
              on a.id <> b.id
             and lower(regexp_replace(trim(a.agravo), '\\s+', ' ', 'g'))
               = lower(regexp_replace(trim(b.agravo), '\\s+', ' ', 'g'))
             and lower(regexp_replace(trim(a.nome_paciente), '\\s+', ' ', 'g'))
               = lower(regexp_replace(trim(b.nome_paciente), '\\s+', ' ', 'g'))
             and lower(regexp_replace(trim(a.nome_mae), '\\s+', ' ', 'g'))
               = lower(regexp_replace(trim(b.nome_mae), '\\s+', ' ', 'g'))
             and a.data_nascimento = b.data_nascimento
             and abs(a.data_notificacao - b.data_notificacao) <= 3
            where trim(coalesce(a.agravo, '')) <> ''
              and trim(coalesce(a.nome_paciente, '')) <> ''
              and trim(coalesce(a.nome_mae, '')) <> ''
            """, nativeQuery = true)
    List<Long> findIdsDuplicados();
}

