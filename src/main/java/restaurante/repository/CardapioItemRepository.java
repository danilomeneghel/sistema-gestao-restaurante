package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.CardapioItemEntity;

import java.util.List;

public interface CardapioItemRepository extends JpaRepository<CardapioItemEntity, Long> {

    List<CardapioItemEntity> findByNomeContainingIgnoreCase(String nome);

}
