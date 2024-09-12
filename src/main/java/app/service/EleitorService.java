package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Eleitor;
import app.repository.EleitorRepository;

@Service
public class EleitorService {

	@Autowired
	private EleitorRepository eleitorRepository;
	
	public String save(Eleitor eleitor) {
		this.eleitorRepository.save(eleitor);
		return "Eleitor cadastrado com sucesso";
	}
	
	public String update(Eleitor eleitor, Long id) {
		Eleitor eleitorEx = this.findById(id);
		if (eleitorEx == null)
			return "Eleitor não existente";
		this.eleitorRepository.save(eleitor);
		return "Eleitor atualizado com sucesso";
	}
	
	public Eleitor findById(Long id) {
		Optional<Eleitor> optional = this.eleitorRepository.findById(id);
		if (optional == null)
			return null;
		return optional.get();
	}
	
	public List<Eleitor> findAll(){
		return this.eleitorRepository.findAll();
	}
	
	public String delete(Long id) {
		Eleitor eleitorEx = this.findById(id);
		if (eleitorEx == null)
			return "Eleitor não existente";
		this.eleitorRepository.deleteById(id);
		return "Eleitor deletado com sucesso";
	}
	
}
