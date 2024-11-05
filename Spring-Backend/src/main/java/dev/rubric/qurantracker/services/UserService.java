package dev.rubric.qurantracker.services;

import dev.rubric.qurantracker.collections.User;
import dev.rubric.qurantracker.types.SuccessResponse;
import dev.rubric.qurantracker.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTrackerService userTrackerService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserTrackerService userTrackerService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTrackerService = userTrackerService;
    }

    private Integer getLastUserId() {
        return userRepository.findMaxUserId()
                .map(User::getUserId)
                .orElse(-1);
    }

    private SuccessResponse validatePassword(String password) {
        SuccessResponse response = new SuccessResponse();
        if (password.length() < 8)
            response.setResponse(false, "Password must be at least 8 characters long");
        else if (password.chars().noneMatch(Character::isUpperCase))
            response.setResponse(false, "Password must contain an uppercase character");
        else if (password.chars().allMatch(Character::isLetterOrDigit))
            response.setResponse(false, "Password must contain a special character");
        else
            response.setResponse(true, "Password is valid");

        return response;
    }


    public SuccessResponse createUser(String username, String password) {
        SuccessResponse response = new SuccessResponse();
        if (userRepository.getUserByUsername(username).isPresent()) {
            response.setResponse(
                    false,
                    String.format("User with username: %s already exists", username));
            return response;
        }

        SuccessResponse isPasswordValid = validatePassword(password);

        if (!isPasswordValid.getSuccess()) return isPasswordValid;

        User user = new User();
        String hashedPassword = passwordEncoder.encode(password);
        user.setUserId(getLastUserId() + 1);
        user.setUsername(username);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        userTrackerService.createUserTracker(user.getUserId());

        response.setResponse(true, user.getUserId().toString());
        return response;
    }

    public SuccessResponse authenticateUser(String username, String password) {
        SuccessResponse response = new SuccessResponse();
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isEmpty()) {
            response.setResponse(
                    false,
                    String.format("User with username: %s does not exist", username));
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            response.setResponse(false, "Incorrect password");
        } else {
            response.setResponse(true, user.get().getUserId().toString());
        }
        return response;
    }

    public Integer getUserIdByUsername(String username) {
        return userRepository.getUserByUsername(username)
                .map(User::getUserId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("User with username: %s does not exist", username)));
    }

    public String getUsername(Integer userId) {
        return userRepository.getUserByUserId(userId)
                .map(User::getUsername)
                .orElseThrow(() -> new RuntimeException(
                        String.format("User with id: %d does not exist", userId)));
    }
}
