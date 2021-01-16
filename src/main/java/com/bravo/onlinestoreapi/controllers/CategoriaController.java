package com.bravo.onlinestoreapi.controllers;

import com.bravo.onlinestoreapi.dtos.CategoriaDto;
import com.bravo.onlinestoreapi.entities.Categoria;
import com.bravo.onlinestoreapi.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDto>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDto> categoriaDtos = categorias.stream().map
                (cat -> new CategoriaDto(cat)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoriaDtos);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<CategoriaDto>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy
    ) {
        Page<Categoria> categorias = categoriaService.findPage(page, linesPerPage, direction, orderBy);
        Page<CategoriaDto> categoriaDtos = categorias.map(cat -> new CategoriaDto(cat));
        return ResponseEntity.ok().body(categoriaDtos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {

        Categoria categoria = categoriaService.find(id);
        return ResponseEntity.ok().body(categoria);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDto categoriaDto) {
        Categoria categoria = categoriaService.dtoToCategoria(categoriaDto);
        categoria = categoriaService.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoriaDto.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDto categoriaDto, @PathVariable Integer id) {
        Categoria categoria = categoriaService.dtoToCategoria(categoriaDto);
        categoria.setId(id);
        categoria = categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
