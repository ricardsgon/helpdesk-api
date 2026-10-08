package io.github.ricardsgon.helpdeskapi.controller;

import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChamadoResponseDTO> getChamados() {
        return chamadoService.listar();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoResponseDTO getChamadoById(@PathVariable Long id) {
        return chamadoService.buscarPorId(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponseDTO createChamado(@Valid @RequestBody ChamadoRequestDTO chamadoRequestDTO) {
        return chamadoService.criar(chamadoRequestDTO);
    }

    @PutMapping("/{id}")
    public ChamadoResponseDTO atualizarChamado(@Valid @RequestBody ChamadoRequestDTO chamadoRequestDTO, @PathVariable Long id) {
        return chamadoService.atualizar(id, chamadoRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChamado(@PathVariable Long id) {
        chamadoService.deletar(id);
    }
}
