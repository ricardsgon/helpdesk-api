
package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.dto.Prioridade;
import io.github.ricardsgon.helpdeskapi.dto.Status;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChamadoServiceTest {

    private ChamadoService chamadoService;

    @BeforeEach
    void setUp() {
        chamadoService = new ChamadoService();
    }

    @Test
    void deveListarChamados() {
        var chamados = chamadoService.listar();

        assertNotNull(chamados);
    }

    @Test
    void deveLancarExcecaoQuandoChamadoNaoExiste() {
        assertThrows(
                ChamadoNotFoundException.class,
                () -> chamadoService.buscarPorId(999L)
        );
    }

    @Test
    void deveCriarChamado() {
        ChamadoRequestDTO request = criarRequest(
                "Teclado não funciona",
                "Teclado do laboratório não está funcionando",
                Prioridade.ALTA,
                Status.ABERTO
        );

        int quantidadeInicial = chamadoService.listar().size();

        ChamadoResponseDTO response = chamadoService.criar(request);

        assertNotNull(response.id());
        assertEquals("Teclado não funciona", response.titulo());
        assertEquals(
                "Teclado do laboratório não está funcionando",
                response.descricao()
        );
        assertEquals(Prioridade.ALTA, response.prioridade());
        assertEquals(Status.ABERTO, response.status());
        assertEquals(quantidadeInicial + 1, chamadoService.listar().size());

        ChamadoResponseDTO chamadoSalvo =
                chamadoService.buscarPorId(response.id());

        assertEquals(response.titulo(), chamadoSalvo.titulo());
    }

    @Test
    void deveAtualizarChamado() {
        Long id = chamadoService.listar().getFirst().id();

        ChamadoRequestDTO request = criarRequest(
                "Impressora atualizada",
                "Impressora voltou a apresentar problemas",
                Prioridade.BAIXA,
                Status.ENCERRADO
        );

        ChamadoResponseDTO response =
                chamadoService.atualizar(id, request);

        assertEquals(id, response.id());
        assertEquals("Impressora atualizada", response.titulo());
        assertEquals(
                "Impressora voltou a apresentar problemas",
                response.descricao()
        );
        assertEquals(Prioridade.BAIXA, response.prioridade());
        assertEquals(Status.ENCERRADO, response.status());

        ChamadoResponseDTO chamadoAtualizado =
                chamadoService.buscarPorId(id);

        assertEquals("Impressora atualizada", chamadoAtualizado.titulo());
        assertEquals(Status.ENCERRADO, chamadoAtualizado.status());
    }

    @Test
    void deveRemoverChamado() {
        Long id = chamadoService.listar().getFirst().id();
        int quantidadeInicial = chamadoService.listar().size();

        chamadoService.deletar(id);

        assertEquals(quantidadeInicial - 1, chamadoService.listar().size());

        assertThrows(
                ChamadoNotFoundException.class,
                () -> chamadoService.buscarPorId(id)
        );
    }

    @Test
    void deveLancarExcecaoAoAtualizarChamadoInexistente() {
        ChamadoRequestDTO request = criarRequest(
                "Chamado inexistente",
                "Teste de atualização",
                Prioridade.ALTA,
                Status.ABERTO
        );

        assertThrows(
                ChamadoNotFoundException.class,
                () -> chamadoService.atualizar(999L, request)
        );
    }

    @Test
    void deveLancarExcecaoAoDeletarChamadoInexistente() {
        assertThrows(
                ChamadoNotFoundException.class,
                () -> chamadoService.deletar(999L)
        );
    }

    private ChamadoRequestDTO criarRequest(
            String titulo,
            String descricao,
            Prioridade prioridade,
            Status status
    ) {
        ChamadoRequestDTO request = new ChamadoRequestDTO();
        request.setTitulo(titulo);
        request.setDescricao(descricao);
        request.setPrioridade(prioridade);
        request.setStatus(status);

        return request;
    }
}