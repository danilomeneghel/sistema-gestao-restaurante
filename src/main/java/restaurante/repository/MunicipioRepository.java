package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurante.entity.EstadoEntity;
import restaurante.entity.MunicipioEntity;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioEntity, Long> {

    List<MunicipioEntity> findByNomeContainingIgnoreCase(String nome);

    List<MunicipioEntity> findByEstado(EstadoEntity estado);

}
