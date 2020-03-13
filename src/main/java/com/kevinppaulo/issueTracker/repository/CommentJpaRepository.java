package com.kevinppaulo.issueTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinppaulo.issueTracker.models.Comment;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

}
