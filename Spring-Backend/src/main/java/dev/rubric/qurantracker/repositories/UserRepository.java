package dev.rubric.qurantracker.repositories;

import dev.rubric.qurantracker.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> getUserByUserId(Integer userId);
    Optional<User> getUserByUsername(String username);

    @Query(value = "{}", sort = "{ 'userId' : -1 }")
    Optional<User> findMaxUserId();
}
