package com.example.demo.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial,UUID> {

}
