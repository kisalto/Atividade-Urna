package app.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Eleitor;
import app.exception.EleitorInvalido;
import app.exception.StatusInvalido;
import app.repository.EleitorRepository;
import app.service.EleitorService;
import app.service.VotoService;

public class EleitorServiceTest {

	@InjectMocks
    private VotoService votoService;
	
	@InjectMocks
	private EleitorService eleitorService;

	@Mock
	private EleitorRepository eleitorRepository;

	private Eleitor eleitor;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		votoService = new VotoService();
		eleitor = new Eleitor();
		
		
		Eleitor eleitor = new Eleitor();
		eleitor.setId(1L);
		eleitor.setCPF("12345678900");
		eleitor.setEmail("email@example.com");
		eleitor.setStatus("APTO");

		when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

		when(eleitorRepository.save(any(Eleitor.class))).thenReturn(eleitor);

	}

	@Test
	public void testSave_WithNullCpfAndEmail() {
		eleitor.setCPF(null);
		eleitor.setEmail(null);

		String result = eleitorService.save(eleitor);

		assertEquals("Eleitor cadastrado com sucesso", result);
		assertEquals("PENDENTE", eleitor.getStatus());
	}

	@Test
	public void testSave_WithCpfAndEmail() {
		eleitor.setCPF("12345678900");
		eleitor.setEmail("email@example.com");
		
		String result = eleitorService.save(eleitor);

		assertEquals("Eleitor cadastrado com sucesso", result);
		assertEquals("APTO", eleitor.getStatus());
	}

	@Test
	public void testUpdate_EleitorExists() {

		Eleitor existingEleitor = new Eleitor();
		existingEleitor.setId(1L);
		existingEleitor.setStatus("PENDENTE");

		Eleitor updatedEleitor = new Eleitor();
		updatedEleitor.setId(1L);
		updatedEleitor.setCPF("12345678900");

		when(eleitorRepository.findById(1L)).thenReturn(Optional.of(existingEleitor));
		when(eleitorRepository.save(any(Eleitor.class))).thenReturn(updatedEleitor);

		String result = eleitorService.update(updatedEleitor, 1L);

		assertEquals("Eleitor atualizado com sucesso", result);
		assertEquals("APTO", updatedEleitor.getStatus());

	}

	@Test
	public void testUpdate_EleitorDoesNotExist() {

		when(eleitorRepository.findById(1L)).thenReturn(Optional.empty());

		Eleitor eleitor = new Eleitor();
		eleitor.setNome("novo eleitor");

		assertThrows(EleitorInvalido.class, () -> eleitorService.update(eleitor, 1L));
	}

	@Test
	public void testFindById_EleitorExists() {

		Eleitor result = eleitorService.findById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void testFindById_EleitorDoesNotExist() {
		when(eleitorRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(StatusInvalido.class, () -> eleitorService.delete(1L));
	}

	@Test
    public void testDelete_EleitorExistsAndStatusVotou() {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setStatus("VOTOU");

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

        assertThrows(StatusInvalido.class, () -> eleitorService.delete(1L));
    }

	@Test
	public void testDelete_EleitorExists() {
		
		String result = eleitorService.delete(1L);
		
		assertEquals("Eleitor deletado com sucesso", result);
		
		eleitor.setStatus("INATIVO");
		
		assertEquals("INATIVO", eleitor.getStatus());
	}
	
	@Test
	public void testFindAll() {
		List<Eleitor> eleitor = List.of(
				new Eleitor(null, "eleitor A", "12345678900", "batata", "(45) 9 9927-9690", null, "teste@email.com", null, null),
				new Eleitor(null, "eleitor B", "98765432100", "batata", "(45) 9 9927-9690", null, "teste@email.com", null, null));
		
		when(eleitorRepository.findAll()).thenReturn(eleitor);
		
		List<Eleitor> resultado = eleitorService.findAll();
		
		assertEquals(2, resultado.size());
		assertEquals(eleitor, resultado);
	}
}
