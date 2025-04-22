package com.studybuddy.controller;

import com.studybuddy.model.Profile;
import com.studybuddy.model.User;
import com.studybuddy.service.ProfileService;
import com.studybuddy.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        Long userId = userService.findByEmail(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow();

        Profile profile = profileService.getProfileByUserId(userId)
                .orElseGet(() -> {
                    Profile p = new Profile();
                    p.setAvailability(Collections.emptyList());
                    return p;
                });

        model.addAttribute("profile", profile);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String fullName,
            @RequestParam String bio,
            @RequestParam(required = false) String availability  // now a single comma‑list
    ) {
        String email = userDetails.getUsername();
        Long userId = userService.findByEmail(email)
                .map(User::getId)
                .orElseThrow();

        List<String> availList = List.of();
        if (availability != null && !availability.isBlank()) {
            availList = Arrays.stream(availability.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }

        profileService.createOrUpdateProfile(userId, fullName, bio, availList);
        return "redirect:/profile";
    }
}
