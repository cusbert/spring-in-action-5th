package tacos.restClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.domain.Ingredient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RestClient {

    private RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Ingredient getIngredientById(String id) {
        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                id);
    }

    /* GET */

    // Map을 사용하새 URL 변수 저장
    public Ingredient getIngredientByIdWithMap(String id) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", id);
        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                urlVariables);
    }

    // URI 매개변수를 사용하기
    public Ingredient getIngredientByIdWithURI(String id) {
        Map<String, String> urlVariables = new HashMap<>();
        urlVariables.put("id", id);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(urlVariables);
        return restTemplate.getForObject(url, Ingredient.class);
    }

    // getForEntity
    public Ingredient getIngredientByIdUsingGetForEntity(String id) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/ingredients/{id}",
                        Ingredient.class,
                        id);
        log.info("Fetched time: " +
                responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    // exchange
    public List<Ingredient> getAllIngredients() {
        return restTemplate.exchange("http://localhost:8080/ingredients",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Ingredient>>() {
                })
                .getBody();
    }


    /* POST */
    // postForObject
    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients",
                ingredient,
                Ingredient.class);
    }

    // postForLocation
    public URI createIngredientWithURI(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/ingredients",
                ingredient,
                Ingredient.class);
    }

    // postForEntity
    public Ingredient createIngredientWithPostForEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/ingredients",
                        ingredient,
                        Ingredient.class);
        log.info("New resource created at " +
                responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }

    /* PUT */
    // put
    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/ingredients/{id}",
                ingredient,
                ingredient.getId());
    }

    /* DELETE */
    // delete
    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}",
                ingredient.getId());
    }
}
