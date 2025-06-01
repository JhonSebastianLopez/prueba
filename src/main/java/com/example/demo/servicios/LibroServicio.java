package com.example.demo.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Excepciones.MyException;
import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.repositorios.AutorRepositorio;
import com.example.demo.repositorios.EditorialRepositorio;
import com.example.demo.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MyException{

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setAlta(new Date());

        libroRepositorio.save(libro);

    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros(){
        return libroRepositorio.findAll();
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MyException{
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Optional<Libro> libroModificado = libroRepositorio.findById(isbn);
        Optional<Autor> autor = autorRepositorio.findById(idAutor);
        Optional<Editorial> editorial = editorialRepositorio.findById(idEditorial);
        if (libroModificado.isPresent() && autor.isPresent() && editorial.isPresent()) {
            libroModificado.get().setTitulo(titulo);
            libroModificado.get().setEjemplares(ejemplares);
            libroModificado.get().setAutor(autor.get());
            libroModificado.get().setEditorial(editorial.get());
        }else{
            System.out.println("error, datos invalidos");
        }
    }

    @Transactional(readOnly = true)
    public Libro buscarLibro(Long isbn){
        return libroRepositorio.findById(isbn).get();
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial)
            throws MyException {

        if (isbn == null) {
            throw new MyException("el isbn no puede ser nulo"); //
        }
        if (titulo.isEmpty() || titulo == null) {
            throw new MyException("el titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares == null) {
            throw new MyException("ejemplares no puede ser nulo");
        }
        if (idAutor == null) {
            throw new MyException("el Autor no puede ser nulo o estar vacio");
        }
        if (idEditorial == null) {
            throw new MyException("La Editorial no puede ser nula o estar vacia");
        }
    }


}
