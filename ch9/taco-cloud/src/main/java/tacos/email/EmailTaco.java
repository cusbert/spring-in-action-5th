package tacos.email;

import java.util.List;

import lombok.Data;

@Data
public class EmailTaco {

  private final String name;
  private List<String> ingredients;
  
}
