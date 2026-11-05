package io.github.ricardsgon.helpdeskapi.controller;

import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.service.ChamadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@Tag(name = "Chamados", description = "Abertura e acompanhamento de chamados de suporte")
public class ChamadoController {
    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @Operation(summary = "Lista todos os chamados")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChamadoResponseDTO> getChamados() {
        return chamadoService.listar();
    }

    @Operation(summary = "Busca um chamado pelo ID")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChamadoResponseDTO getChamadoById(@PathVariable Long id) {
        return chamadoService.buscarPorId(id);
    }

    @Operation(summary = "Abre um novo chamado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponseDTO createChamado(@Valid @RequestBody ChamadoRequestDTO chamadoRequestDTO) {
        return chamadoService.criar(chamadoRequestDTO);
    }

    @Operation(summary = "Atualiza um chamado existente")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    @PutMapping("/{id}")
    public ChamadoResponseDTO atualizarChamado(@Valid @RequestBody ChamadoRequestDTO chamadoRequestDTO, @PathVariable Long id) {
        return chamadoService.atualizar(id, chamadoRequestDTO);
    }

    @Operation(summary = "Remove um chamado")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChamado(@PathVariable Long id) {
        chamadoService.deletar(id);
    }
}