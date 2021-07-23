package tacos.messaging.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.messaging.OrderReceiver;

@Service
public class RabbitOrderReceiver implements OrderReceiver {

  private RabbitTemplate rabbit;

  public RabbitOrderReceiver(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }
  
  public Order receiveOrder() {
    System.out.println("@@@@@@@@@@@@@@");
    return (Order) rabbit.receiveAndConvert("tacocloud.order.queue");
  }
  
}
