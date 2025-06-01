package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Excepciones.MyException;
import com.example.demo.entidades.Autor;
import com.example.demo.repositorios.AutorRepositorio;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MyException{

        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);

    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }

    @Transactional
    public void modificarAutor(UUID id, String nombre) throws MyException{

        validar(nombre);
        Optional<Autor> autorModificado = autorRepositorio.findById(id);
        if (autorModificado.isPresent()) {
            autorModificado.get().setNombre(nombre);
            autorRepositorio.save(autorModificado.get());
        }

    }

    private void validar(String nombre) throws MyException{
        if (nombre.isEmpty() || nombre==null) {
            throw new MyException("el nombre no puede ser nulo o estar vacio");            
        }
    }

    @Transactional(readOnly = true)
    public Autor buscarAutor(UUID id){
        return autorRepositorio.findById(id).get();
    }

}
