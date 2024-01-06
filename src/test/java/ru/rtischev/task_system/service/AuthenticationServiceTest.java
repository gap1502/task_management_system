//package ru.rtischev.task_system.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import ru.rtischev.task_system.auth.AuthenticationRequest;
//import ru.rtischev.task_system.auth.AuthenticationResponse;
//import ru.rtischev.task_system.auth.RegisterRequest;
//import ru.rtischev.task_system.config.JwtService;
//import ru.rtischev.task_system.model.User;
//import ru.rtischev.task_system.repository.AuthenticationRepository;
//import ru.rtischev.task_system.repository.UserRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthenticationServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtService jwtService;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @InjectMocks
//    private AuthenticationService authenticationService;
//
//    @Test
//    void testRegister() {
//        // Arrange
//        RegisterRequest registerRequest = new RegisterRequest("username", "email@example.com", "password");
//        User user = User.builder()
//                .username(registerRequest.getUsername())
//                .email(registerRequest.getEmail())
//                .password("encodedPassword")
//                .build();
//
//        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(jwtService.generateToken(user)).thenReturn("jwtToken");
//
//        // Act
//        AuthenticationResponse response = authenticationService.register(registerRequest);
//
//        // Assert
//        assertEquals("jwtToken", response.getToken());
//        verify(userRepository, times(1)).save(any(User.class));
//        verify(jwtService, times(1)).generateToken(user);
//    }
//
//    @Test
//    void testAuthenticate() {
//        // Arrange
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest("email@example.com", "password");
//        User user = User.builder()
//                .username("username")
//                .email(authenticationRequest.getEmail())
//                .password("encodedPassword")
//                .build();
//
//        when(userRepository.findByEmail(authenticationRequest.getEmail())).thenReturn(java.util.Optional.of(user));
//        when(jwtService.generateToken(user)).thenReturn("jwtToken");
//
//        // Act
//        AuthenticationResponse response = authenticationService.authenticate(authenticationRequest);
//
//        // Assert
//        assertEquals("jwtToken", response.getToken());
//        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(userRepository, times(1)).findByEmail(authenticationRequest.getEmail());
//        verify(jwtService, times(1)).generateToken(user);
//        verify(authenticationRepository, times(1)).putTokenAndUsername("jwtToken", authenticationRequest.getEmail());
//    }
//}
//
