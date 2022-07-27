package lt.bit.products.ui.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Modifying
    @Query("update OrderEntity o set o.status = ?1 where o.id = ?2")
    void updateStatus(OrderStatus status, String id);

}
