package restaurante.repository;

import restaurante.entity.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

    Optional<FornecedorEntity> findByNome(String nome);

    Optional<FornecedorEntity> findByNomeAndIdNot(String nome, Long id);
    List<FornecedorEntity> findByNomeContainingIgnoreCase(String nome);
    Optional<FornecedorEntity> findByEmail(String email);

}
