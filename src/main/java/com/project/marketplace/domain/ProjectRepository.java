package com.project.marketplace.domain;

import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;


public interface ProjectRepository extends CrudRepository<Project, Long>
{
    Project findProjectById(long projectId) throws Exception;

    List<Project> findAll();

    List<Project> findAllByProjectBiddingDeadlineIsLessThanEqual(Timestamp timestamp);

    void removeProjectByIdEquals(long ProjectId);
}
