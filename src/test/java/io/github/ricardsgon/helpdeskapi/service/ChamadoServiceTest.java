package io.github.ricardsgon.helpdeskapi.service;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import io.github.ricardsgon.helpdeskapi.database.model.Prioridade;
import io.github.ricardsgon.helpdeskapi.database.model.Status;
import io.github.ricardsgon.helpdeskapi.database.repository.ChamadoRepository;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoRequestDTO;
import io.github.ricardsgon.helpdeskapi.dto.ChamadoResponseDTO;
import io.github.ricardsgon.helpdeskapi.exception.ChamadoNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock
    private ChamadoRepository chamadoRepository;

    @InjectMocks
    private ChamadoService chamadoService;

    @Test
    void deveListarChamados() {
        given(chamadoRepository.findAll()).willReturn(List.of(criarChamado()));

        List<ChamadoResponseDTO> resultado = chamadoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Teclado não funciona", resultado.getFirst().titulo());
    }

    @Test
    void deveBuscarChamadoPorId() {
        given(chamadoRepository.findById(1L)).willReturn(Optional.of(criarChamado()));

        ChamadoResponseDTO resultado = chamadoService.buscarPorId(1L);

        assertEquals(1L, resultado.id());
        assertEquals(Prioridade.ALTA, resultado.prioridade());
    }

    @Test
    void deveLancarExcecaoQuandoChamadoNaoExiste() {
        given(chamadoRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(ChamadoNotFoundException.class,
                () -> chamadoService.buscarPorId(999L));
    }

    @Test
    void deveCriarChamado() {
        given(chamadoRepository.save(any(Chamado.class))).willAnswer(invocation -> {
            Chamado salvo = invocation.getArgument(0);
            salvo.setId(10L);
            return salvo;
        });

        ChamadoResponseDTO resposta = chamadoService.criar(
                criarRequest("Mouse quebrado", "Mouse da sala 2", Prioridade.BAIXA, Status.ABERTO));

        assertEquals(10L, resposta.id());
        assertEquals("Mouse quebrado", resposta.titulo());
        assertEquals(Status.ABERTO, resposta.status());
    }

    @Test
    void deveAtualizarChamado() {
        given(chamadoRepository.findById(1L)).willReturn(Optional.of(criarChamado()));
        given(chamadoRepository.save(any(Chamado.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ChamadoResponseDTO resposta = chamadoService.atualizar(1L,
                criarRequest("Atualizado", "Nova descrição", Prioridade.BAIXA, Status.ENCERRADO));

        assertEquals(1L, resposta.id());
        assertEquals("Atualizado", resposta.titulo());
        assertEquals(Status.ENCERRADO, resposta.status());
    }

    @Test
    void deveLancarExcecaoAoAtualizarChamadoInexistente() {
        given(chamadoRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(ChamadoNotFoundException.class,
                () -> chamadoService.atualizar(999L,
                        criarRequest("x", "y", Prioridade.ALTA, Status.ABERTO)));

        verify(chamadoRepository, never()).save(any());
    }

    @Test
    void deveRemoverChamado() {
        Chamado chamado = criarChamado();
        given(chamadoRepository.findById(1L)).willReturn(Optional.of(chamado));

        chamadoService.deletar(1L);

        verify(chamadoRepository).delete(chamado);
    }

    @Test
    void deveLancarExcecaoAoDeletarChamadoInexistente() {
        given(chamadoRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(ChamadoNotFoundException.class,
                () -> chamadoService.deletar(999L));

        verify(chamadoRepository, never()).delete(any());
    }

    private Chamado criarChamado() {
        return Chamado.builder()
                .id(1L)
                .titulo("Teclado não funciona")
                .descricao("Teclado do laboratório")
                .prioridade(Prioridade.ALTA)
                .status(Status.ABERTO)
                .build();
    }

    private ChamadoRequestDTO criarRequest(String titulo, String descricao,
                                           Prioridade prioridade, Status status) {
        ChamadoRequestDTO request = new ChamadoRequestDTO();
        request.setTitulo(titulo);
        request.setDescricao(descricao);
        request.setPrioridade(prioridade);
        request.setStatus(status);
        return request;
    }
}