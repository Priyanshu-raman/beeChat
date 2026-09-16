package beechat.controller;

import beechat.dto.UserProfileUpdateRequest;
import beechat.dto.UserResponse;
import beechat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get a user's public profile.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Search users by partial username.
     *
     * Example:
     * GET /api/users/search?username=ram
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam String username) {

        return ResponseEntity.ok(
                userService.searchUsersByUsername(username)
        );
    }

    /**
     * Update profile information.
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<UserResponse> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileUpdateRequest request) {

        return ResponseEntity.ok(
                userService.updateUserProfile(id, request)
        );
    }
}