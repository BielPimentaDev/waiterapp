package com.example.waiterapp.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para a entidade Cliente")
class ClienteTest {

    private static final LocalDateTime DATA = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0);

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Ana Silva", "ana@test.com", "11122233344", DATA);
    }

    @Test
    @DisplayName("construtor com argumentos deve inicializar todos os campos")
    void construtor_comArgumentos_deveInicializarCampos() {
        assertEquals(1L, cliente.getId());
        assertEquals("Ana Silva", cliente.getNome());
        assertEquals("ana@test.com", cliente.getEmail());
        assertEquals("11122233344", cliente.getCpf());
        assertEquals(DATA, cliente.getDataCriacao());
    }

    @Test
    @DisplayName("construtor padrão deve criar instância válida")
    void construtor_padrao_deveCriarInstancia() {
        Cliente c = new Cliente();
        assertNotNull(c);
    }

    @Test
    @DisplayName("setters devem atualizar os campos corretamente")
    void setters_devemAtualizarCampos() {
        cliente.setId(2L);
        cliente.setNome("Maria");
        cliente.setEmail("maria@test.com");
        cliente.setCpf("99988877766");
        cliente.setDataCriacao(DATA.plusDays(1));

        assertEquals(2L, cliente.getId());
        assertEquals("Maria", cliente.getNome());
        assertEquals("maria@test.com", cliente.getEmail());
        assertEquals("99988877766", cliente.getCpf());
        assertEquals(DATA.plusDays(1), cliente.getDataCriacao());
    }

    @Test
    @DisplayName("equals deve retornar true para clientes com mesmo id")
    void equals_mesmoId_deveRetornarTrue() {
        Cliente outro = new Cliente(1L, "Outro Nome", "outro@test.com", "00000000000", DATA);
        assertEquals(cliente, outro);
    }

    @Test
    @DisplayName("equals deve retornar false para clientes com ids diferentes")
    void equals_idsDiferentes_deveRetornarFalse() {
        Cliente outro = new Cliente(2L, "Ana Silva", "ana@test.com", "11122233344", DATA);
        assertNotEquals(cliente, outro);
    }

    @Test
    @DisplayName("hashCode deve ser igual para clientes com mesmo id")
    void hashCode_mesmoId_deveSerIgual() {
        Cliente outro = new Cliente(1L, "Outro", "outro@test.com", "00000000000", DATA);
        assertEquals(cliente.hashCode(), outro.hashCode());
    }

    @Test
    @DisplayName("toString deve conter o nome do cliente")
    void toString_deveConterNome() {
        assertTrue(cliente.toString().contains("Ana Silva"));
    }

    @Test
    @DisplayName("getPedidos e setPedidos devem funcionar corretamente")
    void pedidos_getterESetter_devemFuncionar() {
        cliente.setPedidos(new ArrayList<>());
        assertNotNull(cliente.getPedidos());
        assertTrue(cliente.getPedidos().isEmpty());
    }
}
