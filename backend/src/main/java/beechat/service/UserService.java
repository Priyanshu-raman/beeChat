package beechat.service;

import beechat.dto.UserProfileUpdateRequest;
import beechat.dto.UserResponse;
import beechat.entity.User;
import beechat.exception.UserNotFoundException;
import beechat.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return toUserResponse(user);
    }

    public List<UserResponse> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateUserProfile(
            Long userId,
            UserProfileUpdateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setProfilePicture(request.getProfilePicture());
        user.setBio(request.getBio());

        User updatedUser = userRepository.save(user);

        return toUserResponse(updatedUser);
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePicture(),
                user.getBio(),
                user.getActive(),
                user.getCreatedAt()
        );
    }
}