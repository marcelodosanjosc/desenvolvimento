package desenvolvimento.aula.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import desenvolvimento.aula.conexao.SingleConnectionBanco;
import desenvolvimento.aula.entity.Aluno;

public class DaoAlunoRepository {

	private static Connection connection;

	public DaoAlunoRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public Aluno find() throws Exception {

		Aluno aluno = new Aluno();
		String sql = "select * from aluno ";
		PreparedStatement consultar = connection.prepareStatement(sql);

		ResultSet resultado = consultar.executeQuery();

		while (resultado.next()) {
			aluno.setId(resultado.getInt("id"));
			aluno.setNome(resultado.getString("nome"));

		}

		return aluno;
	}
	
	public Aluno findId(int id) throws Exception {

		Aluno aluno = new Aluno();
		String sql = "select * from aluno where id="+id;
		PreparedStatement consultar = connection.prepareStatement(sql);

		ResultSet resultado = consultar.executeQuery();

		while (resultado.next()) {
			aluno.setId(resultado.getInt("id"));
			aluno.setNome(resultado.getString("nome"));

		}

		return aluno;
	}

	public List<Aluno> findAll() throws Exception {

		List<Aluno> listaAluno = new ArrayList<>();
		String sql = "select * from aluno ";
		PreparedStatement consultar = connection.prepareStatement(sql);

		ResultSet resultado = consultar.executeQuery();

		while (resultado.next()) {
			Aluno aluno = new Aluno();
			aluno.setId(resultado.getInt("id"));
			aluno.setNome(resultado.getString("nome"));

			listaAluno.add(aluno);
		}

		return listaAluno;
	}

	public void salvarAluno(Aluno aluno) throws Exception {
		String sql = "INSERT INTO public.aluno( nome)VALUES ( ?);";
		PreparedStatement salvar = connection.prepareStatement(sql);

		salvar.setString(1, aluno.getNome());

		salvar.execute();
		connection.commit();

	}

	public void atualizarAluno(Aluno aluno, Integer id) throws Exception {
		String sql = "UPDATE public.aluno SET nome=? WHERE id =" + id;

		PreparedStatement atualizar = connection.prepareStatement(sql);
		atualizar.setString(1, aluno.getNome());

		atualizar.executeUpdate();
		connection.commit();
	}
}
