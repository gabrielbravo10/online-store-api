package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.repositories.ClienteRepository;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente find(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id +
                        ", Tipo: " + Cliente.class.getName()));
    }
}
