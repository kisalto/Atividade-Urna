package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

	List<Candidato> findAllByFuncaoAndStatusNot(int funcao, String status);
	
}
