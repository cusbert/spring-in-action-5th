package tacos.web.hateoas;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.domain.Taco;
import tacos.web.api.DesignTacoController;

public class TacoResourceAssembler
       extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

  public TacoResourceAssembler() {
    super(DesignTacoController.class, TacoResource.class);
  }

  @Override
  protected TacoResource instantiateModel(Taco entity) {
    return new TacoResource(entity);
  }

  @Override
  public TacoResource toModel(Taco entity) {
    return createModelWithId(entity.getId(), entity);
  }


}
