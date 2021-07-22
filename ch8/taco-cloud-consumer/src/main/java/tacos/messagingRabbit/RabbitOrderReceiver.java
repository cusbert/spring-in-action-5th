package tacos.messagingRabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tacos.domain.Order;
import tacos.messaging.OrderReceiver;

@Component("templateOrderReceiver")
public class RabbitOrderReceiver implements OrderReceiver {

  private RabbitTemplate rabbit;

  public RabbitOrderReceiver(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public Order receiveOrder() {
    return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
  }
  
}
