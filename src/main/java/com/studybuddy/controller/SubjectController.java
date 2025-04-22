package com.studybuddy.controller;

import com.studybuddy.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects"; // subjects.html
    }

    @PostMapping("/subjects")
    public String addSubject(@RequestParam String name) {
        subjectService.createSubject(name);
        return "redirect:/subjects";
    }
}
