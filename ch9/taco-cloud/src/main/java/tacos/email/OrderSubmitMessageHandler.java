package tacos.email;

import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder> {

    // 메시지 핸들러를 통해서 타코 클라우드 API 에 주문 post
    private RestTemplate restTemplate;
    private ApiProperties apiProps;

    public OrderSubmitMessageHandler(RestTemplate restTemplate, ApiProperties apiProps) {
        this.restTemplate = restTemplate;
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(EmailOrder emailOrder, MessageHeaders messageHeaders) {
        // restTemplate을 통해 주문을 제출한다.
        // 즉 Order 객체를 제출하여 ApiProperties 객체에 post 요청한다.
        restTemplate.postForObject(apiProps.getUrl(), emailOrder, String.class);
        return null; // 이 핸들러가 플로우의 제일 끝이라는 것을 나타내기 위해 null 반환
    }

}
