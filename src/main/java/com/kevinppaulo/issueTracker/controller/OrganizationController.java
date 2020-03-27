package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		List<ApplicationUser> organizationUsers = new ArrayList<ApplicationUser>();
		organizationUsers.add(applicationUser);
		organization.setUsers(organizationUsers);
		organizationRepo.save(organization);
		appUserRepo.save(applicationUser);
		System.out.println("Saving organization" + organization);
		return "redirect:/app";
	}
	
	
	
	@GetMapping("/{organizationId}")
	public ModelAndView allOrganizationIssues(@PathVariable("organizationId") Long organizationId, Principal principal) {
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		Organization organization = organizationRepo.findAllByUsers_UserId(user.getUserId()).stream().filter(org -> org.getOrganizationId() == organizationId).findFirst().orElseThrow(RuntimeException::new);
		
		ModelAndView mv = new ModelAndView("organization-details");
		mv.addObject("user", user);
		mv.addObject("organization", organization);

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
		issue.setOrganization(organization);
		issueRepo.save(issue);
		return "redirect:/app";
	}
	
	
}
