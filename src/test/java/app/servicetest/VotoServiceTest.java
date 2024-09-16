package app.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.entity.Voto;
import app.entity.Apuracao;
import app.entity.Candidato;
import app.entity.Eleitor;
import app.exception.EleitorInvalido;
import app.exception.StatusInvalido;
import app.repository.VotoRepository;
import app.service.CandidatoService;
import app.service.EleitorService;
import app.service.VotoService;

class VotoServiceTest {

    @InjectMocks
    private VotoService votoService;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private EleitorService eleitorService;

    @Mock
    private CandidatoService candidatoService;

    private Eleitor eleitor = new Eleitor();
    private Voto voto= new Voto();
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        eleitor = new Eleitor();
        voto = new Voto();
        
        eleitor.setId(1L);
        eleitor.setStatus("APTO");
        
        when(eleitorService.findById(1L)).thenReturn(eleitor);
        
        
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);
        
    }

    @Test
    void testVotar_ShouldSaveVotoAndReturnHash() {

        String result = votoService.votar(voto, 1L);

        assertNotNull(result);
        assertEquals(36, result.length());
        verify(votoRepository).save(voto);
        verify(eleitorService).save(eleitor);
    }

    @Test
    void testVotar_EleitorInvalido() {
        when(eleitorService.findById(1L)).thenReturn(null);

        assertThrows(EleitorInvalido.class, () -> votoService.votar(new Voto(), 1L));
    }

    @Test
    void testVotar_EleitorBloqueado() {

        eleitor.setStatus("BLOQUEADO");

        when(eleitorService.findById(1L)).thenReturn(eleitor);

        assertThrows(StatusInvalido.class, () -> votoService.votar(voto, 1L));
    }
    
    @Test
    void testRealizarApuracao() {

    	//############	PREFEITOS
    	
        List<Candidato> prefeitos = new ArrayList<>();

        Candidato prefeito1 = new Candidato();
        prefeito1.setId(1L);
        Candidato prefeito2 = new Candidato();
        prefeito2.setId(2L);

        prefeitos.add(prefeito1);
        prefeitos.add(prefeito2);
        
        when(candidatoService.findAllPrefeito()).thenReturn(prefeitos);
        
        when(votoRepository.countByPrefeitoId(1L)).thenReturn(10);
        when(votoRepository.countByPrefeitoId(2L)).thenReturn(5);
        
      //############	VEREADORES
        
        List<Candidato> vereadores = new ArrayList<>();
        
        Candidato vereador1 = new Candidato();
        vereador1.setId(3L);
        Candidato vereador2 = new Candidato();
        vereador2.setId(4L);
 
        vereadores.add(vereador1);
        vereadores.add(vereador2);

        when(candidatoService.findAllVereador()).thenReturn(vereadores);
        
        when(votoRepository.countByVereadorId(3L)).thenReturn(20);
        when(votoRepository.countByVereadorId(4L)).thenReturn(15);

      //############	ASSERTS
        
        Apuracao apuracao = votoService.realizarApuracao();

        assertNotNull(apuracao);
        assertEquals(10, apuracao.getPrefeito().get(0).getVotos());
        assertEquals(5, apuracao.getPrefeito().get(1).getVotos());
        
        assertEquals(20, apuracao.getVereador().get(0).getVotos());
        assertEquals(15, apuracao.getVereador().get(1).getVotos());
        
        assertEquals(2, apuracao.getPrefeito().size());
        assertEquals(2, apuracao.getVereador().size());
    }
}
