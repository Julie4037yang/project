package com.example.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * 用戶實體類，對應資料庫中的用戶表。
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 用戶ID

    @Column(unique = true, nullable = false)
    private String email;  // 用戶的電子郵件，必須唯一且不為空

    // 無參數建構子
    public User() {}

    // 帶參數建構子，初始化用戶電子郵件
    public User(String email) {
        this.email = email;
    }

    /**
     * 獲取用戶ID
     * @return 用戶的ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 設置用戶ID
     * @param id 要設置的用戶ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 獲取用戶的電子郵件
     * @return 用戶的電子郵件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 設置用戶的電子郵件
     * @param email 要設置的電子郵件
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
