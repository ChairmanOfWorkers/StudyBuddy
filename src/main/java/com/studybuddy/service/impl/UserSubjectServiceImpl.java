package com.studybuddy.service.impl;

import com.studybuddy.model.Subject;
import com.studybuddy.model.UserSubject;
import com.studybuddy.repository.SubjectRepository;
import com.studybuddy.repository.UserSubjectRepository;
import com.studybuddy.repository.UserRepository;
import com.studybuddy.service.UserSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSubjectServiceImpl implements UserSubjectService {

    private final UserSubjectRepository userSubjectRepo;
    private final UserRepository userRepo;
    private final SubjectRepository subjectRepo;

    public UserSubjectServiceImpl(UserSubjectRepository userSubjectRepo,
                                  UserRepository userRepo,
                                  SubjectRepository subjectRepo) {
        this.userSubjectRepo = userSubjectRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    public List<Subject> getSubjectsForUser(Long userId) {
        return userSubjectRepo.findAll().stream()
                .filter(us -> us.getUser().getId().equals(userId))
                .map(UserSubject::getSubject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addSubjectToUser(Long userId, Long subjectId) {
        UserSubject us = new UserSubject();
        us.setUser(userRepo.findById(userId).orElseThrow());
        us.setSubject(subjectRepo.findById(subjectId).orElseThrow());
        userSubjectRepo.save(us);
    }
}
