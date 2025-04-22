package com.studybuddy.repository;

import com.studybuddy.model.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
}