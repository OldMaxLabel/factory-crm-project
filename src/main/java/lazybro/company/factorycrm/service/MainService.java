package lazybro.company.factorycrm.service;

import org.springframework.stereotype.Controller;

import java.time.LocalTime;

@Controller
public class MainService {

    public String getGreeting(LocalTime time) {

        String greeting;

        if (time.isAfter(LocalTime.of(4, 00)) &&time.isBefore(LocalTime.of(12, 00))) {
            greeting = "Доброе утро!";
        } else if (time.isAfter(LocalTime.of(12, 00)) && time.isBefore(LocalTime.of(16, 00))) {
            greeting = "Добрый день!";
        } else if (time.isAfter(LocalTime.of(00, 00)) && time.isBefore(LocalTime.of(4, 00))) {
            greeting = "Доброй ночи!";
        } else {
            greeting = "Добрый вечер!";
        }

        return greeting;
    }

    public String getAction(LocalTime time) {

        String action;

        if (time.isAfter(LocalTime.of(9, 00)) &&time.isBefore(LocalTime.of(12, 00))) {
            action = "рабочий процесс набирает обороты!";
        } else if (time.isAfter(LocalTime.of(12, 00)) && time.isBefore(LocalTime.of(13, 00))) {
            action = "обед!";
        } else if (time.isAfter(LocalTime.of(13, 00)) && time.isBefore(LocalTime.of(18, 00))) {
            action = "рабочий процесс в полном разгаре!";
        } else {
            action = "все отдыхают!";
        }

        return action;
    }

}
