package io.github.ricardsgon.helpdeskapi.database.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Chamados")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @Enumerated(EnumType.STRING)
    private Status status;

}
