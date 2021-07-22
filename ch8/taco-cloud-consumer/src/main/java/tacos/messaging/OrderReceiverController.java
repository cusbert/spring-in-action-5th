package tacos.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tacos.domain.Order;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderReceiverController {

  private final OrderReceiver orderReceiver;

  @GetMapping("/receive")
  public Order receiveOrder(Model model) {
    Order order = orderReceiver.receiveOrder();

    System.out.println("### order");
    System.out.println(order);

    return order;
  }
  
  
}