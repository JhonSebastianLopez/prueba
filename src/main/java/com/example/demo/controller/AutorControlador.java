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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Excepciones.MyException;
import com.example.demo.servicios.AutorServicio;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio; // inyecto el servicio

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String nombre, ModelMap modelo) {
        try {
            autorServicio.crearAutor(nombre); // llamo a mi servicio para persistir
            modelo.put("exito", "el autor fue registrado con exito");
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("autores", autorServicio.listarAutores());
        return "autor_list.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, ModelMap modelo) {
        modelo.addAttribute("autor", autorServicio.buscarAutor(id));
        return "autor_edit.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, @RequestParam(required = false) String nombre,
            RedirectAttributes redirectAttributes) {
        try {
            autorServicio.modificarAutor(id, nombre);
            redirectAttributes.addFlashAttribute("exito", "El autor fue registrado con éxito");
            return "redirect:/autor/lista";
            // autorServicio.modificarAutor(id, nombre);
            // modelo.put("exito", "el autor fue registrado con exito");
            // return "redirect:../lista";
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/autor/editar/" + id;
            // modelo.put("error", e.getMessage());
            // return "redirect:../autor_edit/"+id;
        }
    }

}
