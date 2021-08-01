package tacos.messaging;

import tacos.domain.Order;

public interface OrderMessagingService {

    void sendOrder(Order order);

}
