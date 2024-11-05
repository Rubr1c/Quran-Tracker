package dev.rubric.qurantracker.repositories;


import dev.rubric.qurantracker.collections.UserTracker;
import dev.rubric.qurantracker.types.SurahProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.List;

public interface UserTrackerRepository extends MongoRepository<UserTracker, String> {

    Optional<UserTracker> findByUserId(Integer userId);

}
