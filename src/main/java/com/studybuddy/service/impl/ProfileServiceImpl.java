package com.studybuddy.service.impl;

import com.studybuddy.model.Profile;
import com.studybuddy.model.User;
import com.studybuddy.repository.ProfileRepository;
import com.studybuddy.repository.UserRepository;
import com.studybuddy.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepo;
    private final UserRepository userRepo;

    public ProfileServiceImpl(ProfileRepository profileRepo,
                              UserRepository userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public Profile createOrUpdateProfile(Long userId, String fullName, String bio, List<String> availability) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Profile profile = profileRepo.findByUserId(userId)
                .orElse(new Profile());
        profile.setUser(user);
        profile.setFullName(fullName);
        profile.setBio(bio);
        profile.setAvailability(availability);
        return profileRepo.save(profile);
    }

    @Override
    public Optional<Profile> getProfileByUserId(Long userId) {
        return profileRepo.findByUserId(userId);
    }
}
