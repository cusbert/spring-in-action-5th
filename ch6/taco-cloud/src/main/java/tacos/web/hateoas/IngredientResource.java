package tacos.web.hateoas;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;

public class IngredientResource extends RepresentationModel<IngredientResource> {

  @Getter
  private String name;

  @Getter
  private Type type;
  
  public IngredientResource(Ingredient ingredient) {
    this.name = ingredient.getName();
    this.type = ingredient.getType();
  }

}
