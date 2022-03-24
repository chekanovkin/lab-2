package com.example.lab1.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class MailSender {

    @Autowired
    @Qualifier("getMailSenderYandex")
    JavaMailSender yandex;

    @Autowired
    @Qualifier("getMailSenderGmail")
    JavaMailSender gmail;

    @Autowired
    private StorageService storageService;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String header, String message, String sender, MultipartFile attachment) throws MessagingException {
        File file = storageService.convertMultipartFile(attachment);
        List<String[]> lines = CSVReadService.readCSV(file.getName());
        if (sender.equals("Yandex")) {
            for (String[] arr : lines) {
                MimeMessage mimeMessage = yandex.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom(username);
                messageHelper.setTo(arr[0]);
                messageHelper.setSubject(header);
                messageHelper.setText(message);
                for (int i = 1; i < arr.length; i++) {
                    File data = storageService.downloadFile("doc2.docx");
                    messageHelper.addAttachment(arr[i], data);
                }
                yandex.send(mimeMessage);
            }
        } else {
            MimeMessage mimeMessage = gmail.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            for (String[] arr : lines) {
                messageHelper.setFrom(username);
                messageHelper.setTo(arr[0]);
                messageHelper.setSubject(header);
                messageHelper.setText(message);
                for (int i = 1; i < arr.length; i++) {
                    File data = storageService.downloadFile("doc1.docx");
                    messageHelper.addAttachment(arr[i], data);
                }
                gmail.send(mimeMessage);
            }
        }
    }

    public void sendMessage(String header, String message, String sender, MultipartFile attachment) {
        if (!ObjectUtils.isEmpty(attachment)) {
            try {
                send(header, message, sender, attachment);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
