package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Role;
import lazybro.company.factorycrm.entity.User;
import lazybro.company.factorycrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Страницу регистрации
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/registration")
    public String registration() {

        return "user-registration";
    }

    /**
     * Регистрацию нового юзера
     *
     * @param user
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/registration")
    public String addUser(User user, Model model) {

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            return "user-registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "redirect:/user";
    }

    /**
     * Получение всех юзеров
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public String userList(Model model) {

        model.addAttribute("users", userRepository.findAll());

        return "user-list";
    }

    /**
     * Информация для изменения юзера
     *
     * @param user
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    /**
     * Изменение юзера
     *
     * @param username
     * @param password
     * @param firstname
     * @param secondname
     * @param email
     * @param phone
     * @param form
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    public String userSave(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String firstname,
                           @RequestParam String secondname,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam(name = "role") String[] form,
                           @RequestParam("userId") User user) {

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setUsername(username);
        user.setFirstname(firstname);
        user.setSecondname(secondname);
        user.setEmail(email);
        user.setPhone(phone);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);

        return "redirect:/user";
    }

    /**
     * Получение всех контактов пользователей
     *
     * @param model
     * @return
     */
    @GetMapping("/user/contact")
    public String getUserContacts(Model model) {

        Iterable<User> employee = userRepository.findAll();

        model.addAttribute("employee", employee);

        return "user-contact";
    }

    /**
     * Получение профиля пользователя
     *
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/user/profile")
    public String getUserProfile(Model model,
                                 @AuthenticationPrincipal User user) {

        model.addAttribute("emp", user);

        return "user-profile";
    }

    /**
     * Информация для изменение профиля
     *
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/user/profile/edit")
    public String getUserProfileEdit(Model model,
                                     @AuthenticationPrincipal User user) {

        model.addAttribute("user", user);

        return "user-profile-edit";
    }

    /**
     * Изменение профиля
     *
     * @param user
     * @param password
     * @param email
     * @param phone
     * @param model
     * @return
     */
    @PostMapping("/user/profile/edit")
    public String updateUserProfile(@AuthenticationPrincipal User user,
                                    @RequestParam String password,
                                    @RequestParam String email,
                                    @RequestParam String phone,
                                    Model model) {

        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setEmail(email);
        user.setPhone(phone);

        userRepository.save(user);

        return "redirect:/user/profile";
    }


}
