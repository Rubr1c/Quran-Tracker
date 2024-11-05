package dev.rubric.qurantracker.controllers;

import dev.rubric.qurantracker.collections.UserTracker;
import dev.rubric.qurantracker.services.SessionService;
import dev.rubric.qurantracker.services.UserTrackerService;
import dev.rubric.qurantracker.types.QuranSurah;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/tracking")
public class UserTrackerController {

    private final UserTrackerService userTrackerService;
    private final SessionService sessionService;

    public UserTrackerController(UserTrackerService userTrackerService, SessionService sessionService) {
        this.userTrackerService = userTrackerService;
        this.sessionService = sessionService;
    }

    @PutMapping("/update-surah-progress")
    public ResponseEntity<String> updateSurahProgress(@RequestParam("surah") QuranSurah surah,
                                                      @RequestParam("versesCompleted") Integer number,
                                                      HttpSession session) {

        Integer userId = sessionService.getUserFromSession(session);

        userTrackerService.updateSurahProgress(userId, surah, number);
        return ResponseEntity.ok("Updated Surah Progress successfully");
    }

    @PutMapping("/increment-surah-progress")
    public ResponseEntity<String> incrementSurahProgress(@RequestParam("surah") QuranSurah surah,
                                                         HttpSession session) {

        Integer userId = sessionService.getUserFromSession(session);
        userTrackerService.incrementSurahProgress(userId, surah);
        return ResponseEntity.ok("Incremented Surah Progress successfully");
    }

    @PutMapping("/decrement-surah-progress")
    public ResponseEntity<String> decrementSurahProgress(@RequestParam("surah") QuranSurah surah,
                                                         HttpSession session) {

        Integer userId = sessionService.getUserFromSession(session);
        userTrackerService.decrementSurahProgress(userId, surah);
        return ResponseEntity.ok("decremented Surah Progress successfully");
    }

    @GetMapping("/get-all-progress")
    public ResponseEntity<UserTracker> getAllProgress(HttpSession session) {
        Integer userId = sessionService.getUserFromSession(session);
        return ResponseEntity.ok(userTrackerService.getUserTracker(userId));
    }
}
