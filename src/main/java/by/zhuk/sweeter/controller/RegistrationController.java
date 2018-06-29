package by.zhuk.sweeter.controller;

import by.zhuk.sweeter.model.Role;
import by.zhuk.sweeter.model.User;
import by.zhuk.sweeter.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User newUser, Map<String, Object> model) {
        User user = userRepository.findByUsername(newUser.getUsername());

        if (user != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        newUser.setActive(true);
        newUser.setRoles(Collections.singleton(Role.USER));
        userRepository.save(newUser);

        return "redirect:/login";
    }
}