package dev.rubric.qurantracker.services;

import dev.rubric.qurantracker.collections.UserTracker;
import dev.rubric.qurantracker.repositories.UserTrackerRepository;
import dev.rubric.qurantracker.types.QuranSurah;
import org.springframework.stereotype.Service;

@Service
public class UserTrackerService {
    private final UserTrackerRepository userTrackerRepository;


    public UserTrackerService(UserTrackerRepository userTrackerRepository) {
        this.userTrackerRepository = userTrackerRepository;
    }

    public void updateSurahProgress(Integer userId,
                                    QuranSurah surah,
                                    Integer versesCompleted) {
        userTrackerRepository.findByUserId(userId)
                .ifPresentOrElse(tracker -> {
                    tracker.setSurahProgress(surah, versesCompleted);
                    userTrackerRepository.save(tracker);
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });
    }

    public void incrementSurahProgress(Integer userId, QuranSurah surah) {
        userTrackerRepository.findByUserId(userId)
                .ifPresentOrElse(tracker -> {
                    tracker.incrementSurahProgress(surah);
                    userTrackerRepository.save(tracker);
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });
    }

    public void decrementSurahProgress(Integer userId, QuranSurah surah) {
        userTrackerRepository.findByUserId(userId)
                .ifPresentOrElse(tracker -> {
                    tracker.decrementSurahProgress(surah);
                    userTrackerRepository.save(tracker);
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });
    }

    public void createUserTracker(Integer userId) {
        UserTracker userTracker = new UserTracker(userId);
        userTrackerRepository.save(userTracker);
    }

    public UserTracker getUserTracker(Integer userId) {
        return userTrackerRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
