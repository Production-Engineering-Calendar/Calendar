package ro.unibuc.hello.controller;

import ro.unibuc.hello.model.User;
import ro.unibuc.hello.dto.AuthResponse;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.RegisterRequest;
import ro.unibuc.hello.security.JwtUtils;
import ro.unibuc.hello.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username already exists!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email already in use!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        if (registerRequest.isAdmin()) {
            roles.add("ROLE_ADMIN");
        }

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity
                .ok("User registered successfully as " + (registerRequest.isAdmin() ? "ADMIN" : "USER") + "!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.badRequest().body("Error: Invalid username or password!");
        }

        List<String> roles = new ArrayList<>();
        user.get().getRoles().forEach(role -> roles.add(role));
        String token = jwtUtils.generateToken(user.get().getUsername(), roles);

        return ResponseEntity.ok(new AuthResponse("Login successful", user.get().getUsername(), token));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserByUsername(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable String username) {
        if (authHeader != null && !isAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Access denied! Admin role required.");
        }

        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authHeader,
            @PathVariable String username) {
        if (!isAdmin(authHeader)) {
            return ResponseEntity.status(403).body("Access denied! Admin role required.");
        }

        return userRepository.findByUsername(username)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("User deleted successfully!");
                })
                .orElseGet(() -> ResponseEntity.status(404).body("User not found!"));
    }

    private boolean isAdmin(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Set<String> roles = jwtUtils.getRolesFromToken(token);
        return roles.contains("ROLE_ADMIN");
    }

}
