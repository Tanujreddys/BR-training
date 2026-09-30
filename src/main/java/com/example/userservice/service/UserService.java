package com.example.userservice.service;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.LoginResponse;
import com.example.userservice.dto.RegisterRequest;
import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.User;
import com.example.userservice.exception.DuplicateEmailException;
import com.example.userservice.exception.InvalidCredentialsException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);

        return convertToResponse(savedUser);
    }

    public String registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");
        user.setStatus("ACTIVE");

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        ));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken = jwtService.generateAccessToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                accessExpiration
        );
    }

    public LoginResponse refreshAccessToken(String refreshToken) {

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new InvalidCredentialsException(
                    "Invalid or expired refresh token"
            );
        }

        String tokenType = jwtService.extractTokenType(refreshToken);

        if (!"refresh".equals(tokenType)) {
            throw new InvalidCredentialsException(
                    "Invalid refresh token"
            );
        }

        Long userId = jwtService.extractUserId(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "User not found"
                        ));

        String newAccessToken =
                jwtService.generateAccessToken(user);

        return new LoginResponse(
                newAccessToken,
                refreshToken,
                "Bearer",
                accessExpiration
        );
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return convertToResponse(user);
    }

    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());

        User updatedUser = userRepository.save(user);

        return convertToResponse(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }

    private UserResponse convertToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}