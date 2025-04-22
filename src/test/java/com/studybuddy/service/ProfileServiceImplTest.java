package com.studybuddy.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.studybuddy.model.Profile;
import com.studybuddy.model.User;
import com.studybuddy.repository.ProfileRepository;
import com.studybuddy.repository.UserRepository;
import com.studybuddy.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void createOrUpdateProfile_NewProfile_CreatesProfile() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Profile result = profileService.createOrUpdateProfile(
                userId, "John Doe", "Bio text", List.of("Mon 18-20", "Wed 09-11")
        );

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals("John Doe", result.getFullName());
        assertEquals("Bio text", result.getBio());
        assertEquals(List.of("Mon 18-20", "Wed 09-11"), result.getAvailability());
        verify(profileRepository).save(result);
    }

    @Test
    void getProfileByUserId_ReturnsEmptyIfNotFound() {
        Long userId = 2L;
        when(profileRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Optional<Profile> found = profileService.getProfileByUserId(userId);

        assertTrue(found.isEmpty());
    }
}
