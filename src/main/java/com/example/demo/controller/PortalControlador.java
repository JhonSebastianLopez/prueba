package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entidades.Usuario;
import com.example.demo.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/") // Acá es donde realizamos el mapeo
    public String index(){
        return "index.html"; // Acá es que retornamos con el método. 
    }

    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrector");
        }
        return "login.html";
    }

    @PostMapping("/registro")
    public String registro(
        @RequestParam(required = false) MultipartFile archivo,
        @RequestParam(required = false) String nombre, 
        @RequestParam(required = false) String email, 
        @RequestParam(required = false) String password, 
        @RequestParam(required = false) String password2, 
        ModelMap modelo){

        try {
            usuarioServicio.registrarUsuario(archivo, nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");
            return "index.html";  
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
        if (usuarioLogueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

}
