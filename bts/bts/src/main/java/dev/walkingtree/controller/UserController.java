package dev.walkingtree.controller;
import dev.walkingtree.entity.User;
import dev.walkingtree.service.imp.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Update endpoint
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User updateUser) {
        try {

            System.out.println(updateUser);
            // Call a method in UserService to update the user
            userService.updateUser(updateUser);
            return ResponseEntity.ok("User updated successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
        }
    }

    // Delete endpoint
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User deleteUser) {
        try {
            // Call a method in UserService to delete the user
            userService.deleteUser(deleteUser.getUsername());
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

}

