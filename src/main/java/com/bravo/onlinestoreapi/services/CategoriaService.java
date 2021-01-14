package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.Categoria;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import com.bravo.onlinestoreapi.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) {
        return categoriaRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
}
