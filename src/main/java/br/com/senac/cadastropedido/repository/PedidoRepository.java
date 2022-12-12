package br.com.senac.cadastropedido.repository;

import br.com.senac.cadastropedido.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
