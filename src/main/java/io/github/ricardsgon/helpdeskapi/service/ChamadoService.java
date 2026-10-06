package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChamadoService {
    private final List<Chamado> chamados = new ArrayList<>(List.of(
            new Chamado(1L, "Impressora não funciona", "Impressora não está conectando na rede", "ALTA", "Pendente"),
            new Chamado(2L, "Computador não liga", "Computador não está ligando", "BAIXA", "Pendente")
        )
    );

    public List<Chamado> listar() {
        return chamados;
    }
    public Chamado buscarPorId(Long id) {
        return chamados.stream()
                .filter(chamado -> chamado.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
