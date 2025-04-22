package com.studybuddy.service.impl;

import com.studybuddy.model.StudyRequest;
import com.studybuddy.model.StudyRequest.RequestStatus;
import com.studybuddy.model.Subject;
import com.studybuddy.model.User;
import com.studybuddy.repository.StudyRequestRepository;
import com.studybuddy.repository.UserRepository;
import com.studybuddy.repository.SubjectRepository;
import com.studybuddy.service.StudyRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class StudyRequestServiceImpl implements StudyRequestService {

    private final StudyRequestRepository reqRepo;
    private final UserRepository userRepo;
    private final SubjectRepository subjectRepo;

    public StudyRequestServiceImpl(StudyRequestRepository reqRepo,
                                   UserRepository userRepo,
                                   SubjectRepository subjectRepo) {
        this.reqRepo = reqRepo;
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
    }

    @Override
    @Transactional
    public StudyRequest sendRequest(Long senderId, Long receiverId, Long subjectId) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();
        Subject subj = subjectRepo.findById(subjectId).orElseThrow();

        StudyRequest req = new StudyRequest();
        req.setSender(sender);
        req.setReceiver(receiver);
        req.setSubject(subj);
        req.setStatus(RequestStatus.PENDING);
        req.setRequestDate(Instant.now());
        return reqRepo.save(req);
    }

    @Override
    public List<StudyRequest> getRequestsSentBy(Long userId) {
        return reqRepo.findBySenderId(userId);
    }

    @Override
    public List<StudyRequest> getRequestsReceivedBy(Long userId) {
        return reqRepo.findByReceiverId(userId);
    }

    @Override
    @Transactional
    public StudyRequest updateStatus(Long requestId, RequestStatus status) {
        StudyRequest req = reqRepo.findById(requestId).orElseThrow();
        req.setStatus(status);
        return reqRepo.save(req);
    }
}
