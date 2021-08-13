package tacos.messaging.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.domain.Order;
import tacos.messaging.OrderMessagingService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /* MessageCreator 사용할 때
    @Override
    public void sendOrder(Order order) {
        // Order로 부터 새로운 메시지 생성
        jmsTemplate.send(session -> session.createObjectMessage(order));
    }
    */

    /* 기본 도착지를 send 의 파라미터로 전달할 때
    public void sendOrder(Order order) {
        // Order로 부터 새로운 메시지 생성
        jmsTemplate.send(
                "tacocloud.order.queue",
                session -> session.createObjectMessage(order));
    }
    */

    // convertAndSend() 로 전성될 객체 인자로 직접 전달
    /*@Override
    public void sendOrder(Order order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }*/

    @Override
    public void sendOrder(Order order) {
       /* jmsTemplate.convertAndSend("tacocloud.order.queue", order,
                this::addOrderSource);*/
        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }

    private Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
