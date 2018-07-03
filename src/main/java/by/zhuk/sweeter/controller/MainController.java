package by.zhuk.sweeter.controller;

import by.zhuk.sweeter.model.Message;
import by.zhuk.sweeter.model.User;
import by.zhuk.sweeter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        return "home";

    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages;
        if (filter == null || filter.isEmpty()) {
            messages = messageRepository.findAll();
        } else {
            messages = messageRepository.findByTag(filter);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";

    }

    @PostMapping("add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model
    ) throws IOException {
        Message message = new Message();
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
        message.setTag(tag);
        message.setText(text);
        message.setAuthor(user);
        messageRepository.save(message);
        model.put("messages", messageRepository.findAll());
        return "redirect:/main";
    }


}