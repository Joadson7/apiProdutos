package br.com.cotiinformatica.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.dtos.DashboardResponseDto;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class CategoriaRepository {

	private ConnectionFactory connectionFactory = new ConnectionFactory();

	public List<Categoria> findAll() {

		try {

			var connection = connectionFactory.getConnection();

			var statement = connection.prepareStatement("select * from categoria order by nome");
			var result = statement.executeQuery();

			var lista = new ArrayList<Categoria>();

			while (result.next()) {
				var categoria = new Categoria();
				categoria.setId(UUID.fromString(result.getString("id")));
				categoria.setNome(result.getString("nome"));

				lista.add(categoria);
			}
			connection.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	public List<DashboardResponseDto> groupByQtdProdutos(){
		
		try {
			
			var connection = connectionFactory.getConnection();
			
			var query = """
					
					SELECT
						c.nome as nomecategoria,
						SUM(p.quantidade) as qtdprodutos
					FROM produto p 
					INNER JOIN categoria c
					ON c.id = p.categoria_id
					GROUP BY c.nome;
					
					""";
			
			var statement = connection.prepareStatement(query);
			var result = statement.executeQuery();
			
			var lista = new ArrayList<DashboardResponseDto>();
			while(result.next()) {
				
				var dto = new DashboardResponseDto();
				dto.setNomeCategoria(result.getString("nomecategoria"));
				dto.setQtdProdutos(result.getInt("qtdprodutos"));
				
				lista.add(dto);
			}
			
			connection.close();
			return lista;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
}
