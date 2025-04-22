package com.studybuddy.service;

import com.studybuddy.model.Profile;
import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Profile createOrUpdateProfile(Long userId, String fullName, String bio, List<String> availability);
    Optional<Profile> getProfileByUserId(Long userId);
}
