package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.entity.Registration;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // 根據用戶ID和活動ID查詢報名資料
    Registration findByUserIdAndEventId(Long userId, Long eventId);

    // 根據活動ID查詢所有報名資料
    List<Registration> findByEventId(Long eventId);

    // 根據用戶ID查詢所有報名資料
    List<Registration> findByUserId(Long userId);
}
