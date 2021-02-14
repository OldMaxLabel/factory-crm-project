package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class MainController {

    @Autowired
    private MainService mainService;

    /**
     * Главная страница
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String main(Model model) {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);


        String greeting = mainService.getGreeting(time);

        String action = mainService.getAction(time);

        model.addAttribute("greeting", greeting);
        model.addAttribute("action", action);
        model.addAttribute("date", date);
        model.addAttribute("time", time);

        return "main";
    }

    /**
     * Информация о разработчике
     *
     * @return
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
