package com.bravo.onlinestoreapi.services;

import com.bravo.onlinestoreapi.entities.*;
import com.bravo.onlinestoreapi.entities.enums.EstadoPagamento;
import com.bravo.onlinestoreapi.entities.enums.TipoCliente;
import com.bravo.onlinestoreapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    private CategoriaRepository categoriaRepository;
    private ProdutoRepository produtoRepository;
    private EstadoRepository estadoRepository;
    private CidadeRepository cidadeRepository;
    private ClienteRepository clienteRepository;
    private EnderecoRepository enderecoRepository;
    private PedidoRepository pedidoRepository;
    private PagamentoRepository pagamentoRepository;
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public DBService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, EstadoRepository estadoRepository,
                                     CidadeRepository cidadeRepository, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository,
                                     PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.estadoRepository = estadoRepository;
        this.cidadeRepository = cidadeRepository;
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public void instanciatedTestDatabase() throws ParseException {

        // Categoria and Produto added
        Categoria cat1 = new Categoria(null, "Informatica");
        Categoria cat2 = new Categoria(null, "Escritorio");
        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);


        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));
        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

        // Estado and Cidade added
        Estado estadoMG = new Estado(null, "Minas Gerais");
        Estado estadoSP = new Estado(null, "Sao Paulo");
        Cidade cidadeUberlandia = new Cidade(null, "Uberlandia", estadoMG);
        Cidade cidadeSP = new Cidade(null, "Sao Paulo", estadoSP);
        Cidade cidadeCampinas = new Cidade(null, "Campinas", estadoSP);

        estadoMG.getCidades().addAll(Arrays.asList(cidadeUberlandia));
        estadoSP.getCidades().addAll(Arrays.asList(cidadeSP, cidadeCampinas));

        estadoRepository.saveAll(Arrays.asList(estadoMG, estadoSP));
        cidadeRepository.saveAll(Arrays.asList(cidadeUberlandia, cidadeSP, cidadeCampinas));

        // Cliente, Telefones, Enderecos added
        Cliente clienteMaria = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);

        clienteMaria.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

        Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303",
                "Jardim", "38220834", clienteMaria, cidadeUberlandia);
        Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800",
                "Centro", "38777012", clienteMaria, cidadeSP);

        clienteMaria.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        clienteRepository.saveAll(Arrays.asList(clienteMaria));
        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

        // Pedido, Pagamento added
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2020 10:32"), clienteMaria, endereco1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2020 19:12"), clienteMaria, endereco2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"), null);
        ped2.setPagamento(pagto2);

        clienteMaria.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        // ItemPedido added

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
