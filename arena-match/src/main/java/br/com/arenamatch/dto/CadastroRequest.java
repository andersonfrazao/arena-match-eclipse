package br.com.arenamatch.dto;

import java.util.List;

public record CadastroRequest(
        RepresentanteCadastroRequest representante,
        TimeCadastroRequest time,
        List<String> categorias,                 // ["ESPORTE","VETERANO","MASTER"]
        List<AgendaRequest> agendas              // linhas da agenda
) {}
