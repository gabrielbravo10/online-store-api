package com.bravo.onlinestoreapi;

import com.bravo.onlinestoreapi.entities.*;
import com.bravo.onlinestoreapi.entities.enums.TipoCliente;
import com.bravo.onlinestoreapi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class OnlineStoreApiApplication implements CommandLineRunner {

	CategoriaRepository categoriaRepository;
	ProdutoRepository produtoRepository;
	EstadoRepository estadoRepository;
	CidadeRepository cidadeRepository;
	ClienteRepository clienteRepository;
	EnderecoRepository enderecoRepository;

	@Autowired
	public OnlineStoreApiApplication(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository, EstadoRepository estadoRepository,
									 CidadeRepository cidadeRepository, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository) {
		this.categoriaRepository = categoriaRepository;
		this.produtoRepository = produtoRepository;
		this.estadoRepository = estadoRepository;
		this.cidadeRepository = cidadeRepository;
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

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
	}
}
