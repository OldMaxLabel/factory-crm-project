package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Message;
import lazybro.company.factorycrm.entity.User;
import lazybro.company.factorycrm.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessengerController {

    @Autowired
    MessageRepository messageRepository;

    /**
     * Получение всех сообщений + фильтр
     *
     * @param filter
     * @param model
     * @return
     */
    @GetMapping("/messenger")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "messenger";
    }

    /**
     * Опубликовать новое сообщение + фильтр
     *
     * @param user
     * @param text
     * @param tag
     * @param model
     * @return
     */
    @PostMapping("/messenger")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam(name = "text", required = false) String text,
                      @RequestParam(name = "tag", required = false) String tag,
                      Model model) {

        Message message = new Message(text, tag, user);

        if (message.getText() != null && !message.getText().isEmpty()) {
            messageRepository.save(message);
        }

        Iterable<Message> messages = messageRepository.findByTag(tag);
        model.addAttribute("messages", messages);

        model.addAttribute("tag", tag);

        return "messenger";
    }

}
