package com.kevinppaulo.issueTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinppaulo.issueTracker.models.Organization;

@Repository
public interface OrganizationJpaRepository extends JpaRepository<Organization, Long>{

}
