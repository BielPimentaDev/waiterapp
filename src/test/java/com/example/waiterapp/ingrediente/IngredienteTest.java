package com.example.waiterapp.ingrediente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a entidade Ingrediente")
class IngredienteTest {

    private static final LocalDateTime DATA = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0);

    private Ingrediente ingrediente;

    @BeforeEach
    void setUp() {
        ingrediente = new Ingrediente(1L, "Tomate", "Tomate fresco", DATA, 18.0f);
    }

    @Test
    @DisplayName("construtor com argumentos deve inicializar todos os campos")
    void construtor_comArgumentos_deveInicializarCampos() {
        assertEquals(1L, ingrediente.getId());
        assertEquals("Tomate", ingrediente.getNome());
        assertEquals("Tomate fresco", ingrediente.getDescricao());
        assertEquals(DATA, ingrediente.getDataCriacao());
        assertEquals(18.0f, ingrediente.getCaloria());
    }

    @Test
    @DisplayName("construtor padrão deve criar instância válida")
    void construtor_padrao_deveCriarInstancia() {
        Ingrediente i = new Ingrediente();
        assertNotNull(i);
    }

    @Test
    @DisplayName("setters devem atualizar os campos corretamente")
    void setters_devemAtualizarCampos() {
        ingrediente.setId(2L);
        ingrediente.setNome("Cebola");
        ingrediente.setDescricao("Cebola roxa");
        ingrediente.setDataCriacao(DATA.plusDays(1));
        ingrediente.setCaloria(40.0f);

        assertEquals(2L, ingrediente.getId());
        assertEquals("Cebola", ingrediente.getNome());
        assertEquals("Cebola roxa", ingrediente.getDescricao());
        assertEquals(DATA.plusDays(1), ingrediente.getDataCriacao());
        assertEquals(40.0f, ingrediente.getCaloria());
    }

    @Test
    @DisplayName("equals deve retornar true para ingredientes com mesmo id")
    void equals_mesmoId_deveRetornarTrue() {
        Ingrediente outro = new Ingrediente(1L, "Outro", "desc", DATA, 5.0f);
        assertEquals(ingrediente, outro);
    }

    @Test
    @DisplayName("equals deve retornar false para ingredientes com ids diferentes")
    void equals_idsDiferentes_deveRetornarFalse() {
        Ingrediente outro = new Ingrediente(2L, "Tomate", "Tomate fresco", DATA, 18.0f);
        assertNotEquals(ingrediente, outro);
    }

    @Test
    @DisplayName("hashCode deve ser igual para ingredientes com mesmo id")
    void hashCode_mesmoId_deveSerIgual() {
        Ingrediente outro = new Ingrediente(1L, "Outro", "desc", DATA, 5.0f);
        assertEquals(ingrediente.hashCode(), outro.hashCode());
    }

    @Test
    @DisplayName("toString deve conter o nome do ingrediente")
    void toString_deveConterNome() {
        assertTrue(ingrediente.toString().contains("Tomate"));
    }

    @Test
    @DisplayName("getPratos e setPratos devem funcionar corretamente")
    void pratos_getterESetter_devemFuncionar() {
        ingrediente.setPratos(new ArrayList<>());
        assertNotNull(ingrediente.getPratos());
        assertTrue(ingrediente.getPratos().isEmpty());
    }
}
