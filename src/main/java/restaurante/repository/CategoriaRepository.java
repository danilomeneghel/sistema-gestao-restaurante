package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.CategoriaEntity;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    List<CategoriaEntity> findByNomeContainingIgnoreCase(String nome);

}
