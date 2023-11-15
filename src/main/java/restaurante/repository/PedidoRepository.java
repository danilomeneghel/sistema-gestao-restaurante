package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.CardapioItemEntity;
import restaurante.entity.CategoriaEntity;
import restaurante.entity.PedidoEntity;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByStatusTrue();

    List<PedidoEntity> findByStatusTrueAndCardapioItemCategoria(CategoriaEntity categoriaEntity);

    List<PedidoEntity> findByCardapioItemNome(String cardapioNome);

}
