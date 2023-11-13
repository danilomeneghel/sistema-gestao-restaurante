package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurante.entity.BairroEntity;
import restaurante.entity.MunicipioEntity;

import java.util.List;

@Repository
public interface BairroRepository extends JpaRepository<BairroEntity, Long> {

    List<BairroEntity> findByNomeContainingIgnoreCase(String nome);

    List<BairroEntity> findByMunicipio(MunicipioEntity municipio);

}
