// package com.example.demo.controller;

// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.demo.Excepciones.MyException;
// import com.example.demo.servicios.UsuarioServicio;

// @Controller
// @RequestMapping("/imagenes")
// public class ImagenControlador {

//     @Autowired 
//     UsuarioServicio usuarioServicio;

//     @GetMapping("/perfil/{id}")
//     public ResponseEntity<byte[]> imagenUsuario(@PathVariable UUID id){
//         try {
//             byte[] imagen = usuarioServicio.buscarUsuario(id).getImagen().getContenido();
//             HttpHeaders headers = new HttpHeaders();
//             headers.setContentType(MediaType.IMAGE_JPEG);
//             return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
//         } catch (MyException e) {
//             System.out.println("Error al obtener la imagen: " + e.getMessage());
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         }
//     }

// }
