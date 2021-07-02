package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.domain.Order;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }


    @PostMapping
    public String processOrder(@Valid Order order, Errors errors) {

        if (errors.hasErrors()) {
            return "orderForm";  // 에러 시 사용자가 입력 오류 수정할 수 있도록 주문폼으로 돌아감
        }

        log.info("Order submitted : " + order);
        return "redirect:/designs";
    }
}
