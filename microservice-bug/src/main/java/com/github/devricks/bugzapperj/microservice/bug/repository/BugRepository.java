package com.github.devricks.bugzapperj.microservice.bug.repository;

import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Integer> {

}
