package tacos.messaging;

import org.springframework.stereotype.Component;
import tacos.domain.Order;

public interface OrderReceiver {

  Order receiveOrder();

}