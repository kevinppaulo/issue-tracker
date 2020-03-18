package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Issue;
import com.kevinppaulo.issueTracker.models.IssueStatus;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.IssueJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;

@Controller
@RequestMapping("/issues")
public class IssueController {
	
	private final OrganizationJpaRepository organizationRepo;
	private final ApplicationUserJpaRepository userRepo;
	private final IssueJpaRepository issueRepo;
	
	@Autowired
	public IssueController(OrganizationJpaRepository organizationRepo, ApplicationUserJpaRepository userRepo,
			IssueJpaRepository issueRepo) {
		this.organizationRepo = organizationRepo;
		this.userRepo = userRepo;
		this.issueRepo = issueRepo;
	}

	@GetMapping("/{issueId}")
	public ModelAndView issueDetails(@PathVariable("issueId") Long issueId) {
		ModelAndView mv = new ModelAndView("issue-details");
		Issue issue = issueRepo.findById(issueId).orElseThrow(RuntimeException::new);
		mv.addObject("issue", issue);
		return mv;
	}

}
