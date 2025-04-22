package com.studybuddy.repository;

import com.studybuddy.model.StudyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRequestRepository extends JpaRepository<StudyRequest, Long> {
    List<StudyRequest> findBySenderId(Long senderId);
    List<StudyRequest> findByReceiverId(Long receiverId);
}
