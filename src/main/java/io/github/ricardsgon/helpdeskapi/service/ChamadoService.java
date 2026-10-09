package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.dto.Prioridade;
import io.github.ricardsgon.helpdeskapi.dto.Status;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChamadoService {

    private final List<Chamado> chamados = new ArrayList<>(List.of(
            Chamado.builder().id(1L).titulo("Impressora não funciona").descricao("Impressora não está conectando na rede").prioridade(Prioridade.ALTA).status(Status.ANDAMENTO).build(),
            Chamado.builder().id(2L).titulo("Computador não liga").descricao("Computador não está ligando").prioridade(Prioridade.BAIXA).status(Status.ANDAMENTO).build()
    ));

    public List<ChamadoResponseDTO> listar() {
        return chamados.stream()
                .map(this::toChamadoResponseDTO)
                .toList();
    }

    public ChamadoResponseDTO buscarPorId(Long id) {
        Chamado chamado = encontrarEntidadePorId(id);
        return toChamadoResponseDTO(chamado);
    }

    public ChamadoResponseDTO criar(ChamadoRequestDTO chamadoRequestDTO) {
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
        return toChamadoResponseDTO(chamado);
    }

    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO chamadoRequestDTO) {
        Chamado chamado = encontrarEntidadePorId(id);
        chamado.setTitulo(chamadoRequestDTO.getTitulo());
        chamado.setDescricao(chamadoRequestDTO.getDescricao());
        chamado.setStatus(chamadoRequestDTO.getStatus());
        chamado.setPrioridade(chamadoRequestDTO.getPrioridade());

        return toChamadoResponseDTO(chamado);
    }

    public void deletar(Long id) {
        Chamado chamado = encontrarEntidadePorId(id);
        chamados.remove(chamado);
    }

    private Chamado encontrarEntidadePorId(Long id) {
        return chamados.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ChamadoNotFoundException(id));
    }

    private ChamadoResponseDTO toChamadoResponseDTO(Chamado chamado) {
        return new ChamadoResponseDTO(
                chamado.getId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getPrioridade(),
                chamado.getStatus()
        );
    }
}