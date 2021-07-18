package tacos;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.data.IngredientRepository;
import tacos.domain.Ingredient;
import tacos.restClient.RestClient;

import java.util.List;

@Slf4j
@SpringBootTest
public class TacoRestClientTests {

    @Autowired
    RestClient restClient;
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    public void fetchIngredientOne() {
        log.info("----------------------- GET -------------------------");
        log.info("GETTING INGREDIENT BY IDE");
        log.info("Ingredient:  " + restClient.getIngredientByIdUsingGetForEntity("FLTO"));
    }

    @Test
    public void fetchIngredients() {
        log.info("----------------------- GET -------------------------");
        log.info("GETTING ALL INGREDIENTS");
        List<Ingredient> ingredients = restClient.getAllIngredients();
        log.info("All ingredients:");
        for (Ingredient ingredient : ingredients) {
            log.info("   - " + ingredient);
        }
    }

    @Test
    public void addAnIngredient() {
        log.info("----------------------- POST -------------------------");
        Ingredient chix = new Ingredient("CHIX", "Shredded Chicken", Ingredient.Type.PROTEIN);
        Ingredient chixAfter = restClient.createIngredient(chix);
        log.info("AFTER=1:  " + chixAfter);
        /*Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
        URI uri = restClient.createIngredientWithURI(beefFajita);
        log.info("AFTER-2:  " + uri);*/
        Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
        Ingredient shrimpAfter = restClient.createIngredient(shrimp);
        log.info("AFTER-3:  " + shrimpAfter);

        // 원복
        ingredientRepository.deleteById(chix.getId());
        ingredientRepository.deleteById(shrimp.getId());
        // ingredientRepository.deleteById(beefFajita.getId());
    }

    @Test
    public void putAnIngredient() {
        log.info("----------------------- PUT -------------------------");
        Ingredient before = restClient.getIngredientById("LETC");
        log.info("BEFORE:  " + before);
        restClient.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
        Ingredient after = restClient.getIngredientById("LETC");
        log.info("AFTER:  " + after);
    }

    @Test
    public void deleteAnIngredient() {
        log.info("----------------------- DELETE -------------------------");

        Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
        ingredientRepository.save(shrimp);

        Ingredient before = restClient.getIngredientById("SHMP");
        log.info("BEFORE:  " + before);
        restClient.deleteIngredient(before);

    }

}
