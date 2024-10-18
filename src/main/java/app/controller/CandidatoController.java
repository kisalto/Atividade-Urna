package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.entity.Candidato;
import app.service.CandidatoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/candidato")
public class CandidatoController {

	@Autowired
	private CandidatoService candidatoService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Candidato candidato) {

		try {
			if (candidato.getStatus() != null)
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("status deve ser nulo");

			String mensagem = this.candidatoService.save(candidato);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Candidato candidato, @PathVariable Long id) {

		try {
			if (candidato.getStatus() != null)
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "status deve ser nulo");

			String mensagem = this.candidatoService.update(candidato, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById")
	public ResponseEntity<Candidato> findById(@PathVariable Long id) {

		try {
			Candidato candidato = this.candidatoService.findById(id);
			return new ResponseEntity<>(candidato, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Candidato>> findAll() {

		try {
			List<Candidato> candidato = this.candidatoService.findAll();
			return new ResponseEntity<>(candidato, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findAllPrefeito")
	public ResponseEntity<List<Candidato>> findAllPrefeito() {

		try {
			List<Candidato> candidato = this.candidatoService.findAllPrefeito();
			return new ResponseEntity<>(candidato, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/findAllVereador")
	public ResponseEntity<List<Candidato>> findAllVereador() {

		try {
			List<Candidato> candidato = this.candidatoService.findAllVereador();
			return new ResponseEntity<>(candidato, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			String mensagem = this.candidatoService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
