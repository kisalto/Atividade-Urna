package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Candidato;
import app.repository.CandidatoRepository;

@Service
public class CandidatoService {
	
	@Autowired
	private CandidatoRepository candidatoRepository;
	
	public String save(Candidato candidato) {
		this.candidatoRepository.save(candidato);
		return "Candidato cadastrado com sucesso";
	}
	
	public String update(Candidato candidato, Long id) {
		Candidato candidatoEx = this.findById(id);
		if (candidatoEx == null)
			return "candidato não existe";
		this.candidatoRepository.save(candidato);
		return "candidato salvo com sucesso";
	}
	
	public Candidato findById(Long id) {
		Optional<Candidato> optional = this.candidatoRepository.findById(id);
		if (optional == null)
			return null;
		return optional.get();
	}
	
	public List<Candidato> findAll() {
		return this.candidatoRepository.findAll();
	}
	
	public String delete(Long id) {
		Candidato candidatoEx = this.findById(id);
		if (candidatoEx == null)
			return "candidato não existe";
		this.candidatoRepository.deleteById(id);
		return "Candidato deletado com sucesso";
	}
	
}
