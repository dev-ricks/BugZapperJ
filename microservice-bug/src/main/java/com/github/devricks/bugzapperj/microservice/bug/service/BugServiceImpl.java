package com.github.devricks.bugzapperj.microservice.bug.service;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NullBugDataException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import com.github.devricks.bugzapperj.microservice.bug.repository.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BugServiceImpl implements BugService {

    @Autowired
    private BugRepository bugRepository;

    @Override
    public Bug getBugById(int id) throws BugNotFoundException, InvalidBugIdException {
        if (id < 1) {
            throw new InvalidBugIdException("Invalid bug id");
        }
        Optional<Bug> bug = bugRepository.findById(id);
        if (bug.isPresent()) {
            return bug.get();
        }
        throw new BugNotFoundException("Bug not found");
    }

    @Override
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @Override
    public Bug createBug(Bug bug) throws BugAlreadyExistsException, NullBugDataException {
        if (bug == null) {
            throw new NullBugDataException("Invalid bug data, bug object is required");
        } else if (bug.getName() == null || bug.getName().isBlank()) {
            throw new NullBugDataException("Invalid bug data, name is required");
        } else if (bug.getDescription() == null || bug.getDescription().isBlank()) {
            throw new NullBugDataException("Invalid bug data, description is required");
        } else if (bugRepository.existsById(bug.getId())) {
            throw new BugAlreadyExistsException("Bug already exists");
        }
        return bugRepository.save(bug);
    }

    @Override
    public void deleteBug(int id) throws BugNotFoundException, InvalidBugIdException {
        if (id < 1) {
            throw new InvalidBugIdException("Invalid bug id");
        } else if (bugRepository.existsById(id)) {
            bugRepository.deleteById(id);
        }
        throw new BugNotFoundException("Bug not found");
    }

    @Override
    public Bug updateBug(Bug bug) throws BugNotFoundException, NullBugDataException {
        if (bug == null) {
            throw new NullBugDataException("Invalid bug data, bug object is required");
        } else if (bug.getName() == null || bug.getName().isBlank()) {
            throw new NullBugDataException("Invalid bug data, name is required");
        } else if (bug.getDescription() == null || bug.getDescription().isBlank()) {
            throw new NullBugDataException("Invalid bug data, description is required");
        } else if (!bugRepository.existsById(bug.getId())) {
            throw new BugNotFoundException("Bug not found");
        }
        return bugRepository.save(bug);
    }
}
