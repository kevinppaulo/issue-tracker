package com.kevinppaulo.issueTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinppaulo.issueTracker.models.Issue;

@Repository
public interface IssueJpaRepository extends JpaRepository<Issue, Long>{
	List<Issue> findAllByOrganization_OrganizationId(Long organizationId);
}
