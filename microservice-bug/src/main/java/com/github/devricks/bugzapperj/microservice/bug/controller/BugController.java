package com.github.devricks.bugzapperj.microservice.bug.controller;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.BugNotFoundException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NullBugDataException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import com.github.devricks.bugzapperj.microservice.bug.service.BugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bug-microservice")
@Controller
public class BugController {

    private final BugService bugService;

    BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/bugs")
    ResponseEntity<?> getBugs() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<Bug> bugs = bugService.getAllBugs();
        List<?> bugIds = bugs.stream().map(Bug::getId).collect(Collectors.toList());
        map.put("data", bugs);
        map.put("status", 1);
        map.put("ids", bugIds);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/bugs/{id}")
    ResponseEntity<?> getBugById(@PathVariable("id") int id) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            Bug bug = bugService.getBugById(id);
            map.put("status", 1);
            map.put("data", bug);
            map.put("id", id);
            return ResponseEntity.ok().body(map);
        } catch (InvalidBugIdException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } catch (BugNotFoundException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }

    }

    @PostMapping("/bugs")
    ResponseEntity<?> createBug(@RequestBody Bug bug) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            Bug newBug = bugService.createBug(bug);
            map.put("status", 1);
            map.put("data", newBug);
            map.put("id", newBug.getId());
            return ResponseEntity.created(new URI("/bugs/" + newBug.getId())).body(map);
        } catch (NullBugDataException | BugAlreadyExistsException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", bug.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } catch (URISyntaxException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", bug.getId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @PutMapping("/bugs/{id}")
    ResponseEntity<?> updateBug(@PathVariable("id") int id, @RequestBody Bug bug) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            bug.setId(id);
            Bug updatedBug = bugService.updateBug(bug);
            map.put("status", 1);
            map.put("data", updatedBug);
            map.put("id", id);
            return ResponseEntity.ok().body(map);
        } catch (NullBugDataException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } catch (BugNotFoundException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }

    @DeleteMapping("/bugs/{id}")
    ResponseEntity<?> deleteBug(@PathVariable("id") int id) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            bugService.deleteBug(id);
            map.put("status", 1);
            map.put("id", id);
            return ResponseEntity.ok().body(map);
        } catch (InvalidBugIdException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } catch (BugNotFoundException e) {
            map.put("status", 0);
            map.put("message", e.getMessage());
            map.put("id", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        }
    }
}
