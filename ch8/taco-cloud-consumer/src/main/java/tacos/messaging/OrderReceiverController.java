package tacos.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.domain.Order;
import tacos.messaging.jms.JmsOrderReceiver;
import tacos.messaging.rabbitMQ.RabbitOrderReceiver;

@RestController
@RequestMapping("/receive")
@RequiredArgsConstructor
public class OrderReceiverController {

  private final JmsOrderReceiver jmsOrderReceiver;
  private final RabbitOrderReceiver rabbitOrderReceiver;

  @GetMapping("/rabbit")
  public Order receiveOrderWithRabbit(Model model) {
    Order order = rabbitOrderReceiver.receiveOrder();

    System.out.println("### Receive order");
    System.out.println(order);

    return order;
  }

  @GetMapping("/jms")
  public Order receiveOrderWithJms(Model model) {
    Order order = jmsOrderReceiver.receiveOrder();

    System.out.println("### Receive order");
    System.out.println(order);

    return order;
  }
  
  
}