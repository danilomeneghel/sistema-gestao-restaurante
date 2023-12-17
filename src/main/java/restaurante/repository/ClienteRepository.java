package restaurante.repository;

import restaurante.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByNome(String nome);

    List<ClienteEntity> findByNomeContainingIgnoreCase(String nome);

    Optional<ClienteEntity> findByNomeAndIdNot(String nome, Long id);

    Optional<ClienteEntity> findByEmail(String email);

}
