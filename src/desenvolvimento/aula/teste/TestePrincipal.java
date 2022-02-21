package desenvolvimento.aula.teste;

import java.util.List;

import desenvolvimento.aula.dao.DaoAlunoRepository;
import desenvolvimento.aula.entity.Aluno;

public class TestePrincipal {

	public static void main(String[] args) throws Exception {
		DaoAlunoRepository repository = new DaoAlunoRepository();
		
		Aluno aluno = new Aluno();
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Joao Silva");
		
		//repository.salvarAluno(aluno2);
		repository.atualizarAluno(aluno2, 3);
		
		List<Aluno> alunos = repository.findAll();
		
		for(Aluno a: alunos) {
			
			System.out.println(a);
		}
		
		
	}

}
