package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.*;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByStatusTrue();

    List<PedidoEntity> findByStatusTrueAndCardapioItems(List<CardapioItemEntity> cardapioItemsEntities);

    List<PedidoEntity> findByCardapioItemsNome(String cardapioNome);

}
