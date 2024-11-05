package dev.rubric.qurantracker.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public void createUserSession(Integer userId, HttpSession session) {
        session.setAttribute("USER_ID", userId);
    }

    public Integer getUserFromSession(HttpSession session) {
        return (Integer) session.getAttribute("USER_ID");
    }

    public Integer deleteSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("USER_ID");
        session.invalidate();
        return userId;
    }
}
