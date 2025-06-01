// package com.example.demo.servicios;

// import java.io.IOException;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.demo.Excepciones.MyException;
// import com.example.demo.entidades.Imagen;
// import com.example.demo.repositorios.ImagenRepositorio;

// @Service
// public class ImagenServicio {

//     @Autowired
//     ImagenRepositorio imagenRepositorio;

//     public Imagen guardarImagen(MultipartFile archivo) throws MyException {

//         if (archivo != null) {
//             try {
//                 Imagen nuevaImagen = new Imagen();
//                 nuevaImagen.setNombre(archivo.getName());
//                 nuevaImagen.setMimeType(archivo.getContentType());
//                 nuevaImagen.setContenido(archivo.getBytes());
//                 return imagenRepositorio.save(nuevaImagen);
//             } catch (IOException e) {
//                 System.out.println("Error al guardar la imagen: " + e.getMessage());
//                 return null;
//             }
//         }
//         return null;
//     }

//     public Imagen actualizarImagen(MultipartFile archivo, UUID id) {

//         if (archivo != null) {

//             try {
//                 Imagen imagenExistente = new Imagen();

//                 if (id != null) {
//                     Optional<Imagen> respuesta = imagenRepositorio.findById(id);
//                     if (respuesta.isPresent()) {
//                         imagenExistente = respuesta.get();
//                     }
//                 }

//                 imagenExistente.setNombre(archivo.getName());
//                 imagenExistente.setMimeType(archivo.getContentType());
//                 imagenExistente.setContenido(archivo.getBytes());
//                 return imagenRepositorio.save(imagenExistente);

//             } catch (IOException e) {
//                 System.out.println("Error al actualizar la imagen: " + e.getMessage());
//             }

//         }
//         return null;
//     }

// }
