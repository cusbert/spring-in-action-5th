package tacos.email;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmailOrder {

    private final String email;
    private List<EmailTaco> tacos = new ArrayList<>();

    public void addTaco(EmailTaco taco) {
        this.tacos.add(taco);
    }

}