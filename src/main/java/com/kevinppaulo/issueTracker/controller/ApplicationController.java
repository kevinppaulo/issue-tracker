package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Issue;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.IssueJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;

@Controller
@RequestMapping("/")
public class ApplicationController {
	
	private final ApplicationUserJpaRepository appUserRepo;
	private final OrganizationJpaRepository organizationRepo;
	private final IssueJpaRepository issueRepo;
	
	@Autowired
	public ApplicationController(ApplicationUserJpaRepository appUserRepo, OrganizationJpaRepository organizationRepo,
			IssueJpaRepository issueRepo) {
		super();
		this.appUserRepo = appUserRepo;
		this.organizationRepo = organizationRepo;
		this.issueRepo = issueRepo;
	}

	@GetMapping("/invite")
	public ModelAndView invite(Principal principal) {
		ModelAndView mv = new ModelAndView("invite");
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		Organization organization = organizationRepo.findByUsers_UserId(user.getUserId()).orElseThrow(RuntimeException::new);
		mv.addObject("user", user);
		mv.addObject("organization", organization);
		return mv;
	}
	
	@GetMapping("error")
	public String  error() {
		return "error";
	}
	
	
	@GetMapping("app")
	public ModelAndView dashboard(Principal principal) {
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		Optional<Organization> usersOrganization = organizationRepo.findByUsers_UserId(user.getUserId());
		if(usersOrganization.get() != null) {
			System.out.println("shit's emtpy bro");
			ModelAndView newOrganization = new ModelAndView("new-organization");
			return newOrganization;
		}
		List<Issue> issues = issueRepo.findAllByOrganization_OrganizationId(usersOrganization.get().getOrganizationId());
		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("user", user);
		mv.addObject("organization", usersOrganization.get());
		mv.addObject("issues", issues);
		return mv;
	}
	
	@GetMapping("new-organization")
	public String newOrganization() {
		return "new-organization";
	}
	
	@GetMapping("userOrganizationCheck")
	public String checkIfUserHasOrganizations(Principal principal) {
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		Optional<Organization> usersOrganization = organizationRepo.findByUsers_UserId(user.getUserId());
		if(usersOrganization.get() != null) {
			return "redirect:/app";
		}
		return "redirect:/new-organization";
	}
	
	

}
