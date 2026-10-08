package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.Prioridade;
import io.github.ricardsgon.helpdeskapi.dto.Status;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChamadoService {
    private final List<Chamado> chamados = new ArrayList<>(List.of(
            Chamado.builder().id(1L).titulo("Impressora não funciona").descricao("Impressora não está conectando na rede").prioridade(Prioridade.valueOf("ALTA")).status(Status.valueOf("ANDAMENTO")).build(),
            Chamado.builder().id(2L).titulo("Computador não liga").descricao("Computador não está ligando").prioridade(Prioridade.valueOf("BAIXA")).status(Status.valueOf("ANDAMENTO")).build()
    ));

    public List<Chamado> listar() {
        return chamados;
    }
    public Chamado buscarPorId(Long id) {
        return chamados.stream()
                .filter(chamado -> chamado.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ChamadoNotFoundException(id));
    }

    public Chamado criar(ChamadoRequestDTO chamadoRequestDTO) {
        Long novoId = chamados.stream()
                .mapToLong(Chamado::getId)
                .max()
                .orElse(0L) + 1;

            Chamado chamado = Chamado.builder()
                    .id(novoId)
                    .titulo(chamadoRequestDTO.getTitulo())
                    .descricao(chamadoRequestDTO.getDescricao())
                    .prioridade(chamadoRequestDTO.getPrioridade())
                    .status(chamadoRequestDTO.getStatus())
                    .build();
            chamados.add(chamado);
            return chamado;
    }

    public Chamado atualizar(Long id, ChamadoRequestDTO chamadoRequestDTO) {
        Chamado chamado = buscarPorId(id);
        chamado.setTitulo(chamadoRequestDTO.getTitulo());
        chamado.setDescricao(chamadoRequestDTO.getDescricao());
        chamado.setStatus(chamadoRequestDTO.getStatus());
        chamado.setPrioridade(chamadoRequestDTO.getPrioridade());

        return chamado;

    }

    public void deletar(Long id) {
        Chamado chamado = buscarPorId(id);
        chamados.remove(chamado);
    }
}
