package tacos;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import tacos.domain.Ingredient;
import tacos.restClient.RestClient;

import java.net.URI;
import java.util.List;

@Slf4j
@SpringBootTest
public class TacoRestClientTests {

    @Autowired
    RestClient restClient;

    @Test
    public void fetchIngredients() {
        log.info("----------------------- GET -------------------------");
        log.info("GETTING INGREDIENT BY IDE");
        log.info("Ingredient:  " + restClient.getIngredientById("FLTO"));
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
        Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
        URI uri = restClient.createIngredientWithURI(beefFajita);
        log.info("AFTER-2:  " + uri);
//      Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
//      Ingredient shrimpAfter = tacoCloudClient.createIngredient(shrimp);
//      log.info("AFTER-3:  " + shrimpAfter);
    }

    @Test
    public void putAnIngredient(RestClient tacoCloudClient) {
        log.info("----------------------- PUT -------------------------");
        Ingredient before = tacoCloudClient.getIngredientById("LETC");
        log.info("BEFORE:  " + before);
        tacoCloudClient.updateIngredient(new Ingredient("LETC", "Shredded Lettuce", Ingredient.Type.VEGGIES));
        Ingredient after = tacoCloudClient.getIngredientById("LETC");
        log.info("AFTER:  " + after);
    }

    @Test
    public void deleteAnIngredient(RestClient tacoCloudClient) {
        log.info("----------------------- DELETE -------------------------");
        // start by adding a few ingredients so that we can delete them later...
        Ingredient beefFajita = new Ingredient("BFFJ", "Beef Fajita", Ingredient.Type.PROTEIN);
        tacoCloudClient.createIngredient(beefFajita);
        Ingredient shrimp = new Ingredient("SHMP", "Shrimp", Ingredient.Type.PROTEIN);
        tacoCloudClient.createIngredient(shrimp);


        Ingredient before = tacoCloudClient.getIngredientById("CHIX");
        log.info("BEFORE:  " + before);
        tacoCloudClient.deleteIngredient(before);
        Ingredient after = tacoCloudClient.getIngredientById("CHIX");
        log.info("AFTER:  " + after);
        before = tacoCloudClient.getIngredientById("BFFJ");
        log.info("BEFORE:  " + before);
        tacoCloudClient.deleteIngredient(before);
        after = tacoCloudClient.getIngredientById("BFFJ");
        log.info("AFTER:  " + after);
        before = tacoCloudClient.getIngredientById("SHMP");
        log.info("BEFORE:  " + before);
        tacoCloudClient.deleteIngredient(before);
        after = tacoCloudClient.getIngredientById("SHMP");
        log.info("AFTER:  " + after);
    }

}
