package com.studybuddy.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.studybuddy.model.User;
import com.studybuddy.repository.UserRepository;
import com.studybuddy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_ShouldHashPasswordAndSaveUser() {
        String email = "test@example.com";
        String rawPassword = "secret";
        String hashedPassword = "hashedPwd";
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register(email, rawPassword);

        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(userCaptor.capture());
        User saved = userCaptor.getValue();
        assertEquals(email, saved.getEmail());
        assertEquals(hashedPassword, saved.getPasswordHash());
        assertNotNull(saved.getRegistrationDate());
        assertEquals(saved, result);
    }

    @Test
    void findByEmail_ReturnsUser() {
        String email = "foo@bar.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> found = userService.findByEmail(email);

        assertTrue(found.isPresent());
        assertEquals(email, found.get().getEmail());
    }
}