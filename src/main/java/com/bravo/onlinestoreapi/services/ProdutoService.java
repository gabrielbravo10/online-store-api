package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.Categoria;
import com.bravo.onlinestoreapi.entities.Produto;
import com.bravo.onlinestoreapi.repositories.CategoriaRepository;
import com.bravo.onlinestoreapi.repositories.ProdutoRepository;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        return produtoRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id +
                        ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids,
                         Integer page, Integer linesPerPage,
                         String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.search(nome, categorias, pageRequest);
    }
}
