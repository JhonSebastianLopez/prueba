package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Excepciones.MyException;
import com.example.demo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo) {
        modelo.put("roles", usuarioServicio.listaRols());
        return "perfil.html";
    }

    @PostMapping("/modificar/{id}")
    public String perfil(   @RequestParam(required = false) MultipartFile archivo,
                            @PathVariable UUID id,
                            @RequestParam(required = false) String nombre,
                            @RequestParam(required = false) String email) {

        try {
            usuarioServicio.modificarUsuario(archivo, id, nombre, email);
        } catch (MyException e) {
            return "redirect:/usuarios/perfil?error=" + e.getMessage();
        }

        return "redirect:/usuarios/perfil";

    }

}
