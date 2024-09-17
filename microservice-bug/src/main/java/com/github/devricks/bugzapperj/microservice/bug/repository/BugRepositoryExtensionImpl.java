package com.github.devricks.bugzapperj.microservice.bug.repository;

import com.github.devricks.bugzapperj.microservice.bug.exception.BugAlreadyExistsException;
import com.github.devricks.bugzapperj.microservice.bug.exception.InvalidBugIdException;
import com.github.devricks.bugzapperj.microservice.bug.exception.NotABugEntityException;
import com.github.devricks.bugzapperj.microservice.bug.model.Bug;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BugRepositoryExtensionImpl<T> implements BugRepositoryExtension<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BugRepositoryExtensionImpl(JpaContext context) {
        this.entityManager = context.getEntityManagerByManagedType(Bug.class);
    }

    public <S extends T> S duplicateNotAllowedToSave(S entity) throws NotABugEntityException, InvalidBugIdException {
        if (!(entity instanceof Bug bug)) {
            throw new NotABugEntityException("Entity is not a Bug");
        }
        int id = bug.getId();
        String name = bug.getName();
        if (id < 0) {
            throw new InvalidBugIdException("Invalid bug id");
        }
        String query;
        List<Bug> existingBugs;
        if (id == 0) {
            query = "SELECT b FROM Bug b WHERE b.name = :name";
            existingBugs = entityManager.createQuery(query, Bug.class)
                    .setParameter("name", name)
                    .getResultList();
        } else {
            query = "SELECT b FROM Bug b WHERE b.id = :id OR b.name = :name";
            existingBugs = entityManager.createQuery(query, Bug.class)
                    .setParameter("id", id)
                    .setParameter("name", name)
                    .getResultList();
        }
        if (!existingBugs.isEmpty()) {
            throw new BugAlreadyExistsException("Bug already exists with id: " + id + " or name: " + name);
        }
        return entityManager.merge(entity);
    }

    @Override
    public String toString() {
        return "BugRepositoryImpl(entityManager=" + entityManager + ")";
    }
}
