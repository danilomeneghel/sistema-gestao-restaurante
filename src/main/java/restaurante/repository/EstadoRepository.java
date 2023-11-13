package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurante.entity.EstadoEntity;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoEntity, Long> {

    List<EstadoEntity> findByNomeContainingIgnoreCase(String nome);

}
