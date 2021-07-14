package tacos.web.hateoas;

import org.springframework.hateoas.CollectionModel;

import java.util.List;

public class TacoResources extends CollectionModel<TacoResource> {
    public TacoResources(List<TacoResource> tacoResources) {
        super(tacoResources);
    }
}
