package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.*;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByStatusTrue();

    List<PedidoEntity> findByStatusTrueAndCategoria(CategoriaEntity categoriaEntity);

    List<PedidoEntity> findByCardapio(CardapioEntity cardapioEntity);

    List<PedidoEntity> findByProduto(ProdutoEntity produtoEntity);

    List<PedidoEntity> findByPrato(CardapioItemEntity cardapioItemEntity);

    List<PedidoEntity> findByBairro(BairroEntity bairroEntity);

}
