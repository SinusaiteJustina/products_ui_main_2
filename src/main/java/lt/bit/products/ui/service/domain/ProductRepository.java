package lt.bit.products.ui.service.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

  List<ProductEntity> findByNameContainingOrIdIs(
      @Param("name") String name,
      @Param("id") UUID uuid
  );

  List<ProductEntity> findByNameContaining(@Param("name") String name);

  @Query("select p from ProductEntity p where (:id is null or p.id = :id) and (:name is null or p.name like %:name%)")
  List<ProductEntity> findByNameAndIdOptional(
      @Param("name") String name,
      @Param("id") UUID uuid
  );
  @Modifying
  void deleteAllBySupplierId(UUID supplierId);
}
