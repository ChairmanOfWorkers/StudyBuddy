package com.studybuddy.service;

import com.studybuddy.model.StudyRequest;
import com.studybuddy.model.StudyRequest.RequestStatus;
import java.util.List;

public interface StudyRequestService {
    StudyRequest sendRequest(Long senderId, Long receiverId, Long subjectId);
    List<StudyRequest> getRequestsSentBy(Long userId);
    List<StudyRequest> getRequestsReceivedBy(Long userId);
    StudyRequest updateStatus(Long requestId, RequestStatus status);
}