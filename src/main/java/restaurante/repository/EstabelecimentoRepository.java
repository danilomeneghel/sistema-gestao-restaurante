package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurante.entity.EstabelecimentoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<EstabelecimentoEntity, Long> {

    Optional<EstabelecimentoEntity> findByNome(String nome);

    Optional<EstabelecimentoEntity> findByNomeAndIdNot(String nome, Long id);
    List<EstabelecimentoEntity> findByNomeContainingIgnoreCase(String nome);
    Optional<EstabelecimentoEntity> findByEmail(String email);

}
