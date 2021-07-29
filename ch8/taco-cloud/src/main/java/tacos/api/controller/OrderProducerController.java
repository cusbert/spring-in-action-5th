package tacos.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Order;
import tacos.messaging.jms.JmsOrderMessagingService;
import tacos.messaging.kafka.KafkaOrderMessagingService;
import tacos.messaging.rabbitMQ.RabbitOrderProducerService;

@RestController
@RequestMapping(path = "/produce", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderProducerController {

    private RabbitOrderProducerService rabbitOrderProducerService;
    private JmsOrderMessagingService jmsOrderMessagingService;
    private KafkaOrderMessagingService kafkaOrderMessagingService;

    public OrderProducerController(RabbitOrderProducerService rabbitOrderProducerService,
                                   JmsOrderMessagingService jmsOrderMessagingService,
                                   KafkaOrderMessagingService kafkaOrderMessagingService) {
        this.rabbitOrderProducerService = rabbitOrderProducerService;
        this.jmsOrderMessagingService = jmsOrderMessagingService;
        this.kafkaOrderMessagingService = kafkaOrderMessagingService;
    }

    @PostMapping(path = "/rabbit", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void produceOrderWithRabbit(@RequestBody Order order) {
        rabbitOrderProducerService.sendOrder(order);
    }

    @PostMapping(path = "/jms", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void produceOrderWithJms(@RequestBody Order order) {
        jmsOrderMessagingService.sendOrder(order);
    }

    @PostMapping(path = "/kafka", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void produceOrderWitKafka(@RequestBody Order order) {
        kafkaOrderMessagingService.sendOrder(order);
    }

}
