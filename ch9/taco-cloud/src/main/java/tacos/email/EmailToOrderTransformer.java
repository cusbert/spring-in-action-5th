package tacos.email;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private static final String SUBJECT_KEYWORDS = "TACO ORDER";

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message message)
            throws Exception {
        // 통합 변환기를 사용하여 입력 이메일을 Order 객체로 변환하기
        EmailOrder emailOrder = processPayload(message);

        return MessageBuilder.withPayload(emailOrder);
    }

    private EmailOrder processPayload(Message message) {
        try {
            String subject = message.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email =
                        ((InternetAddress) message.getFrom()[0]).getAddress();
                String content = message.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder emailOrder = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    }
                }

                EmailTaco taco = new EmailTaco(tacoName);
                taco.setIngredients(ingredientCodes);
                emailOrder.addTaco(taco);
            }
        }
        return emailOrder;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (EmailIngredient emailIngredient : allEmailIngredients) {
            String ucIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucIngredientName, emailIngredient.getName()) < 3 ||
                    ucIngredientName.contains(emailIngredient.getName()) ||
                    emailIngredient.getName().contains(ucIngredientName)) {
                return emailIngredient.getCode();
            }
        }
        return null;
    }

    private static EmailIngredient[] allEmailIngredients = new EmailIngredient[] {
            new EmailIngredient("FLTO", "FLOUR TORTILLA"),
            new EmailIngredient("COTO", "CORN TORTILLA"),
            new EmailIngredient("GRBF", "GROUND BEEF"),
            new EmailIngredient("CARN", "CARNITAS"),
            new EmailIngredient("TMTO", "TOMATOES"),
            new EmailIngredient("LETC", "LETTUCE"),
            new EmailIngredient("CHED", "CHEDDAR"),
            new EmailIngredient("JACK", "MONTERREY JACK"),
            new EmailIngredient("SLSA", "SALSA"),
            new EmailIngredient("SRCR", "SOUR CREAM")
    };
}
