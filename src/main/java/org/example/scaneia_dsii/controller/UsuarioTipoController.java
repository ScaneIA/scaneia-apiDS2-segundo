package org.example.scaneia_dsii.controller;

import org.example.scaneia_dsii.model.UsuarioTipo;
import org.example.scaneia_dsii.repository.UsuarioTipoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarioTipo")
public class UsuarioTipoController {

    private final UsuarioTipoRepository usuarioTipoRepository;

    public UsuarioTipoController(UsuarioTipoRepository usuarioTipoRepository) {
        this.usuarioTipoRepository = usuarioTipoRepository;
    }

    @GetMapping()
    public List<UsuarioTipo> getAll(){
        return usuarioTipoRepository.findAll();
    }
}
