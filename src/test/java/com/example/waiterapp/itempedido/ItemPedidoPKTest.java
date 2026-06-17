package com.example.waiterapp.itempedido;

import com.example.waiterapp.enums.Estado;
import com.example.waiterapp.item.Item;
import com.example.waiterapp.pedido.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes unitários para ItemPedidoPK")
class ItemPedidoPKTest {

    private static final LocalDateTime DATA = LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0);

    private Item item;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "Pizza", "Pizza napolitana", DATA, 40.0);
        pedido = new Pedido(1L, DATA, Estado.PENDENTE, 0.0, null, null, null);
    }

    @Test
    @DisplayName("getItem e setItem devem funcionar corretamente")
    void item_getterESetter_devemFuncionar() {
        ItemPedidoPK pk = new ItemPedidoPK();
        pk.setItem(item);
        assertEquals(item, pk.getItem());
    }

    @Test
    @DisplayName("getPedido e setPedido devem funcionar corretamente")
    void pedido_getterESetter_devemFuncionar() {
        ItemPedidoPK pk = new ItemPedidoPK();
        pk.setPedido(pedido);
        assertEquals(pedido, pk.getPedido());
    }

    @Test
    @DisplayName("equals deve retornar true para PKs com mesmo item e pedido")
    void equals_mesmoItemEPedido_deveRetornarTrue() {
        ItemPedidoPK pk1 = new ItemPedidoPK();
        pk1.setItem(item);
        pk1.setPedido(pedido);

        ItemPedidoPK pk2 = new ItemPedidoPK();
        pk2.setItem(item);
        pk2.setPedido(pedido);

        assertEquals(pk1, pk2);
    }

    @Test
    @DisplayName("equals deve retornar false para PKs com itens diferentes")
    void equals_itensDiferentes_deveRetornarFalse() {
        Item outroItem = new Item(2L, "Suco", "Suco natural", DATA, 8.0);

        ItemPedidoPK pk1 = new ItemPedidoPK();
        pk1.setItem(item);
        pk1.setPedido(pedido);

        ItemPedidoPK pk2 = new ItemPedidoPK();
        pk2.setItem(outroItem);
        pk2.setPedido(pedido);

        assertNotEquals(pk1, pk2);
    }

    @Test
    @DisplayName("hashCode deve ser igual para PKs com mesmo item e pedido")
    void hashCode_mesmoItemEPedido_deveSerIgual() {
        ItemPedidoPK pk1 = new ItemPedidoPK();
        pk1.setItem(item);
        pk1.setPedido(pedido);

        ItemPedidoPK pk2 = new ItemPedidoPK();
        pk2.setItem(item);
        pk2.setPedido(pedido);

        assertEquals(pk1.hashCode(), pk2.hashCode());
    }
}
