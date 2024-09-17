package app.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.EleitorController;
import app.entity.Eleitor;
import app.service.EleitorService;

@SpringBootTest
public class EleitorControllerTest {

    @Autowired
    EleitorController eleitorController;

    @MockBean
    EleitorService eleitorService;

    private Eleitor eleitor;
    private List<Eleitor> eleitores;

    @BeforeEach
    void setup() {
        eleitor = criarEleitor();
        eleitores = List.of(eleitor);
        configurarMocks("sucesso");
    }

    private Eleitor criarEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome("João");
        eleitor.setCPF("12345678900");
        return eleitor;
    }

    private void configurarMocks(String tipo) {
        if ("sucesso".equals(tipo)) {
            Mockito.when(eleitorService.save(Mockito.any())).thenReturn("Eleitor salvo com sucesso!");
            Mockito.when(eleitorService.update(Mockito.any(), Mockito.anyLong())).thenReturn("Eleitor atualizado com sucesso!");
            Mockito.when(eleitorService.findById(Mockito.anyLong())).thenReturn(eleitor);
            Mockito.when(eleitorService.findAll()).thenReturn(eleitores);
            Mockito.when(eleitorService.delete(Mockito.anyLong())).thenReturn("Eleitor deletado com sucesso!");
        } else if ("erro".equals(tipo)) {
            Mockito.when(eleitorService.save(Mockito.any())).thenThrow(new RuntimeException("Erro ao salvar eleitor"));
            Mockito.when(eleitorService.update(Mockito.any(), Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao atualizar eleitor"));
            Mockito.when(eleitorService.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao buscar eleitor"));
            Mockito.when(eleitorService.findAll()).thenThrow(new RuntimeException("Erro ao buscar todos os eleitores"));
            Mockito.when(eleitorService.delete(Mockito.anyLong())).thenThrow(new RuntimeException("Erro ao deletar eleitor"));
        }
    }

    private void verificarResposta(ResponseEntity<?> retorno, Object esperado, HttpStatus statusEsperado) {
        assertEquals(esperado, retorno.getBody());
        assertEquals(statusEsperado, retorno.getStatusCode());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - salvar eleitor com sucesso")
    void cenario01() {
        ResponseEntity<String> retorno = eleitorController.save(eleitor);
        verificarResposta(retorno, "Eleitor salvo com sucesso!", HttpStatus.OK);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - erro ao salvar eleitor")
    void cenario02() {
        configurarMocks("erro");
        ResponseEntity<String> retorno = eleitorController.save(eleitor);
        verificarResposta(retorno, "Erro ao salvar eleitor", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - atualizar eleitor com sucesso")
    void cenario03() {
        eleitor.setNome("Carlos");
        ResponseEntity<String> retorno = eleitorController.update(eleitor, 1L);
        verificarResposta(retorno, "Eleitor atualizado com sucesso!", HttpStatus.OK);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - erro ao atualizar eleitor")
    void cenario04() {
        configurarMocks("erro");
        ResponseEntity<String> retorno = eleitorController.update(eleitor, 1L);
        verificarResposta(retorno, "Erro ao atualizar eleitor", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar eleitor por ID com sucesso")
    void cenario05() {
        ResponseEntity<Eleitor> retorno = eleitorController.findById(1L);
        verificarResposta(retorno, eleitor, HttpStatus.OK);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - erro ao buscar eleitor por ID")
    void cenario06() {
        configurarMocks("erro");
        ResponseEntity<Eleitor> retorno = eleitorController.findById(1L);
        verificarResposta(retorno, null, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar todos os eleitores com sucesso")
    void cenario07() {
        ResponseEntity<List<Eleitor>> retorno = eleitorController.findAll();
        verificarResposta(retorno, eleitores, HttpStatus.OK);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - erro ao buscar todos os eleitores")
    void cenario08() {
        configurarMocks("erro");
        ResponseEntity<List<Eleitor>> retorno = eleitorController.findAll();
        verificarResposta(retorno, null, HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - deletar eleitor com sucesso")
    void cenario09() {
        ResponseEntity<String> retorno = eleitorController.delete(1L);
        verificarResposta(retorno, "Eleitor deletado com sucesso!", HttpStatus.OK);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - erro ao deletar eleitor")
    void cenario10() {
        configurarMocks("erro");
        ResponseEntity<String> retorno = eleitorController.delete(1L);
        verificarResposta(retorno, "Erro ao deletar eleitor", HttpStatus.BAD_REQUEST);
    }
    
    @Test
    @DisplayName("ERRO - salvar eleitor com status não nulo")
    void cenario11() {
        Eleitor eleitorComStatus = new Eleitor();
        eleitorComStatus.setStatus("ATIVO");

        ResponseEntity<String> retorno = eleitorController.save(eleitorComStatus);

        verificarResposta(retorno, "status deve ser nulo", HttpStatus.FORBIDDEN);
    }
}
