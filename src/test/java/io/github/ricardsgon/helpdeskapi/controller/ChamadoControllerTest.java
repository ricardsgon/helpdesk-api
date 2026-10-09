
package io.github.ricardsgon.helpdeskapi.controller;

import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.dto.Prioridade;
import io.github.ricardsgon.helpdeskapi.dto.Status;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import io.github.ricardsgon.helpdeskapi.service.ChamadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(ChamadoController.class)
class ChamadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChamadoService chamadoService;

    private static final String JSON_VALIDO = """
            {
                "titulo": "Teclado não funciona",
                "descricao": "Teclado do laboratório não funciona",
                "prioridade": "ALTA",
                "status": "ABERTO"
            }
            """;

    private static final String JSON_INVALIDO = """
            {
                "titulo": "",
                "descricao": "",
                "prioridade": "ALTA",
                "status": "ABERTO"
            }
            """;

    private ChamadoResponseDTO criarResponseDTO() {
        return new ChamadoResponseDTO(
                10L,
                "Teclado não funciona",
                "Teclado do laboratório não funciona",
                Prioridade.ALTA,
                Status.ABERTO
        );
    }

    // GET /chamados

    @Test
    void deveListarChamados() throws Exception {
        given(chamadoService.listar())
                .willReturn(List.of(criarResponseDTO()));

        mockMvc.perform(get("/chamados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].titulo")
                        .value("Teclado não funciona"))
                .andExpect(jsonPath("$[0].prioridade").value("ALTA"))
                .andExpect(jsonPath("$[0].status").value("ABERTO"));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemChamados()
            throws Exception {

        given(chamadoService.listar())
                .willReturn(List.of());

        mockMvc.perform(get("/chamados"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    // GET /chamados/{id}

    @Test
    void deveBuscarChamadoPorId() throws Exception {
        given(chamadoService.buscarPorId(10L))
                .willReturn(criarResponseDTO());

        mockMvc.perform(get("/chamados/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.titulo")
                        .value("Teclado não funciona"))
                .andExpect(jsonPath("$.descricao")
                        .value("Teclado do laboratório não funciona"))
                .andExpect(jsonPath("$.prioridade").value("ALTA"))
                .andExpect(jsonPath("$.status").value("ABERTO"));
    }

    @Test
    void deveRetornar404AoBuscarChamadoInexistente()
            throws Exception {

        given(chamadoService.buscarPorId(999L))
                .willThrow(new ChamadoNotFoundException(999L));

        mockMvc.perform(get("/chamados/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Chamado não encontrado: 999"));
    }

    // POST /chamados

    @Test
    void deveCriarChamado() throws Exception {
        given(chamadoService.criar(any(ChamadoRequestDTO.class)))
                .willReturn(criarResponseDTO());

        mockMvc.perform(post("/chamados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.titulo")
                        .value("Teclado não funciona"))
                .andExpect(jsonPath("$.prioridade").value("ALTA"))
                .andExpect(jsonPath("$.status").value("ABERTO"));
    }

    @Test
    void deveRetornar400AoCriarChamadoComDadosInvalidos()
            throws Exception {

        mockMvc.perform(post("/chamados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_INVALIDO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message",
                        containsString("Título não pode estar vazio")));

        verifyNoInteractions(chamadoService);
    }

    // PUT /chamados/{id}

    @Test
    void deveAtualizarChamado() throws Exception {
        given(chamadoService.atualizar(
                eq(10L),
                any(ChamadoRequestDTO.class)
        )).willReturn(criarResponseDTO());

        mockMvc.perform(put("/chamados/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_VALIDO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.titulo")
                        .value("Teclado não funciona"))
                .andExpect(jsonPath("$.status").value("ABERTO"));
    }

    @Test
    void deveRetornar404AoAtualizarChamadoInexistente()
            throws Exception {

        given(chamadoService.atualizar(
                eq(999L),
                any(ChamadoRequestDTO.class)
        )).willThrow(new ChamadoNotFoundException(999L));

        mockMvc.perform(put("/chamados/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_VALIDO))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Chamado não encontrado: 999"));
    }

    @Test
    void deveRetornar400AoAtualizarComDadosInvalidos()
            throws Exception {

        mockMvc.perform(put("/chamados/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_INVALIDO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message",
                        containsString("Título não pode estar vazio")));

        verifyNoInteractions(chamadoService);
    }

    // DELETE /chamados/{id}

    @Test
    void deveDeletarChamado() throws Exception {
        mockMvc.perform(delete("/chamados/10"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void deveRetornar404AoDeletarChamadoInexistente()
            throws Exception {

        doThrow(new ChamadoNotFoundException(999L))
                .when(chamadoService)
                .deletar(999L);

        mockMvc.perform(delete("/chamados/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Chamado não encontrado: 999"));
    }
}