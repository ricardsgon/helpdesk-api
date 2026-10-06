package io.github.ricardsgon.helpdeskapi.controller;

import io.github.ricardsgon.helpdeskapi.database.model.Chamado;
import io.github.ricardsgon.helpdeskapi.service.ChamadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

   @GetMapping
   @ResponseStatus(HttpStatus.ACCEPTED)
   public List<Chamado> getChamados() {
       return new ChamadoService().listar();
   }
   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.ACCEPTED)
   public Chamado getChamadoById(@PathVariable Long id) {
       return new ChamadoService().buscarPorId(id);
   }

}
