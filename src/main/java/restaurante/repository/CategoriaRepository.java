package restaurante.repository;

import restaurante.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNome(String nome);
    List<CategoriaEntity> findByNomeContainingIgnoreCase(String nome);

}
