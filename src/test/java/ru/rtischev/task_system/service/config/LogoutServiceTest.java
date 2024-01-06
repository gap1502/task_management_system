//package ru.rtischev.task_system.service.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import ru.rtischev.task_system.config.LogoutService;
//import ru.rtischev.task_system.repository.AuthenticationRepository;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class LogoutServiceTest {
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private AuthenticationRepository authenticationRepository;
//
//    @InjectMocks
//    private LogoutService logoutService;
//
//    @Test
//    void testLogoutWithValidToken() {
//        // Arrange
//        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
//        when(authenticationRepository.getUsernameByToken("validToken")).thenReturn("username");
//
//        // Act
//        logoutService.logout(request, response, authentication);
//
//        // Assert
//        verify(authenticationRepository, times(1)).removeTokenAndUsername("validToken");
//        SecurityContextHolder.clearContext();
//    }
//
//    @Test
//    void testLogoutWithInvalidToken() {
//        // Arrange
//        when(request.getHeader("Authorization")).thenReturn("InvalidToken");
//
//        // Act
//        logoutService.logout(request, response, authentication);
//
//        // Assert
//        verify(authenticationRepository, never()).removeTokenAndUsername(anyString());
//        SecurityContextHolder.clearContext();
//    }
//
//
//}
//

