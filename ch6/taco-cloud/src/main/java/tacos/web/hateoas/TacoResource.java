package tacos.web.hateoas;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import tacos.domain.Taco;

import java.util.Date;

@Relation(value="taco", collectionRelation="tacos")
public class TacoResource extends RepresentationModel<TacoResource> {

  private static final IngredientResourceAssembler
            ingredientAssembler = new IngredientResourceAssembler();
  
  @Getter
  private final String name;

  @Getter
  private final Date createdAt;

  
  public TacoResource(Taco taco) {
    this.name = taco.getName();
    this.createdAt = taco.getCreatedAt();
  }
  
}
