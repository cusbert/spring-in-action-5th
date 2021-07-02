package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Taco {
    @NotBlank(message = "Name is required")
    private String name;
    private List<String> ingredients;
}
