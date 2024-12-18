package com.example.project.repository;

import com.example.project.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 事件資料存取介面，繼承自 JpaRepository 用於 CRUD 操作
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
