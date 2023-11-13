package restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.entity.ImagemEntity;

public interface ImagemRepository extends JpaRepository<ImagemEntity, Long> {

}
