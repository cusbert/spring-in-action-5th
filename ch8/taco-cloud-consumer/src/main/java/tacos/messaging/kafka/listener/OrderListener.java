package tacos.messaging.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import tacos.domain.Order;
import tacos.messaging.KitchenUI;

@Component
@Slf4j
public class OrderListener {

    private KitchenUI kitchenUI;

    @Autowired
    public OrderListener(KitchenUI kitchenUI) {
        this.kitchenUI = kitchenUI;
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order) {
        kitchenUI.displayOrder(order);
    }

    // record 부가정보 받기
    @KafkaListener(topics="tacocloud.orders.topic")
    public void handle(Order order, ConsumerRecord<String, Order> record) {

        log.info("Received from partition {} with timestamp {}",
                record.partition(), record.timestamp());

        kitchenUI.displayOrder(order);
    }

    // Message 부가정보 받기
    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order, Message<Order> message) {

        MessageHeaders headers = message.getHeaders();
        log.info("Received from partition {} with timestamp {}",
                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));

        kitchenUI.displayOrder(order);
    }
}
