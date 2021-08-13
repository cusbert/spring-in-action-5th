package tacos.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.domain.Order;
import tacos.domain.User;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    // jpa 리퍼지터리 커스터마이징
    List<Order> findByDeliveryZip(String deliveryZip);

    List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(
            String deliveryZip, Date StartDate, Date endDate
    );

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
