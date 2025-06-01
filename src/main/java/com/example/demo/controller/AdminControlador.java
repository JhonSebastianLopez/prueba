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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Excepciones.MyException;
import com.example.demo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }

    @GetMapping("/lista")
    public String usuarios(ModelMap modelo){
        modelo.addAttribute("usuarios",usuarioServicio.listarUsuarios());
        return "usuario_list.html";
    }

    @PostMapping("/modificar-rol/{id}")
    public String modificarRol(@PathVariable UUID id, RedirectAttributes redirectAttributes){
        try {
            usuarioServicio.modificarRol(id);
            redirectAttributes.addFlashAttribute("exito", "Rol modificado correctamente");
            return "redirect:/admin/lista";
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirecr:/admin/lista";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificarUsuario(@PathVariable UUID id, ModelMap modelo){
        try {
            modelo.addAttribute("usuario", usuarioServicio.buscarUsuario(id));
            return "usuario_edit.html";
        } catch (MyException e) {
            modelo.addAttribute("error", e.getMessage());
            return "usuario_edit.html";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modificarUsuario(@RequestParam(required = false) String nombre,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) MultipartFile archivo,
                                   @PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            usuarioServicio.modificarUsuario(archivo, id, nombre, email);
            redirectAttributes.addFlashAttribute("exito", "Usuario modificado correctamente");
            return "redirect:/admin/lista";
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/admin/modificar/" + id;
        }

    }

}
