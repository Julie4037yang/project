package com.example.project.entity;

import jakarta.persistence.*;

// 註冊實體類別
@Entity
public class Registration {

    @Id  // 註明主鍵
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自動生成主鍵
    private Long id;

    @ManyToOne  // 多對一關聯，表示多個註冊對應一個活動
    @JoinColumn(name = "event_id")  // 設定活動外鍵
    private Event event;

    @ManyToOne  // 多對一關聯，表示多個註冊對應一個使用者
    @JoinColumn(name = "user_id")  // 設定使用者外鍵
    private User user;

    private String name;  // 註冊者姓名
    private String phone;  // 註冊者電話
    private String email;  // 註冊者電子郵件

    // 取得註冊 ID
    public Long getId() {
        return id;
    }

    // 設定註冊 ID
    public void setId(Long id) {
        this.id = id;
    }

    // 取得活動
    public Event getEvent() {
        return event;
    }

    // 設定活動
    public void setEvent(Event event) {
        this.event = event;
    }

    // 取得使用者
    public User getUser() {
        return user;
    }

    // 設定使用者
    public void setUser(User user) {
        this.user = user;
    }

    // 取得姓名
    public String getName() {
        return name;
    }

    // 設定姓名
    public void setName(String name) {
        this.name = name;
    }

    // 取得電話
    public String getPhone() {
        return phone;
    }

    // 設定電話
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 取得電子郵件
    public String getEmail() {
        return email;
    }

    // 設定電子郵件
    public void setEmail(String email) {
        this.email = email;
    }
}
