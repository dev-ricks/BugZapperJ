package com.github.devricks.bugzapperj.microservice.bug.service;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NullBugDataException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BugService {

    Bug createBug(Bug bug) throws NullBugDataException, BugAlreadyExistsException;

    Bug updateBug(Bug bug) throws BugNotFoundException, NullBugDataException;

    void deleteBug(int id) throws BugNotFoundException, InvalidBugIdException;

    Bug getBugById(int id) throws BugNotFoundException, InvalidBugIdException;

    List<Bug> getAllBugs();
}
