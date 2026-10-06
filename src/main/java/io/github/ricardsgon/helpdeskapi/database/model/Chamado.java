package io.github.ricardsgon.helpdeskapi.database.model;

import lombok.Getter;

@Getter
public class Chamado {
    private final Long id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;

    public Chamado(Long id, String titulo, String descricao, String prioridade, String status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
    }
}
