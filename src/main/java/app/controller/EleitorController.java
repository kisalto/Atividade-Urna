package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entity.Eleitor;
import app.service.EleitorService;

@RestController
@RequestMapping("api/eleitor")
public class EleitorController {

	@Autowired
	private EleitorService eleitorService;
	
	@PostMapping()
	public ResponseEntity<String> save (@RequestBody Eleitor eleitor) {
		try {
			String mensagem = this.eleitorService.save(eleitor);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<String> update (@RequestBody Eleitor eleitor, @PathVariable Long id) {
		try {
			String mensagem = this.eleitorService.update(eleitor, id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<Eleitor> findById (@PathVariable Long id) {
		try {
			Eleitor eleitor = this.eleitorService.findById(id);
			return new ResponseEntity<>(eleitor, HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<Eleitor>> findAll () {
		try {
			List<Eleitor> eleitor = this.eleitorService.findAll();
			return new ResponseEntity<>(eleitor, HttpStatus.OK); 
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<String> delete (@PathVariable Long id) {
		try {
			String mensagem = this.eleitorService.delete(id);
			return new ResponseEntity<>(mensagem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
