package com.example.demo.controller;

import java.util.List;
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
import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.servicios.AutorServicio;
import com.example.demo.servicios.EditorialServicio;
import com.example.demo.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(
        @RequestParam(required = false) Long isbn, 
        @RequestParam(required = false) String titulo, 
        @RequestParam(required = false) Integer ejemplares,
        @RequestParam(required = false) UUID idAutor,
        @RequestParam(required = false) UUID idEditorial,
        ModelMap modelo){
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito","El libro fue registrado con éxito.");
        } catch (MyException e) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", e.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        modelo.addAttribute("libros",libroServicio.listarLibros());
        return "libro_list.html";
    }

    @GetMapping("/editar/{isbn}")
    public String editar(@PathVariable Long isbn, ModelMap modelo){
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("libro", libroServicio.buscarLibro(isbn));
        return "libro_edit.html";
    }

    @PostMapping("/editar/{isbn}")
    public String editar(@PathVariable Long isbn,         
    @RequestParam(required = false) String titulo, 
    @RequestParam(required = false) Integer ejemplares,
    @RequestParam(required = false) UUID idAutor,
    @RequestParam(required = false) UUID idEditorial,
    RedirectAttributes redirectAttributes){
        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            redirectAttributes.addFlashAttribute("exito", "El libro fue editado con éxito.");
            return "redirect:/libro/lista";
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/libro/editar/" + isbn;
        }
    
    }

}
