package com.example.project.controller;

import com.example.project.entity.Event;
import com.example.project.entity.User;
import com.example.project.entity.Registration;
import com.example.project.repository.EventRepository;
import com.example.project.repository.RegistrationRepository;
import com.example.project.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.project.service.EmailService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private EmailService emailService;

    // 進入首頁
    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
        String loginStatus = (session.getAttribute("user") != null) ? "登出" : "登入";
        model.addAttribute("loginStatus", loginStatus);
        return "index"; // 返回 index.html
    }

    // 進入登入頁面
    @GetMapping("/log_in")
    public String loginPage() {
        return "log_in"; // 返回 log_in.html
    }

    // 登入處理
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, HttpSession session) {
        // 檢查用戶是否存在，若不存在則註冊新用戶
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User(email);
            userRepository.save(newUser);
            return newUser;
        });

        // 設定用戶為已登入狀態
        session.setAttribute("user", user);
        return "redirect:/"; // 登入後重定向回首頁
    }

    // 登出處理
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // 清除 session
        return "redirect:/"; // 登出後重定向回首頁
    }

    // 瀏覽活動列表頁面
    @GetMapping("/explore")
    public String explorePage(Model model) {
        List<Event> events = eventRepository.findAll();
        model.addAttribute("events", events); // 傳遞活動列表到前端
        return "explore"; // 返回 explore.html
    }

    // 確認登入狀態
    @GetMapping("/login-status")
    @ResponseBody
    public Map<String, Object> loginStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        response.put("loggedIn", user != null);
        return response;
    }

    // 顯示特定活動的報名頁面
    @GetMapping("/registration/{eventId}")
    public String registrationPage(@PathVariable Long eventId, HttpSession session, Model model) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            model.addAttribute("event", event);

            boolean isFull = event.getRegisteredCount() >= event.getCapacity();
            model.addAttribute("isFull", isFull);

            User user = (User) session.getAttribute("user");
            if (user != null) {
                // 檢查使用者是否已報名
                boolean isRegistered = registrationRepository.findByUserIdAndEventId(user.getId(), eventId) != null;
                model.addAttribute("isRegistered", isRegistered);
            } else {
                model.addAttribute("isRegistered", false);  // 未登入使用者
            }
        } else {
            model.addAttribute("errorMessage", "活動不存在");
        }
        return "registration"; // 返回 registration.html
    }

    // 用戶進行報名
    @PostMapping("/register/{eventId}")
    @ResponseBody
    public Map<String, Object> registerForEvent(@PathVariable Long eventId, HttpSession session,
                                                @RequestParam String name, @RequestParam String phone,
                                                @RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        
        // 確保用戶已登入
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("message", "您必須登入才能報名");
            response.put("success", false);
            return response;
        }
        
        if (eventId == null) {
            response.put("message", "活動ID未提供");
            response.put("success", false);
            return response;
        }
        
        // 檢查活動是否存在且未額滿
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null || event.getRegisteredCount() >= event.getCapacity()) {
            response.put("message", "活動不存在或已額滿");
            response.put("success", false);
            return response;
        }

        // 檢查使用者是否已報名
        Registration existingRegistration = registrationRepository.findByUserIdAndEventId(user.getId(), eventId);
        if (existingRegistration != null) {
            response.put("message", "您已報名此活動");
            response.put("success", false);
            return response;
        }

        // 保存報名資料
        try {
            Registration registration = new Registration();
            registration.setEvent(event);
            registration.setUser(user);
            registration.setName(name);
            registration.setPhone(phone);
            registration.setEmail(email);
            registrationRepository.save(registration);  // 儲存到資料庫

            // 更新活動報名人數
            event.setRegisteredCount(event.getRegisteredCount() + 1);
            eventRepository.save(event);

            // 發送郵件通知
            String subject = "報名成功通知";
            String text = "您好，" + name + "！\n\n您已成功報名 " + event.getEventName() + " 活動！\n\n祝您參加愉快！";
            emailService.sendEmail(email, subject, text);

            response.put("message", "報名成功");
            response.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "報名失敗，請稍後再試");
            response.put("success", false);
        }

        return response;
    }

    // 用戶取消報名
    @PostMapping("/cancel-registration/{eventId}")
    @ResponseBody
    public String cancelRegistration(@PathVariable Long eventId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (eventId == null) {
            return "活動ID未提供";
        }
        
        // 檢查用戶是否已登入
        if (user == null) {
            return "請先登入/註冊";
        }

        // 查找報名資料
        Registration registration = registrationRepository.findByUserIdAndEventId(user.getId(), eventId);
        if (registration == null) {
            return "您尚未報名此活動";
        }

        // 刪除報名資料
        registrationRepository.delete(registration);

        // 更新活動報名人數
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            event.setRegisteredCount(event.getRegisteredCount() - 1);
            eventRepository.save(event);
        }
        // 發送取消報名的電子郵件
        if (event != null) {
            String subject = "取消報名通知";
            String text = "您好，" + registration.getName() + "！\n\n您已成功取消報名 " + event.getEventName() + " 活動。\n\n感謝您的參與！";
            emailService.sendEmail(registration.getEmail(), subject, text);
        }

        return "取消報名成功";
    }

    // 獲取活動詳細資料
    @GetMapping("/event-details/{eventId}")
    @ResponseBody
    public Map<String, Object> getEventDetails(@PathVariable Long eventId) {
        Map<String, Object> response = new HashMap<>();
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            response.put("capacity", event.getCapacity());
            response.put("registeredCount", event.getRegisteredCount());
        } else {
            response.put("error", "活動不存在");
        }
        return response;
    }

}
