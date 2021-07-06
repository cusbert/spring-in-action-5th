package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    // jpa 리퍼지터리 커스터마이징
    List<Order> findByDeliveryZip(String deliveryZip);

    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date StartDate, Date endDate
    );
}
