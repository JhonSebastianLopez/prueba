package com.example.demo.servicios;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Excepciones.MyException;
import com.example.demo.entidades.Editorial;
import com.example.demo.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MyException{
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void modificarEditorial(UUID id, String nombre) throws MyException{
        validar(nombre);
        Optional<Editorial> editorialModificada = editorialRepositorio.findById(id);
        if (editorialModificada.isPresent()) {
            editorialModificada.get().setNombre(nombre);
            editorialRepositorio.save(editorialModificada.get());
        }else{
            System.out.println("editorial no encontrada");
        }
    }

    private void validar(String nombre) throws MyException{
        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre de la editorial no puede estar vacio o ser nulo");
        }
    }

    @Transactional(readOnly = true)
    public Editorial buscarEditorial(UUID id){
        return editorialRepositorio.findById(id).get();
    }

}
