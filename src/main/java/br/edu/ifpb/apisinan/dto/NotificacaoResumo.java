package br.edu.ifpb.apisinan.dto;

import java.time.LocalDate;

import br.edu.ifpb.apisinan.entities.enums.ClassificacaoFinal;
import br.edu.ifpb.apisinan.entities.enums.EvolucaoCaso;
import br.edu.ifpb.apisinan.entities.enums.Sexo;

public record NotificacaoResumo(
        Long id,
        String numero,
        String agravo,
        LocalDate dataNotificacao,
        String nomePaciente,
        LocalDate dataNascimento,
        String nomeMae,
        Sexo sexo,
        String municipioResidencia,
        String ufResidencia,
        ClassificacaoFinal classificacaoFinal,
        EvolucaoCaso evolucaoCaso) { }

