package com.bravo.onlinestoreapi.controllers;

import com.bravo.onlinestoreapi.dtos.ClienteDto;
import com.bravo.onlinestoreapi.dtos.ClienteNewDto;
import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDto>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDto> clienteDtos = clientes.stream().map
                (cat -> new ClienteDto(cat)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDtos);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDto>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
    ) {
        Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, direction, orderBy);
        Page<ClienteDto> clienteDtos = clientes.map(cat -> new ClienteDto(cat));
        return ResponseEntity.ok().body(clienteDtos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDto clienteNewDTO) {
        Cliente cliente = clienteService.newDtoToCliente(clienteNewDTO);
        cliente = clienteService.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        Cliente cliente = clienteService.dtoToCliente(clienteDto);
        cliente.setId(id);
        cliente = clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
