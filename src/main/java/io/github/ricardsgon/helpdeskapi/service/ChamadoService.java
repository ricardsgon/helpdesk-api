package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import io.github.ricardsgon.helpdeskapi.database.repository.ChamadoRepository;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    public List<ChamadoResponseDTO> listar() {
        return chamadoRepository.findAll().stream()
                .map(this::toChamadoResponseDTO)
                .toList();
    }

    public ChamadoResponseDTO buscarPorId(Long id) {
        return toChamadoResponseDTO(encontrarEntidadePorId(id));
    }

    public ChamadoResponseDTO criar(ChamadoRequestDTO dto) {
        Chamado chamado = Chamado.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .prioridade(dto.getPrioridade())
                .status(dto.getStatus())
                .build();

        return toChamadoResponseDTO(chamadoRepository.save(chamado));
    }

    public ChamadoResponseDTO atualizar(Long id, ChamadoRequestDTO dto) {
        Chamado chamado = encontrarEntidadePorId(id);
        chamado.setTitulo(dto.getTitulo());
        chamado.setDescricao(dto.getDescricao());
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());

        return toChamadoResponseDTO(chamadoRepository.save(chamado));
    }

    public void deletar(Long id) {
        chamadoRepository.delete(encontrarEntidadePorId(id));
    }

    private Chamado encontrarEntidadePorId(Long id) {
        return chamadoRepository.findById(id)
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