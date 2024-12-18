package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 活動ID

    @Column(nullable = false)
    private String eventName; // 活動名稱

    @Column(nullable = false)
    private String posterUrl; // 活動海報URL

    @Column(nullable = false)
    private String eventDate; // 活動日期

    @Column(nullable = false)
    private LocalTime startTime; // 活動開始時間

    @Column(nullable = false)
    private LocalTime endTime; // 活動結束時間

    @Column(nullable = false)
    private String location; // 活動地點

    @Column(nullable = false)
    private String organizer; // 主辦單位

    @Column(nullable = false)
    private String contactInfo; // 聯絡資訊

    @Column(nullable = false)
    private int registeredCount; // 已報名人數

    @Column(nullable = false)
    private int capacity; // 活動容量

    @Column(nullable = false)
    private String description; // 活動描述

    // 無參數建構子
    public Event() {}

    // 有參數建構子，初始化活動的所有屬性
    public Event(String eventName, String posterUrl, String eventDate, LocalTime startTime, LocalTime endTime, 
                 String location, String organizer, String contactInfo, int registeredCount, int capacity, String description) {
        this.eventName = eventName;
        this.posterUrl = posterUrl;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.organizer = organizer;
        this.contactInfo = contactInfo;
        this.registeredCount = registeredCount;
        this.capacity = capacity;
        this.description = description;
    }

    // 取得活動ID
    public Long getId() {
        return id;
    }

    // 設定活動ID
    public void setId(Long id) {
        this.id = id;
    }

    // 取得活動名稱
    public String getEventName() {
        return eventName;
    }

    // 設定活動名稱
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // 取得海報URL
    public String getPosterUrl() {
        return posterUrl;
    }

    // 設定海報URL
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    // 取得活動日期
    public String getEventDate() {
        return eventDate;
    }

    // 設定活動日期
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    // 取得活動開始時間，格式為 "HH:mm"
    public String getStartTime() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // 設定活動開始時間
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // 取得活動結束時間，格式為 "HH:mm"
    public String getEndTime() {
        return endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // 設定活動結束時間
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    // 取得活動地點
    public String getLocation() {
        return location;
    }

    // 設定活動地點
    public void setLocation(String location) {
        this.location = location;
    }

    // 取得主辦單位名稱
    public String getOrganizer() {
        return organizer;
    }

    // 設定主辦單位名稱
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    // 取得聯絡資訊
    public String getContactInfo() {
        return contactInfo;
    }

    // 設定聯絡資訊
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // 取得已報名人數
    public int getRegisteredCount() {
        return registeredCount;
    }

    // 設定已報名人數
    public void setRegisteredCount(int registeredCount) {
        this.registeredCount = registeredCount;
    }

    // 取得活動容量
    public int getCapacity() {
        return capacity;
    }

    // 設定活動容量
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // 取得活動描述
    public String getDescription() {
        return description;
    }

    // 設定活動描述
    public void setDescription(String description) {
        this.description = description;
    }
}
