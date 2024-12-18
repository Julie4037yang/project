package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // 注入 JavaMailSender 用於發送郵件
    @Autowired
    private JavaMailSender javaMailSender;

    // 發送電子郵件的方法
    public void sendEmail(String to, String subject, String text) {
        // 創建簡單的郵件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);           // 設置收件人
        message.setSubject(subject); // 設置郵件主題
        message.setText(text);       // 設置郵件內容
        message.setFrom("welcometothissystem@gmail.com"); // 設置發件人地址
        javaMailSender.send(message); // 發送郵件
    }
}
