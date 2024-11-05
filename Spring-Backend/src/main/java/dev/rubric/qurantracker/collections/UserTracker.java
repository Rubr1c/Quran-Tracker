package dev.rubric.qurantracker.collections;

import dev.rubric.qurantracker.types.QuranSurah;
import dev.rubric.qurantracker.types.QuranVerse;
import dev.rubric.qurantracker.types.SurahProgress;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Document(collection = "user-tracking")
public class UserTracker {
    @Id
    private Integer userId;

    private EnumMap<QuranSurah, SurahProgress> surahProgress = new EnumMap<>(QuranSurah.class);

    public UserTracker(Integer userId) {
        this.userId = userId;

        for (QuranSurah surah : QuranSurah.values()) {
            int verseCount = QuranVerse.getVerseCount(surah);
            surahProgress.put(surah, new SurahProgress(verseCount));
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public EnumMap<QuranSurah, SurahProgress> getSurahProgress() {
        return surahProgress;
    }

    public void setSurahProgress(QuranSurah surah, Integer versesCompleted) {
        SurahProgress progress = surahProgress.get(surah);
        progress.setCompletedVerses(versesCompleted);
    }

    public void incrementSurahProgress(QuranSurah surah) {
        SurahProgress progress = surahProgress.get(surah);
        progress.incrementCompletedVerses();
    }

    public void decrementSurahProgress(QuranSurah surah) {
        SurahProgress progress = surahProgress.get(surah);
        progress.decrementCompletedVerses();
    }

}
