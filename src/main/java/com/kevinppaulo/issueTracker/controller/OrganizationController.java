package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.models.Organization;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;
import com.kevinppaulo.issueTracker.repository.OrganizationJpaRepository;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

	private final OrganizationJpaRepository organizationRepo;
	private final ApplicationUserJpaRepository appUserRepo;
	
	@Autowired
	public OrganizationController(OrganizationJpaRepository organizationRepo,
			ApplicationUserJpaRepository appUserRepo) {
		this.organizationRepo = organizationRepo;
		this.appUserRepo = appUserRepo;
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
}
