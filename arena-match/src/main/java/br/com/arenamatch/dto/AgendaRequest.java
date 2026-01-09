package br.com.arenamatch.dto;

public record AgendaRequest(
        String categoria,   // "ESPORTE"|"VETERANO"|"MASTER"
        String diaSemana,   // "SEG"|"TER"|...|"DOM"
        String horaInicio,  // "08:00"
        String horaFim      // "10:00"
) {}
