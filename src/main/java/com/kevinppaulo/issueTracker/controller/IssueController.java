package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Comment;
import com.kevinppaulo.issueTracker.models.Issue;
import com.kevinppaulo.issueTracker.models.IssueStatus;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.CommentJpaRepository;
import com.kevinppaulo.issueTracker.repository.IssueJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	private final OrganizationJpaRepository organizationRepo;
	private final ApplicationUserJpaRepository userRepo;
	private final IssueJpaRepository issueRepo;
	private final CommentJpaRepository commentRepo;
	
	
	@Autowired
	public IssueController(OrganizationJpaRepository organizationRepo, ApplicationUserJpaRepository userRepo,
			IssueJpaRepository issueRepo, CommentJpaRepository commentRepo) {
		super();
		this.organizationRepo = organizationRepo;
		this.userRepo = userRepo;
		this.issueRepo = issueRepo;
		this.commentRepo = commentRepo;
	}

	@GetMapping("/{issueId}")
	public ModelAndView issueDetails(@PathVariable("issueId") Long issueId) {
		ModelAndView mv = new ModelAndView("issue-details");
		Issue issue = issueRepo.findById(issueId).orElseThrow(RuntimeException::new);
		
		List<Comment> comments = commentRepo.findByIssue_IssueId(issueId);
		
		mv.addObject("issue", issue);
		mv.addObject("comments", comments);
		return mv;
	}
	
	
	@PostMapping("/{issueId}/edit")
	public String editIssue(@PathVariable("issueId") Long issueId, @Valid Issue issue, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			//TODO: actually do something here...
			System.out.println(bindingResult.getAllErrors());
			System.out.println("Errors were found");
			return "redirect:/issues/"+issueId;
		}
		
		Issue originalIssue = issueRepo.findById(issueId).orElseThrow(RuntimeException::new);
		issue.setIssueId(originalIssue.getIssueId());
		issue.setCreatedAt(originalIssue.getCreatedAt());
		issue.setLastUpdated(new Date());
		issue.setOrganization(originalIssue.getOrganization());
		if(issue.getIssueStatus() == IssueStatus.CLOSED) {
			issue.setClosedAt(new Date());
		}
		issueRepo.save(issue);
		return "redirect:/issues/"+issueId;
	}
	
	@PostMapping("/{issueId}/delete")
	public String deleteIssue(@PathVariable("issueId") Long issueId, @Valid Issue issue, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			//TODO: actually do something here...
			System.out.println(bindingResult.getAllErrors());
			System.out.println("Errors were found");
			return "redirect:/issues/"+issueId;
		}
		
		
		Issue foundIssue = issueRepo.findById(issueId).orElseThrow(RuntimeException::new);
		
		issueRepo.deleteById(issueId);
		return "redirect:/app";
	}
	
	
	@PostMapping("/{issueId}/comments")
	public String addNewComment(@PathVariable("issueId") Long issueId, @Valid Comment comment, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			//TODO: actually do something here...
			System.out.println("Errors were found");
			return "redirect:/issues/"+issueId;
		}

		ApplicationUser author = userRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		Issue issue = issueRepo.findById(issueId).orElseThrow(RuntimeException::new);
		comment.setIssue(issue);
		comment.setAddedAt(new Date());
		comment.setAuthor(author);
		commentRepo.save(comment);
		return "redirect:/issues/"+issueId;
	}

}
