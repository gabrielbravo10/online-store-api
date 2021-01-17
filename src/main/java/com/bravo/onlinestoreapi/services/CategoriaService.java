package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.dtos.CategoriaDto;
import com.bravo.onlinestoreapi.entities.Categoria;
import com.bravo.onlinestoreapi.repositories.CategoriaRepository;
import com.bravo.onlinestoreapi.services.exceptions.DataIntegrityException;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria find(Integer id) {
        return categoriaRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id +
                        ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria newCategoria = find(categoria.getId());
        updateCategoria(newCategoria, categoria);
        return categoriaRepository.save(newCategoria);
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Nao e possivel excluir uma categoria que possui produtos");
        }
    }

    public Categoria dtoToCategoria(CategoriaDto categoriaDto) {
        return new Categoria(categoriaDto.getId(), categoriaDto.getNome());
    }

    private void updateCategoria(Categoria newCategoria, Categoria categoria) {
        newCategoria.setNome(categoria.getNome());
    }
}
