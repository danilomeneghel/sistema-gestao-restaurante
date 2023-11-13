package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.CardapioEntity;

import java.util.List;

public interface CardapioRepository extends JpaRepository<CardapioEntity, Long> {

    List<CardapioEntity> findByNomeContainingIgnoreCase(String nome);

}
