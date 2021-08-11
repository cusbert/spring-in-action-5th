package springInAction5.reactorfun;

import java.time.Duration;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

public class FluxMergingTests {

  @Test
  public void mergeFluxes() {
    Flux<String> characterFlux = Flux
            .just("Tony", "Steve", "Thor")
            .delayElements(Duration.ofMillis(500));  // 500ms 마다 데이터 방출

    Flux<String> foodFlux = Flux
            .just("Lasagna", "Lollipops", "Apples")
            .delaySubscription(Duration.ofMillis(250)) // foodFlux는 characterFlux 250ms 다음에 시작
            .delayElements(Duration.ofMillis(500)); // 500ms 마다 데이터 방출
    
    Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

    StepVerifier.create(mergedFlux)
        .expectNext("Tony")
        .expectNext("Lasagna")
        .expectNext("Steve")
        .expectNext("Lollipops")
        .expectNext("Thor")
        .expectNext("Apples")
        .verifyComplete();
  }
  
  @Test
  public void zipFluxes() {
    Flux<String> characterFlux = Flux
            .just("Tony", "Steve", "Thor");
    Flux<String> foodFlux = Flux
            .just("Lasagna", "Lollipops", "Apples");
    
    Flux<Tuple2<String, String>> zippedFlux = 
        Flux.zip(characterFlux, foodFlux);
    
    StepVerifier.create(zippedFlux)
          .expectNextMatches(p -> 
              p.getT1().equals("Tony") &&
              p.getT2().equals("Lasagna"))
          .expectNextMatches(p -> 
              p.getT1().equals("Steve") &&
              p.getT2().equals("Lollipops"))
          .expectNextMatches(p -> 
              p.getT1().equals("Thor") &&
              p.getT2().equals("Apples"))
          .verifyComplete();
  }
  
  @Test
  public void zipFluxesToObject() {
    Flux<String> characterFlux = Flux
            .just("Tony", "Steve", "Thor");
    Flux<String> foodFlux = Flux
            .just("Lasagna", "Lollipops", "Apples");
    
    Flux<String> zippedFlux = 
        Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);
    
    StepVerifier.create(zippedFlux)
          .expectNext("Tony eats Lasagna")
          .expectNext("Steve eats Lollipops")
          .expectNext("Thor eats Apples")
          .verifyComplete();
  }
  
  
  @Test
  public void firstFlux() {
    // delay needed to "slow down" the slow Flux
    
    Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
          .delaySubscription(Duration.ofMillis(100));
    Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
    
    Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);
    
    StepVerifier.create(firstFlux)
        .expectNext("hare")
        .expectNext("cheetah")
        .expectNext("squirrel")
        .verifyComplete();
  }

}
