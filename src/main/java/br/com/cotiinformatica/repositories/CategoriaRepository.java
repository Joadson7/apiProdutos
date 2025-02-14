package br.com.cotiinformatica.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
