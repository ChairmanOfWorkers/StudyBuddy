package com.studybuddy.service;

import com.studybuddy.model.Subject;
import java.util.List;

public interface UserSubjectService {
    List<Subject> getSubjectsForUser(Long userId);
    void addSubjectToUser(Long userId, Long subjectId);
}