package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.Pedido;
import com.bravo.onlinestoreapi.repositories.PedidoRepository;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido find(Integer id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id +
                        ", Tipo: " + Pedido.class.getName()));
    }
}
