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
import com.example.demo.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String nombre, ModelMap modelo){

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue registrada con éxito.");
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "editorial_form.html";
        }
        return "index.html";

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        modelo.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "editorial_list.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, ModelMap modelo){
        modelo.addAttribute("editorial", editorialServicio.buscarEditorial(id));
        return "editorial_edit.html";
    }

    @PostMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, @RequestParam(required = false) String nombre, RedirectAttributes redirectAttributes){
        try {
            editorialServicio.modificarEditorial(id, nombre);
            redirectAttributes.addFlashAttribute("exito", "La editorial fue editada con éxito.");
            return "redirect:/editorial/lista";
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editorial/editar/" + id;
        }
    }

}
