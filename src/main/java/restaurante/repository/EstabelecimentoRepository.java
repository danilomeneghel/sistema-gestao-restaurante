package restaurante.repository;

import restaurante.entity.EstabelecimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<EstabelecimentoEntity, Long> {

    Optional<EstabelecimentoEntity> findByNome(String nome);

    Optional<EstabelecimentoEntity> findByNomeAndIdNot(String nome, Long id);

    Optional<EstabelecimentoEntity> findByEmail(String email);

}
