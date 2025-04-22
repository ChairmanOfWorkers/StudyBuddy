package com.studybuddy.service;

import com.studybuddy.model.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject createSubject(String name);
    Optional<Subject> getSubjectById(Long id);
}
