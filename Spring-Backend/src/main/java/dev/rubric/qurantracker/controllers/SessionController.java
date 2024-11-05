package dev.rubric.qurantracker.controllers;

import dev.rubric.qurantracker.services.SessionService;
import dev.rubric.qurantracker.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    private final SessionService sessionService;
    private final UserService userService;


    public SessionController(SessionService sessionService,
                             UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @PostMapping("/createSession")
    public ResponseEntity<String> createUserSession(@RequestBody String username,
                                                    HttpSession session) {
        Integer userId = userService.getUserIdByUsername(username);

        if (userId == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Unable to get session for user: " + username);

        return ResponseEntity.ok("Created session for user: " + username);
    }

    @GetMapping("/getUserSession")
    public ResponseEntity<Map<String, Integer>> getUserSession(HttpSession session) {

        Integer userId = sessionService.getUserFromSession(session);
        if (userId == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        String username = userService.getUsername(userId);
        Map<String, Integer> user = new HashMap<>();

        user.put(username, userId);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/destroySession")
    public ResponseEntity<String> destroySession(HttpSession session) {
        Integer userId = sessionService.deleteSession(session);
        return ResponseEntity.ok("Session destroyed for user: " + userId);
    }
}
