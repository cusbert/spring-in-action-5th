package tacos.messaging.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.messaging.OrderReceiver;

@Service
public class JmsOrderReceiver implements OrderReceiver {

  private JmsTemplate jms;

  public JmsOrderReceiver(JmsTemplate jms) {
    this.jms = jms;
  }
  
  @Override
  public Order receiveOrder() {
    return (Order) jms.receiveAndConvert("tacocloud.order.queue");
  }
  
}