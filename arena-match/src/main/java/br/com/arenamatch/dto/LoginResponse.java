package br.com.arenamatch.dto;

import java.time.LocalDate;

public record LoginResponse(Long representanteId, String nomeRepresentante, Long timeId) {}
