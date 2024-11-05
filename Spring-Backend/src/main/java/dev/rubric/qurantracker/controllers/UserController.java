package dev.rubric.qurantracker.controllers;

import dev.rubric.qurantracker.services.SessionService;
import dev.rubric.qurantracker.services.UserService;
import dev.rubric.qurantracker.types.SuccessResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody Map<String, String> credentials,
                                                 HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        SuccessResponse response = userService.authenticateUser(username, password);
        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        sessionService.createUserSession(Integer.parseInt(response.getMessage()), session);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signup(@RequestBody Map<String, String> credentials,
                                                  HttpSession session) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        SuccessResponse response = userService.createUser(username, password);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        sessionService.createUserSession(Integer.parseInt(response.getMessage()), session);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        Integer userId = sessionService.deleteSession(session);
        return ResponseEntity.ok("Session destroyed for user: " + userId);
    }

    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(HttpSession session) {
        Integer userId = sessionService.getUserFromSession(session);
        String username = userService.getUsername(userId);
        return ResponseEntity.ok(username);
    }
}
