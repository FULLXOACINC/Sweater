package by.zhuk.sweeter.controller;

import by.zhuk.sweeter.model.Message;
import by.zhuk.sweeter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingsController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Map<String, Object> model) {
        model.put("name", name);
        return "greeting";

    }

    @GetMapping
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main";

    }

    @PostMapping("add")
    public String add(@RequestParam String text
            , @RequestParam String tag, Map<String, Object> model) {
        messageRepository.save(new Message(text, tag));
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("filter")
    public String add(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages ;
        if(filter==null || filter.isEmpty()){
            messages=messageRepository.findAll();
        }else {
            messages=messageRepository.findByTag(filter);
        }
        model.put("messages", messages);
        return "main";
    }
}