package io.github.ricardsgon.helpdeskapi.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChamadoRequestDTO {


    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
    @NotNull
    private Prioridade prioridade;
    @NotNull
    private Status status;
}
