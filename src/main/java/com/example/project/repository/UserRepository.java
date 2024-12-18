package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 用戶資料存儲庫接口，繼承自 JpaRepository，提供基本的資料庫操作。
 */
@Repository
@SpringBootApplication(scanBasePackages = "com.example.project")
@EnableJpaRepositories(basePackages = "com.example.project.repository") // 啟用 JPA 存儲庫功能
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根據電子郵件查詢用戶
     * 
     * @param email 用戶的電子郵件
     * @return 用戶的 Optional 包裝結果
     */
    Optional<User> findByEmail(String email);
}
