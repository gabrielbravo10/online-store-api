package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.dtos.ClienteDto;
import com.bravo.onlinestoreapi.dtos.ClienteNewDto;
import com.bravo.onlinestoreapi.entities.Cidade;
import com.bravo.onlinestoreapi.entities.Cliente;
import com.bravo.onlinestoreapi.entities.Endereco;
import com.bravo.onlinestoreapi.entities.enums.TipoCliente;
import com.bravo.onlinestoreapi.repositories.CidadeRepository;
import com.bravo.onlinestoreapi.repositories.ClienteRepository;
import com.bravo.onlinestoreapi.repositories.EnderecoRepository;
import com.bravo.onlinestoreapi.services.exceptions.DataIntegrityException;
import com.bravo.onlinestoreapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente find(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Objeto nao encontrado! Id: " + id +
                        ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente newCliente = find(cliente.getId());
        updateCliente(newCliente, cliente);
        return clienteRepository.save(newCliente);
    }

    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityException("Nao e possivel excluir um cliente porque ha pedidos relacionados");
        }
    }

    public Cliente dtoToCliente(ClienteDto clienteDto) {
        return new Cliente(clienteDto.getId(), clienteDto.getNome(),
                clienteDto.getEmail(), null, null, null);
    }

    public Cliente newDtoToCliente(ClienteNewDto clienteNewDto) {
        Cliente cliente = new Cliente(null, clienteNewDto.getNome(), clienteNewDto.getEmail(),
                clienteNewDto.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDto.getTipoCliente()),
                bCryptPasswordEncoder.encode(clienteNewDto.getSenha()));
//        Optional<Cidade> cidade = cidadeRepository.findById(clienteNewDto.getCidadeId());
        Cidade cidade = new Cidade(clienteNewDto.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, clienteNewDto.getLogradouro(), clienteNewDto.getNumero(),
                clienteNewDto.getComplemento(), clienteNewDto.getBairro(), clienteNewDto.getCep(),
                cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteNewDto.getTelefone1());
        if (clienteNewDto.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNewDto.getTelefone2());
        }
        if (clienteNewDto.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNewDto.getTelefone3());
        }
        return cliente;
    }

    private void updateCliente(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
