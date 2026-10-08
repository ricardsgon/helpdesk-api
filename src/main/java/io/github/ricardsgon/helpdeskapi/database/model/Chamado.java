package io.github.ricardsgon.helpdeskapi.database.model;

import io.github.ricardsgon.helpdeskapi.dto.Prioridade;
import io.github.ricardsgon.helpdeskapi.dto.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Chamado {
    private final Long id;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Status status;

    public Chamado(Long id, String titulo, String descricao, Prioridade prioridade, Status status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
    }
}
