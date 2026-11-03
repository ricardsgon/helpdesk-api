package io.github.ricardsgon.helpdeskapi.dto;

import io.github.ricardsgon.helpdeskapi.database.model.Prioridade;
import io.github.ricardsgon.helpdeskapi.database.model.Status;

public record ChamadoResponseDTO(Long id, String titulo, String descricao, Prioridade prioridade, Status status) {


}
