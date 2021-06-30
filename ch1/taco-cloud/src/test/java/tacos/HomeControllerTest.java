package tacos;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc 주입

    @Test
    public void testHomepage() throws Exception {
        mockMvc.perform(get("/")) // get 수행
            .andExpect(status().isOk()) // HTTP 200 OK 리턴
            .andExpect(view().name("home")) // home.html view
            .andExpect(content().string(containsString("Welcome to..."))); // Welcome to... 텍스트 포함
    }
}
