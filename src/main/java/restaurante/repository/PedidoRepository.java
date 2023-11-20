package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.PedidoEntity;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByStatusTrue();

}
