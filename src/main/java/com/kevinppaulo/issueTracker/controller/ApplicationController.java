package com.kevinppaulo.issueTracker.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kevinppaulo.issueTracker.models.ApplicationUser;
import com.kevinppaulo.issueTracker.repository.ApplicationUserJpaRepository;

@Controller
@RequestMapping("/")
public class ApplicationController {
	
	private final ApplicationUserJpaRepository appUserRepo;

	@Autowired
	public ApplicationController(ApplicationUserJpaRepository appUserRepo) {
		super();
		this.appUserRepo = appUserRepo;
	}
	
	
	@GetMapping("app")
	public ModelAndView dashboard(Principal principal) {
		ModelAndView mv = new ModelAndView("dashboard");
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		mv.addObject("user", user);
		return mv;
	}
	
	@GetMapping("newOrganization")
	public String newOrganization() {
		return "new-organization";
	}
	
	@GetMapping("userOrganizationCheck")
	public String checkIfUserHasOrganizations(Principal principal) {
		ApplicationUser user = appUserRepo.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
		if(user.getOrganization() != null) {
			return "redirect:/app";
		}
		return "redirect:/newOrganization";
	}
	
	

}
