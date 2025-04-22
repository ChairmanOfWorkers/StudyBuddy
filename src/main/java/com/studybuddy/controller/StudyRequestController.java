package com.studybuddy.controller;

import com.studybuddy.model.StudyRequest.RequestStatus;
import com.studybuddy.model.User;
import com.studybuddy.service.StudyRequestService;
import com.studybuddy.service.UserService;
import com.studybuddy.service.SubjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudyRequestController {

    private final StudyRequestService requestService;
    private final UserService userService;
    private final SubjectService subjectService;

    public StudyRequestController(StudyRequestService requestService,
                                  UserService userService,
                                  SubjectService subjectService) {
        this.requestService = requestService;
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @GetMapping("/requests")
    public String viewRequests(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Long userId = userService.findByEmail(userDetails.getUsername()).map(User::getId).orElse(null);
        model.addAttribute("sent", requestService.getRequestsSentBy(userId));
        model.addAttribute("received", requestService.getRequestsReceivedBy(userId));
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "requests"; // requests.html
    }

    @PostMapping("/requests/send")
    public String sendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long receiverId,
            @RequestParam Long subjectId) {
        Long senderId = userService.findByEmail(userDetails.getUsername()).map(User::getId).orElseThrow();
        requestService.sendRequest(senderId, receiverId, subjectId);
        return "redirect:/requests";
    }

    @PostMapping("/requests/update")
    public String updateRequest(
            @RequestParam Long requestId,
            @RequestParam RequestStatus status) {
        requestService.updateStatus(requestId, status);
        return "redirect:/requests";
    }
}
