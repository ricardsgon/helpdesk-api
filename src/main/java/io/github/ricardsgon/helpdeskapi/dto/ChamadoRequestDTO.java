package io.github.ricardsgon.helpdeskapi.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChamadoRequestDTO {


    @NotBlank(message = "Título não pode estar vazio")
    private String titulo;
    @NotBlank(message = "Descrição não pode estar vazia")
    private String descricao;
    @NotNull(message = "Prioridade não pode ser nulo")
    private Prioridade prioridade;
    @NotNull(message = "Status não pode ser nulo")
    private Status status;
}
