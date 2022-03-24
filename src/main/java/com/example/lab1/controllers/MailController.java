package com.example.lab1.controllers;

import com.example.lab1.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MailController {

    @Autowired
    MailSender mailSender;

    @GetMapping("/")
    public String getMailForm() {
        return "sendMail";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("header") String header,
                              @RequestParam("message") String message,
                              @RequestParam("sender") String sender,
                              @RequestParam(name = "file") MultipartFile file) {
        mailSender.sendMessage(header, message, sender, file);
        return "sendMail";
    }
}
