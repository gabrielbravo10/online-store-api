package com.bravo.onlinestoreapi;

import com.bravo.onlinestoreapi.entities.Categoria;
import com.bravo.onlinestoreapi.entities.Cidade;
import com.bravo.onlinestoreapi.entities.Estado;
import com.bravo.onlinestoreapi.entities.Produto;
import com.bravo.onlinestoreapi.repositories.CategoriaRepository;
import com.bravo.onlinestoreapi.repositories.CidadeRepository;
import com.bravo.onlinestoreapi.repositories.EstadoRepository;
import com.bravo.onlinestoreapi.repositories.ProdutoRepository;
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

	@Autowired
	public OnlineStoreApiApplication(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository,
									 EstadoRepository estadoRepository, CidadeRepository cidadeRepository) {
		this.categoriaRepository = categoriaRepository;
		this.produtoRepository = produtoRepository;
		this.estadoRepository = estadoRepository;
		this.cidadeRepository = cidadeRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
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

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	}
}
