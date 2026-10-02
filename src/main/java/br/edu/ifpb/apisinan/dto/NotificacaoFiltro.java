package br.edu.ifpb.apisinan.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.edu.ifpb.apisinan.entities.enums.ClassificacaoFinal;
import br.edu.ifpb.apisinan.entities.enums.EvolucaoCaso;
import br.edu.ifpb.apisinan.entities.enums.Sexo;

public record NotificacaoFiltro(
        String numero,
        String agravo,
        String nomePaciente,
        String nomeMae,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNotificacaoInicio,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNotificacaoFim,
        Sexo sexo,
        String ufResidencia,
        String municipioResidencia,
        ClassificacaoFinal classificacaoFinal,
        EvolucaoCaso evolucaoCaso,
        Boolean duplicadas) { }

