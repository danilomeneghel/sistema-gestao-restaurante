package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.CardapioItemEntity;
import restaurante.entity.CategoriaEntity;

import java.util.List;

public interface CardapioItemRepository extends JpaRepository<CardapioItemEntity, Long> {

    List<CardapioItemEntity> findByNomeContainingIgnoreCase(String nome);
    List<CardapioItemEntity> findByCategoria(CategoriaEntity categoriaEntity);

}
