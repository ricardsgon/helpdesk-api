package io.github.ricardsgon.helpdeskapi.exception;

public class ChamadoNotFoundException extends RuntimeException {
    public ChamadoNotFoundException(Long id){
        super("Chamado não encontrado: " + id);
    }

}
