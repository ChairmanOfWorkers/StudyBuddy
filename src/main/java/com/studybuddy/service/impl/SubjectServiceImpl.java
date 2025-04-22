package com.studybuddy.service.impl;

import com.studybuddy.model.Subject;
import com.studybuddy.repository.SubjectRepository;
import com.studybuddy.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepo;

    public SubjectServiceImpl(SubjectRepository subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }

    @Override
    @Transactional
    public Subject createSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        return subjectRepo.save(subject);
    }

    @Override
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepo.findById(id);
    }
}