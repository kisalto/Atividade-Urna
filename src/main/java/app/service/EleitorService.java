package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Eleitor;
import app.exception.EleitorInvalido;
import app.exception.StatusInvalido;
import app.repository.EleitorRepository;

@Service
public class EleitorService {

	@Autowired
	private EleitorRepository eleitorRepository;

	public String save(Eleitor eleitor) {
		
		if (eleitor.getCPF() == null || eleitor.getEmail() == null)
			eleitor.setStatus("PENDENTE");
		else
			eleitor.setStatus("APTO");
		
		this.eleitorRepository.save(eleitor);
		return "Eleitor cadastrado com sucesso";
	}

	public String update(Eleitor eleitor, Long id) {
		
	    Eleitor eleitorEx = this.findById(id);
	    if (eleitorEx == null)
	    	throw new EleitorInvalido("Eleitor não existente");
	    
	    if (verificarStatus(eleitor, eleitorEx) == true) 
	    	eleitor.setStatus("APTO");

	    this.eleitorRepository.save(eleitor);
	    return "Eleitor atualizado com sucesso";
	}

	private Boolean verificarStatus(Eleitor eleitor, Eleitor eleitorEx) {
		
		if (eleitor.getCPF() == null && eleitor.getEmail() == null)
			return false;
		
		if (!eleitorEx.getStatus().equals("PENDENTE"))
			return false;
		
		return true;
	}
	
	public Eleitor findById(Long id) {
		
		Optional<Eleitor> optional = this.eleitorRepository.findById(id);
		if (optional == null)
			return null;
		return optional.get();
	}

	public List<Eleitor> findAll() {
		
		return this.eleitorRepository.findAll();
	}

	public String delete(Long id) {
		
		Eleitor eleitorEx = this.findById(id);
		if (eleitorEx == null)
			throw new StatusInvalido("Eleitor não existente");
		
		if (eleitorEx.getStatus().equals("VOTOU"))
			throw new StatusInvalido("Usuário já votou. Não foi possível inativá-lo");
		
		eleitorEx.setStatus("INATIVO");
		
		this.save(eleitorEx);
		return "Eleitor deletado com sucesso";
	}

}
