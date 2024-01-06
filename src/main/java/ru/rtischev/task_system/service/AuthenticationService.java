package ru.rtischev.task_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtischev.task_system.auth.AuthenticationRequest;
import ru.rtischev.task_system.auth.AuthenticationResponse;
import ru.rtischev.task_system.auth.RegisterRequest;
import ru.rtischev.task_system.config.JwtService;
import ru.rtischev.task_system.exceptions.UnauthorizedUserException;
import ru.rtischev.task_system.model.User;
import ru.rtischev.task_system.repository.AuthenticationRepository;
import ru.rtischev.task_system.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final AuthenticationRepository authenticationRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.existsByUsername(request.getUsername())) {
            throw new UnauthorizedUserException("The username is already taken");
        }
        if (repository.existsByEmail(request.getEmail())) {
            throw new UnauthorizedUserException("The email address is already occupied");
        }

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        authenticationRepository.putTokenAndUsername(jwtToken, request.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

