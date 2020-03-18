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
@RequestMapping("/organization")
public class OrganizationController {

	private final OrganizationJpaRepository organizationRepo;
	private final ApplicationUserJpaRepository appUserRepo;
	private final IssueJpaRepository issueRepo;
	
	
	@Autowired
	public OrganizationController(OrganizationJpaRepository organizationRepo, ApplicationUserJpaRepository appUserRepo,
			IssueJpaRepository issueRepo) {
		super();
		this.organizationRepo = organizationRepo;
		this.appUserRepo = appUserRepo;
		this.issueRepo = issueRepo;
	}



	@PostMapping("/new")
	public String addNewOrganization(@Valid Organization organization, BindingResult result, Principal principal) {
		ApplicationUser applicationUser = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		applicationUser.setOrganization(organization);
		organizationRepo.save(organization);
		appUserRepo.save(applicationUser);
		System.out.println("Saving organization" + organization);
		return "redirect:/app";
	}
	
	
	
	@GetMapping("/{organizationId}/issues")
	public ModelAndView allOrganizationIssues() {
		ModelAndView mv = new ModelAndView("organization-issues");
		return mv;
	}
	
	@GetMapping("/{organizationId}/issues/new")
	public ModelAndView getAddNewIssue(Principal principal) {
		ModelAndView mv = new ModelAndView("new-issue");
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		mv.addObject("user", user);
		return mv;
	}
	
	@PostMapping("/{organizationId}/issues/new")
	public String postAddNewIssue(@PathVariable("organizationId") Long organizationId,  @Valid Issue issue, BindingResult result, Principal principal) {
		if(result.hasErrors()) {
			return "redirect:/organization/" + organizationId + "/issues/new";
		}
		
		issue.setIssueStatus(IssueStatus.OPEN);
		issue.setCreatedAt(new Date());
		Organization organization = organizationRepo.findById(organizationId).orElseThrow(RuntimeException::new);
		organization.addIssue(issue);
		issueRepo.save(issue);
		organizationRepo.save(organization);
		
		System.out.println(organization);
		return "redirect:/app";
	}
	
	
}
