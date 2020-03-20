package com.kevinppaulo.issueTracker.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kevinppaulo.issueTracker.models.Comment;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
	public List<Comment> findByIssue_IssueId(Long issueId); 
}
