package dev.rubric.qurantracker.types;
public class SurahProgress {
    private final int verseCount;
    private int completedVerses;

    public SurahProgress(int verseCount) {
        this.verseCount = verseCount;
        this.completedVerses = 0;
    }

    public int getVerseCount() {
        return verseCount;
    }

    public int getCompletedVerses() {
        return completedVerses;
    }

    public void setCompletedVerses(int completedVerses) {
        if (completedVerses < 0) {
            throw new IllegalArgumentException("Completed verses cannot be negative.");
        }
        if (completedVerses > verseCount) {
            throw new IllegalArgumentException("Completed verses cannot exceed total verse count.");
        }
        this.completedVerses = completedVerses;
    }

    public void incrementCompletedVerses() {
        if (completedVerses < verseCount) {
            completedVerses++;
        } else {
            throw new IllegalStateException("All verses are already completed.");
        }
    }

    public void decrementCompletedVerses() {
        if (completedVerses > 0) {
            completedVerses--;
        } else {
            throw new IllegalStateException("No verses to decrement.");
        }
    }
}

