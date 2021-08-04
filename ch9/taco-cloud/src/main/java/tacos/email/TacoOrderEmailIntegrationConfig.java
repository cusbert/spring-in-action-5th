package tacos.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.dsl.Mail;

@Configuration
public class TacoOrderEmailIntegrationConfig {

    // 이메일을 받아 주문으로 제출하기 위해 통합 플로우 정의하기
    @Bean
    public IntegrationFlow tacoOrderEmailFlow(
            EmailProperties emailProperties,
            EmailToOrderTransformer emailToOrderTransformer,
            OrderSubmitMessageHandler orderSubmitMessageHandler
    ) {
        return IntegrationFlows
                .from(Mail.imapInboundAdapter(emailProperties.getImapUrl()),
                        e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(emailToOrderTransformer)
                .handle(orderSubmitMessageHandler)
                .get();
    }
}
